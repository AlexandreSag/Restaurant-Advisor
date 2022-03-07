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
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FragmentProfile extends Fragment {

    private TextView profileName, profileFirstName, profileAge;
    private Button createRestaurant, logout;
    private User LogUser;
    public String ID;
    SharedPreferences mPrefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        logout = view.findViewById(R.id.LogoutBtn);
        createRestaurant = view.findViewById(R.id.CreateResto);

        mPrefs = requireActivity().getPreferences(Context.MODE_PRIVATE);
        ID = (mPrefs.getString("ID", ""));

        getUser(ID, view);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });

        createRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateRestaurant();
            }
        });

        return view;
    }

    private void CreateRestaurant() {
        getFragmentManager().beginTransaction().replace(R.id.main_layout, new FragmentCreateRestaurant()).addToBackStack(null).commit();
    }

    private void Logout() {
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.clear().apply();
        getFragmentManager().beginTransaction().replace(R.id.main_layout, new FragmentLogin()).addToBackStack(null).commit();
    }

    private void displayFinish(View view) {
        profileName = view.findViewById(R.id.ProfileName);
        profileFirstName = view.findViewById(R.id.ProfileFirstname);
        profileAge = view.findViewById(R.id.ProfileAge);

        profileName.setText(LogUser.getName());
        profileFirstName.setText(LogUser.getFirstname());
        profileAge.setText(LogUser.getAge().toString());
    }

    private void getUser(String id, View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<User> call = retrofitAPI.getUser(id);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                // we are getting response from our body
                // and passing it to our modal class.
                try {
                    String errorCatch = "Error code " + response.code() + " " + response.errorBody().string();
                    String responseString = "Response Code : " + errorCatch;
                    Log.d("ERROR", responseString);

                } catch (Exception e) {
                    LogUser = response.body();
                    displayFinish(view);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Log.d("ERROR", "CALL FAILURE");
            }
        });
    }
}