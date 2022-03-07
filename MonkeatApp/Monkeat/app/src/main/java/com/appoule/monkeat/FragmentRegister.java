package com.appoule.monkeat;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentRegister extends Fragment {

    private ImageButton BackArrowButton;
    private Button RegisterButton;
    private EditText loginEdt, passwordEdt, emailEdt, nameEdt, firstnameEdt, ageEdt;
    private String sAge;
    private Integer nAge;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        loginEdt = view.findViewById(R.id.idEdtLoginReg);
        passwordEdt = view.findViewById(R.id.idEdtPasswordReg);
        emailEdt = view.findViewById(R.id.idEdtMail);
        nameEdt = view.findViewById(R.id.idEdtName);
        firstnameEdt = view.findViewById(R.id.idEdtFirstname);
        ageEdt = view.findViewById(R.id.idEdtAge);

        BackArrowButton = view.findViewById(R.id.BackArrowButton);
        RegisterButton = view.findViewById(R.id.idBtnPost);

        BackArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_layout, new FragmentLogin()).addToBackStack(null).commit();
            }
        });

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // validating if the text field is empty or not.
                if (loginEdt.getText().toString().isEmpty() || passwordEdt.getText().toString().isEmpty() || emailEdt.getText().toString().isEmpty() || nameEdt.getText().toString().isEmpty() || firstnameEdt.getText().toString().isEmpty() || ageEdt.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter all the values", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to post the data and passing our name and job.
                try {
                    sAge = ageEdt.getText().toString();
                    nAge = new Integer(sAge).intValue();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Age must be an entire value", Toast.LENGTH_SHORT).show();
                    return;
                }

                postData(loginEdt.getText().toString() , passwordEdt.getText().toString() , emailEdt.getText().toString() , nameEdt.getText().toString() , firstnameEdt.getText().toString() , nAge);
            }
        });

        return view;
    }

    private void postData(String login, String password, String email, String name, String firstname, Integer age) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        PostUser modal = new PostUser(login, password, email, name, firstname, age);

        Call<User> call = retrofitAPI.createPost(modal);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                ageEdt.setText("");
                firstnameEdt.setText("");
                nameEdt.setText("");
                emailEdt.setText("");
                passwordEdt.setText("");
                loginEdt.setText("");

                try {
                    String errorCatch = "Error code " + response.code() + " " + response.errorBody().string();
                    String responseString = "Response Code : " + errorCatch;

                } catch (Exception e) {
                    String responseString = "Response Code : " + response.code();
                    User responseFromAPI = response.body();
                    Toast.makeText(getContext(), "Account successfully create !", Toast.LENGTH_SHORT).show();
                    getFragmentManager().beginTransaction().replace(R.id.main_layout, new FragmentLogin()).addToBackStack(null).commit();

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Log.d("ERROR", "Error found is : " + t.getMessage());
            }
        });

    }
}