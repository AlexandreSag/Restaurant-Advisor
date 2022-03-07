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


public class FragmentCreateMenu extends Fragment {

    private Restaurant restaurant;
    private String TOKEN;
    private String sPrice;
    private Float fPrice;

    private Button btnCreateMenu;
    private EditText CreateMenuName, CreateMenuDesc, CreateMenuPrice;

    public FragmentCreateMenu(Restaurant restaurant, String TOKEN) {
        this.restaurant = restaurant;
        this.TOKEN = TOKEN;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_menu, container, false);

        btnCreateMenu = view.findViewById(R.id.btnCreateMenu);
        CreateMenuName = view.findViewById(R.id.CreateMenuName);
        CreateMenuDesc = view.findViewById(R.id.CreateMenuDesc);
        CreateMenuPrice = view.findViewById(R.id.CreateMenuPrice);


        btnCreateMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CreateMenuName.getText().toString().isEmpty() || CreateMenuDesc.getText().toString().isEmpty() || CreateMenuPrice.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter all the values", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    sPrice = CreateMenuPrice.getText().toString();
                    fPrice = new Float(sPrice).floatValue();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Price must be a value", Toast.LENGTH_SHORT).show();
                    return;
                }

                postData(CreateMenuName.getText().toString(), CreateMenuDesc.getText().toString(), fPrice);
            }
        });

        return view;
    }

    private void postData(String name, String description, Float price) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        PostMenu modal = new PostMenu(name, description, price);

        Call<RestaurantResponse> call = retrofitAPI.createMenu("Bearer " + TOKEN, modal, restaurant.getId());

        call.enqueue(new Callback<RestaurantResponse>() {
            @Override
            public void onResponse(Call<RestaurantResponse> call, Response<RestaurantResponse> response) {

                CreateMenuName.setText("");
                CreateMenuDesc.setText("");
                CreateMenuPrice.setText("");

                try {
                    String errorCatch = "Error code " + response.code() + " " + response.errorBody().string();
                    String responseString = "Response Code : " + errorCatch;
                    Log.d("WHY", responseString);
                    Toast.makeText(getContext(), responseString, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    String responseString = "Response Code : " + response.code();
                    RestaurantResponse responseFromAPI = response.body();
                    Toast.makeText(getContext(), "Menu successfully create !", Toast.LENGTH_SHORT).show();
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