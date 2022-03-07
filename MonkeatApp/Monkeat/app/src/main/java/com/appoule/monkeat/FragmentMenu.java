package com.appoule.monkeat;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FragmentMenu extends Fragment implements MenuSelectListener{

    RecyclerView recyclerView;
    List<Menu> menuList;
    MenuAdapter menuAdapter;

    private Restaurant restaurant;
    private TextView nameText, descriptionText, localizationText, phoneText, websiteText, hoursText;
    private RatingBar ratingBar;
    private ImageButton editRestaurant;
    private String TOKEN;
    private Boolean TokenIsHere;


    SearchView searchView;
    SearchView.SearchAutoComplete searchAutoComplete;

    SharedPreferences mPrefs;

    public FragmentMenu(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        float grade = Float.parseFloat( this.restaurant.getGrade());

        editRestaurant = view.findViewById(R.id.editBtnRestaurant);

        mPrefs = requireActivity().getPreferences(Context.MODE_PRIVATE);
        TOKEN = (mPrefs.getString("TOKEN", ""));
        TokenIsHere = true;

        if(TOKEN.equals("")){
            editRestaurant.setVisibility(View.GONE);
            TokenIsHere = false;
        }


        nameText = view.findViewById(R.id.restaurant_name_text);
        descriptionText = view.findViewById(R.id.restaurant_desctiption_text);
        ratingBar = view.findViewById(R.id.restaurant_rating_bar);
        localizationText = view.findViewById(R.id.restaurant_location_text);
        phoneText = view.findViewById(R.id.restaurant_phonenumber_text);
        websiteText = view.findViewById(R.id.restaurant_website_text);
        hoursText = view.findViewById(R.id.restaurant_hours_text);

        nameText.setText(this.restaurant.getName());
        descriptionText.setText(this.restaurant.getDescription());
        localizationText.setText(this.restaurant.getLocalization());
        phoneText.setText(this.restaurant.getPhone_number());
        websiteText.setText(this.restaurant.getWebsite());
        hoursText.setText(this.restaurant.getHours());

        ratingBar.setEnabled(false);
        ratingBar.setRating(grade);

        //search bar setup
        searchView = view.findViewById(R.id.search_view_menu);
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

        editRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_layout, new FragmentEditResto(restaurant)).addToBackStack(null).commit();
            }
        });

        return view;
    }

    private void filter(String newText) {
        List<Menu> filteredMenuList = new ArrayList<>();
        for (Menu item : menuList) {
            if (item.getName().toLowerCase().contains(newText.toLowerCase())){
                filteredMenuList.add(item);
            }
        }
        menuAdapter.filterList(filteredMenuList);
    }

    private void displayItems(View view, Context context) {
        recyclerView = view.findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));

        getMenus(restaurant.getId());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // Actions to do after 2 seconds
                displayFinish(context);
            }
        }, 150);

    }

    private void displayFinish(Context context) {
        menuList = restaurant.getMenuList();

        menuAdapter = new MenuAdapter(context, menuList, TokenIsHere, this);
        recyclerView.setAdapter(menuAdapter);
    }

    @Override
    public void onItemMenuClicked(Menu menu) {
        getFragmentManager().beginTransaction().replace(R.id.main_layout, new FragmentEditMenu(restaurant, menu, TOKEN)).addToBackStack(null).commit();
    }

    private void getMenus(String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<List<Menu>> call = retrofitAPI.getAllMenuInOneRestaurants(id);

        call.enqueue(new Callback<List<Menu>>() {
            @Override
            public void onResponse(Call<List<Menu>> call, Response<List<Menu>> response) {

                try {
                    String errorCatch = "Error code " + response.code() + " " + response.errorBody().string();
                    String responseString = "Response Code : " + errorCatch;
                    Log.d("ERROR CALL", responseString);

                } catch (Exception e) {
                    menuList = response.body();
                    restaurant.setMenuList(menuList);
                    //Integer restaurantsSize = menuList.size();
                    //Log.d("CALL SUCCESS", restaurantsSize.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Menu>> call, Throwable t) {
                Log.d("ERROR CALL", "Callback failure");
            }
        });
    }
}