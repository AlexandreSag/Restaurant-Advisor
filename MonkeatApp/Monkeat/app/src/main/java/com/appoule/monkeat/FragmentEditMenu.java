package com.appoule.monkeat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentEditMenu extends Fragment {

    private Restaurant restaurant;
    private Menu menu;
    private String TOKEN;

    private Button EditMenu, DeleteMenu;

    public FragmentEditMenu(Restaurant restaurant, Menu menu, String TOKEN) {
        this.restaurant = restaurant;
        this.menu = menu;
        this.TOKEN = TOKEN;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_menu, container, false);

        EditMenu = view.findViewById(R.id.EditMenu);
        DeleteMenu = view.findViewById(R.id.DeleteMenu);
        
        EditMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_layout, new FragmentEditMenuView(restaurant, menu, TOKEN)).addToBackStack(null).commit();
            }
        });
        
        DeleteMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMenu();
            }
        });

        return view;
    }

    private void deleteMenu() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<RestaurantResponse> call = retrofitAPI.deleteMenu("Bearer " + TOKEN, restaurant.getId(), menu.getId());

        call.enqueue(new Callback<RestaurantResponse>() {
            @Override
            public void onResponse(Call<RestaurantResponse> call, Response<RestaurantResponse> response) {

                try {
                    String errorCatch = "Error code " + response.code() + " " + response.errorBody().string();
                    String responseString = "Response Code : " + errorCatch;
                    Log.d("ERROR CALL", responseString);

                } catch (Exception e) {
                    RestaurantResponse restaurantList = response.body();
                    Toast.makeText(getContext(), "Menu Delete Successfully", Toast.LENGTH_SHORT).show();
                    getFragmentManager().beginTransaction().replace(R.id.main_layout, new FragmentMenu(restaurant)).addToBackStack(null).commit();
                }
            }

            @Override
            public void onFailure(Call<RestaurantResponse> call, Throwable t) {
                Log.d("ERROR CALL", "Callback failure");
            }
        });
    }
}