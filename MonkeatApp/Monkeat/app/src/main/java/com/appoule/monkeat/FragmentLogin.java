package com.appoule.monkeat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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


public class FragmentLogin extends Fragment{

    private Button registerBtn, loginBtn;
    private EditText EdtLogin, EdtPassword;
    private LoginRequest loginRequest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_login, container, false);

        registerBtn = view.findViewById(R.id.idBtnRegister);
        loginBtn = view.findViewById(R.id.idBtnLogin);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_layout, new FragmentRegister()).addToBackStack(null).commit();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EdtLogin = view.findViewById(R.id.idEdtLogin);
                EdtPassword = view.findViewById(R.id.idEdtPassword);

                loginRequest = new LoginRequest();

                if(EdtLogin.getText().toString().equals("")) {
                    //login is empty
                    EdtLogin.setHint("Login is required !!");
                }
                else if(EdtPassword.getText().toString().equals("")){
                    //password is empty
                    EdtPassword.setHint("Password is required !!");
                }
                else {
                    //user can be try to login
                    loginRequest.setLogin(EdtLogin.getText().toString());
                    loginRequest.setPassword(EdtPassword.getText().toString());
                    authUser();
                }


            }
        });

        return view;
    }

    private void authUser() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        // calling a method to create a post and passing our modal class.
        Call<LoginResponse> call = retrofitAPI.authUser(loginRequest);

        // on below line we are executing our method.
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                // we are getting response from our body
                // and passing it to our modal class.
                try {
                    String errorCatch = "Error code " + response.code() + " " + response.errorBody().string();
                    String responseString = "Response Code : " + errorCatch;
                    Toast.makeText(getContext(), "Invalid login or password", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    String responseString = "Response Code : " + response.code();
                    SharedPreferences mPrefs = requireActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    String api_token = response.body().api_token;
                    String UserId = response.body().user.getId();
                    mEditor.putString("TOKEN", api_token).apply();
                    mEditor.putString("ID", UserId).apply();

                    getFragmentManager().beginTransaction().replace(R.id.main_layout, new FragmentProfile()).addToBackStack(null).commit();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Log.d("ERROR", "CALL FAILURE");
            }
        });
    }

}