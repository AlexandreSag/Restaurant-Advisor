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
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentEditResto extends Fragment {

    private Button EditResto, CreateMenu, DeleteResto;
    private Restaurant restaurant;
    private String TOKEN;

    SharedPreferences mPrefs;

    public FragmentEditResto(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_resto, container, false);

        EditResto = view.findViewById(R.id.EditResto);
        CreateMenu = view.findViewById(R.id.BtnCreateMenu);
        DeleteResto = view.findViewById(R.id.DeleteResto);

        mPrefs = requireActivity().getPreferences(Context.MODE_PRIVATE);
        TOKEN = (mPrefs.getString("TOKEN", ""));

        EditResto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_layout, new FragmentEditRestoView(restaurant, TOKEN)).addToBackStack(null).commit();
            }
        });

        CreateMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_layout, new FragmentCreateMenu(restaurant, TOKEN)).addToBackStack(null).commit();
            }
        });

        DeleteResto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteResto(restaurant);
            }
        });

        return view;
    }

    private void deleteResto(Restaurant restaurant) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<RestaurantResponse> call = retrofitAPI.deleteRestaurant("Bearer " + TOKEN, restaurant.getId());

        call.enqueue(new Callback<RestaurantResponse>() {
            @Override
            public void onResponse(Call<RestaurantResponse> call, Response<RestaurantResponse> response) {

                try {
                    String errorCatch = "Error code " + response.code() + " " + response.errorBody().string();
                    String responseString = "Response Code : " + errorCatch;
                    Log.d("ERROR CALL", responseString);

                } catch (Exception e) {
                    RestaurantResponse restaurantList = response.body();
                    Toast.makeText(getContext(), "Restaurant Delete Successfully", Toast.LENGTH_SHORT).show();
                    getFragmentManager().beginTransaction().replace(R.id.main_layout, new FragmentMain()).addToBackStack(null).commit();
                }
            }

            @Override
            public void onFailure(Call<RestaurantResponse> call, Throwable t) {
                Log.d("ERROR CALL", "Callback failure");
            }
        });
    }
}