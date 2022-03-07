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


public class FragmentEditRestoView extends Fragment {

    private Restaurant restaurant;
    private String TOKEN;
    private String sGrade;
    private Float fGrade;

    private Button btnRestoUpdate;
    private EditText editRestoName, editRestoDescription, editRestoGrade, editRestoLocalization, editRestoPhoneNumber, editRestoWebsite, editRestoHours;

    public FragmentEditRestoView(Restaurant restaurant, String TOKEN) {
        this.restaurant = restaurant;
        this.TOKEN = TOKEN;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_resto_view, container, false);

        loadAndSetViewId(view);

        btnRestoUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editRestoName.getText().toString().isEmpty() || editRestoDescription.getText().toString().isEmpty() || editRestoHours.getText().toString().isEmpty() || editRestoLocalization.getText().toString().isEmpty() || editRestoPhoneNumber.getText().toString().isEmpty() || editRestoWebsite.getText().toString().isEmpty() || editRestoHours.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter all the values", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to post the data and passing our name and job.
                try {
                    sGrade = editRestoGrade.getText().toString();
                    fGrade = new Float(sGrade).floatValue();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Grade must be a value", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(fGrade > 5 || fGrade < 0) {
                    Toast.makeText(getContext(), "Grade must be between 0 and 5", Toast.LENGTH_SHORT).show();
                    return;
                }

                postData(editRestoName.getText().toString(), editRestoDescription.getText().toString(), fGrade, editRestoLocalization.getText().toString(), editRestoPhoneNumber.getText().toString(), editRestoWebsite.getText().toString(), editRestoHours.getText().toString());
            }
        });

        return view;
    }

    private void loadAndSetViewId(View view) {
        btnRestoUpdate = view.findViewById(R.id.btnRestoUpdate);

        editRestoName = view.findViewById(R.id.editRestoName);
        editRestoDescription = view.findViewById(R.id.editRestoDesc);
        editRestoGrade = view.findViewById(R.id.editRestoGrade);
        editRestoLocalization = view.findViewById(R.id.editRestoLocalization);
        editRestoPhoneNumber = view.findViewById(R.id.editRestoPhone);
        editRestoWebsite = view.findViewById(R.id.editRestoWebsite);
        editRestoHours = view.findViewById(R.id.editRestoHours);

        editRestoName.setText(restaurant.getName());
        editRestoDescription.setText(restaurant.getDescription());
        editRestoGrade.setText(restaurant.getGrade());
        editRestoLocalization.setText(restaurant.getLocalization());
        editRestoPhoneNumber.setText(restaurant.getPhone_number());
        editRestoWebsite.setText(restaurant.getWebsite());
        editRestoHours.setText(restaurant.getHours());
    }

    private void postData(String name, String description, Float grade, String localization, String phone, String website, String hours) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        PostRestaurant modal = new PostRestaurant(name, description, grade, localization,phone, website, hours);

        Call<RestaurantResponse> call = retrofitAPI.editRestaurant("Bearer " + TOKEN, modal, restaurant.getId());

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
                    Toast.makeText(getContext(), "Restaurant successfully Update !", Toast.LENGTH_SHORT).show();
                    getFragmentManager().beginTransaction().replace(R.id.main_layout, new FragmentMain()).addToBackStack(null).commit();
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