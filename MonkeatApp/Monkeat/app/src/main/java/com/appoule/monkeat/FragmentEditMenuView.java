package com.appoule.monkeat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FragmentEditMenuView extends Fragment {

    private Restaurant restaurant;
    private Menu menu;
    private String TOKEN;

    private Button btnMenuUpdate;
    private EditText editMenuName, editMenuDesc, editMenuPrice;

    private String sPrice;
    private Float fPrice;

    public FragmentEditMenuView(Restaurant restaurant, Menu menu, String TOKEN) {
        this.restaurant = restaurant;
        this.menu = menu;
        this.TOKEN = TOKEN;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_menu_view, container, false);

        loadAndSetView(view);

        btnMenuUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editMenuName.getText().toString().isEmpty() || editMenuDesc.getText().toString().isEmpty() || editMenuPrice.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter all the values", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    sPrice = editMenuPrice.getText().toString();
                    fPrice = new Float(sPrice).floatValue();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Grade must be a value", Toast.LENGTH_SHORT).show();
                    return;
                }

                postData(editMenuName.getText().toString(), editMenuDesc.getText().toString(), fPrice);
            }
        });

        return view;
    }

    private void loadAndSetView(View view) {
        btnMenuUpdate = view.findViewById(R.id.btnMenuUpdate);
        editMenuName = view.findViewById(R.id.editMenuName);
        editMenuDesc = view.findViewById(R.id.editMenuDesc);
        editMenuPrice = view.findViewById(R.id.editMenuPrice);

        editMenuName.setText(menu.getName());
        editMenuDesc.setText(menu.getDescription());
        editMenuPrice.setText(menu.getPrice());
    }

    private void postData(String name, String description, Float price) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        PostMenu modal = new PostMenu(name, description, price);

        Call<RestaurantResponse> call = retrofitAPI.editMenu("Bearer " + TOKEN, modal, restaurant.getId(), menu.getId());

        call.enqueue(new Callback<RestaurantResponse>() {
            @Override
            public void onResponse(Call<RestaurantResponse> call, Response<RestaurantResponse> response) {

                try {
                    String errorCatch = "Error code " + response.code() + " " + response.errorBody().string();
                    String responseString = "Response Code : " + errorCatch;
                    Toast.makeText(getContext(), responseString, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    String responseString = "Response Code : " + response.code();
                    RestaurantResponse responseFromAPI = response.body();
                    Toast.makeText(getContext(), "Menu successfully Update !", Toast.LENGTH_SHORT).show();
                    getFragmentManager().beginTransaction().replace(R.id.main_layout, new FragmentMenu(restaurant)).addToBackStack(null).commit();
                }
            }

            @Override
            public void onFailure(Call<RestaurantResponse> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Log.d("ERROR", "Error found is : " + t.getMessage());
            }
        });
    }
}