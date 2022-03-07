package com.appoule.monkeat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultUser {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("api_token")
    @Expose
    private String api_token;

    public String getSuccess() {
        return success;
    }

    public User getUser() {
        return user;
    }

    public String getApi_token() {
        return api_token;
    }
}
