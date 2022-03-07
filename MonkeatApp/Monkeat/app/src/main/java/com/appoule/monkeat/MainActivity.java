package com.appoule.monkeat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;
    public String TOKEN = "NO TOKEN";
    public String ID = "NO ID";
    SharedPreferences mPrefs;

    private ArrayList<Restaurant> restaurantList;
    private RecyclerView recyclerview_restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPrefs = getPreferences(MODE_PRIVATE);

        TOKEN = (mPrefs.getString("TOKEN", ""));
        ID = (mPrefs.getString("ID", ""));
        if(TOKEN.equals("") || ID.equals("")) {
            TOKEN = "NO TOKEN";
            ID = "NO ID";
        }

        //set the first fragment layout in load
        getSupportFragmentManager().beginTransaction().add(R.id.main_layout, new FragmentMain()).commit();

        toolbar = getSupportActionBar();

        BottomNavigationView navigation = findViewById(R.id.nabigation_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = (item) -> {
        switch (item.getItemId()) {
            case R.id.item_1:
                if(tokenIsValided()){
                    loadFragment(new FragmentProfile());
                }
                else{
                    loadFragment(new FragmentLogin());
                }
                return true;
            case R.id.item_2:
                loadFragment(new FragmentMain());
                return true;
            case R.id.item_3:
                return true;
        }
        return false;
    };

    private boolean tokenIsValided(){
        TOKEN = (mPrefs.getString("TOKEN", ""));
        ID = (mPrefs.getString("ID", ""));
        if(TOKEN.equals("") || ID.equals("")) {
            TOKEN = "NO TOKEN";
            ID = "NO ID";
            return false;
        }
        else{
            return true;
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}