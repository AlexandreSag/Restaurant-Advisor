package com.appoule.monkeat;

public class RestaurantResponse {
    private String response;

    RestaurantResponse(String response){
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
