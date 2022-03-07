package com.appoule.monkeat;

import android.content.Context;
import android.content.SharedPreferences;
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


public class FragmentCreateRestaurant extends Fragment {

    private EditText createRestoName, createRestoDescription, createRestoGrade, createRestoLocalization, createRestoPhoneNumber, createRestoWebsite, createRestoHours;
    private Button btnCreateResto;
    private String sGrade;
    private Float fGrade;
    private String TOKEN;
    SharedPreferences mPrefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_restaurant, container, false);

        createRestoName = view.findViewById(R.id.CreateRestoName);
        createRestoDescription = view.findViewById(R.id.CreateRestoDesc);
        createRestoGrade = view.findViewById(R.id.CreateRestoGrade);
        createRestoLocalization = view.findViewById(R.id.CreateRestoLocalization);
        createRestoPhoneNumber = view.findViewById(R.id.CreateRestoPhone);
        createRestoWebsite = view.findViewById(R.id.CreateRestoWebsite);
        createRestoHours = view.findViewById(R.id.CreateRestoHours);
        btnCreateResto = view.findViewById(R.id.BtnCreateResto);

        btnCreateResto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (createRestoName.getText().toString().isEmpty() || createRestoDescription.getText().toString().isEmpty() || createRestoHours.getText().toString().isEmpty() || createRestoLocalization.getText().toString().isEmpty() || createRestoPhoneNumber.getText().toString().isEmpty() || createRestoWebsite.getText().toString().isEmpty() || createRestoHours.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter all the values", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to post the data and passing our name and job.
                try {
                    sGrade = createRestoGrade.getText().toString();
                    fGrade = new Float(sGrade).floatValue();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Grade must be a value", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(fGrade > 5 || fGrade < 0) {
                    Toast.makeText(getContext(), "Grade must be between 0 and 5", Toast.LENGTH_SHORT).show();
                    return;
                }

                mPrefs = requireActivity().getPreferences(Context.MODE_PRIVATE);
                TOKEN = (mPrefs.getString("TOKEN", ""));

                postData(createRestoName.getText().toString(), createRestoDescription.getText().toString(), fGrade, createRestoLocalization.getText().toString(), createRestoPhoneNumber.getText().toString(), createRestoWebsite.getText().toString(), createRestoHours.getText().toString());
            }
        });

        return view;
    }

    private void postData(String name, String description, Float grade, String localization, String phone, String website, String hours) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        PostRestaurant modal = new PostRestaurant(name, description, grade, localization,phone, website, hours);

        Call<RestaurantResponse> call = retrofitAPI.createRestaurant("Bearer " + TOKEN, modal);

        call.enqueue(new Callback<RestaurantResponse>() {
            @Override
            public void onResponse(Call<RestaurantResponse> call, Response<RestaurantResponse> response) {

                createRestoName.setText("");
                createRestoDescription.setText("");
                createRestoGrade.setText("");
                createRestoLocalization.setText("");
                createRestoPhoneNumber.setText("");
                createRestoWebsite.setText("");
                createRestoHours.setText("");

                try {
                    String errorCatch = "Error code " + response.code() + " " + response.errorBody().string();
                    String responseString = "Response Code : " + errorCatch;
                    Log.d("WHY", responseString);
                    Toast.makeText(getContext(), responseString, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    String responseString = "Response Code : " + response.code();
                    RestaurantResponse responseFromAPI = response.body();
                    Toast.makeText(getContext(), "Restaurant successfully create !", Toast.LENGTH_SHORT).show();
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