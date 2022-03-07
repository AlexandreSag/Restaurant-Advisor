package com.appoule.monkeat;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FragmentMain extends Fragment implements RestaurantSelectListener{

    RecyclerView recyclerView;
    List<Restaurant> restaurantList;
    List<Menu> menuList;
    RestaurantAdapter restaurantAdapter;
    SearchView searchView;
    SearchView.SearchAutoComplete searchAutoComplete;

    Integer size;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        searchView = view.findViewById(R.id.search_view_restaurant);
        searchView.setQueryHint("Search here...");

        searchAutoComplete = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(Color.GRAY);
        searchAutoComplete.setTextColor(Color.BLACK);


        displayItems(view, this.getContext());

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

        return view;
    }

    private void filter(String newText) {
        List<Restaurant> filteredRestaurantList = new ArrayList<>();
        for (Restaurant item : restaurantList) {
            if (item.getName().toLowerCase().contains(newText.toLowerCase())){
                filteredRestaurantList.add(item);
            }
        }
        restaurantAdapter.filterList(filteredRestaurantList);
    }

    private void displayItems(View view, Context context) {
        recyclerView = view.findViewById(R.id.recycler_restaurant);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        //input of json here
        restaurantList = new ArrayList<>();

        getRestaurants();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // Actions to do after 2 seconds
                displayFinish(context);
            }
        }, 150);
    }

    public void displayFinish(Context context){

        setMenuList();

        restaurantAdapter = new RestaurantAdapter(context, restaurantList, this);
        recyclerView.setAdapter(restaurantAdapter);
    }

    private void setMenuList() {
        menuList = new ArrayList<>();
        for(int i = 0; i < restaurantList.size(); i++){
            restaurantList.get(i).setMenuList(menuList);
        }
    }

    @Override
    public void onItemClicked(Restaurant restaurant) {
        getFragmentManager().beginTransaction().replace(R.id.main_layout, new FragmentMenu(restaurant)).addToBackStack(null).commit();
    }

    private void getRestaurants() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<List<Restaurant>> call = retrofitAPI.getAllRestaurants();

        call.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {

                try {
                    String errorCatch = "Error code " + response.code() + " " + response.errorBody().string();
                    String responseString = "Response Code : " + errorCatch;
                    Log.d("ERROR CALL", responseString);

                } catch (Exception e) {
                    restaurantList = response.body();
                    Integer restaurantsSize = restaurantList.size();
                    //Log.d("CALL SUCCESS", restaurantsSize.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                Log.d("ERROR CALL", "Callback failure");
            }
        });
    }
}