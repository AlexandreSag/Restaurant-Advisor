package com.appoule.monkeat;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitAPI {

    @GET("restaurants")
    Call<List<Restaurant>> getAllRestaurants();

    @GET("restaurant/{path_id}")
    Call<Restaurant> getOneRestaurant(@Path("path_id") String var);

    @GET("restaurant/{path_id}/menus")
    Call<List<Menu>> getAllMenuInOneRestaurants(@Path("path_id") String var);

    @GET("user/{path_id}")
    Call<User> getUser(@Path("path_id") String var);

    @POST("auth")
    Call<LoginResponse> authUser(@Body LoginRequest loginRequest);

    @POST("register")
    Call<User> createPost(@Body PostUser user);

    //AUTH TOKEN NEEDED :

    @Headers("Accept: application/json")
    @POST("restaurant")
    Call<RestaurantResponse> createRestaurant(@Header("Authorization") String authToken, @Body PostRestaurant restaurant);

    @Headers("Accept: application/json")
    @PUT("restaurant/{path_id}")
    Call<RestaurantResponse> editRestaurant(@Header("Authorization") String authToken, @Body PostRestaurant restaurant, @Path("path_id") String var);

    @Headers("Accept: application/json")
    @DELETE("restaurant/{path_id}")
    Call<RestaurantResponse> deleteRestaurant(@Header("Authorization") String authToken, @Path("path_id") String var);

    //MENU :

    @Headers("Accept: application/json")
    @POST("restaurant/{path_id}/menu")
    Call<RestaurantResponse> createMenu(@Header("Authorization") String authToken, @Body PostMenu menu, @Path("path_id") String var);

    @Headers("Accept: application/json")
    @PUT("restaurant/{path_id}/menu/{menu_id}")
    Call<RestaurantResponse> editMenu(@Header("Authorization") String authToken, @Body PostMenu menu, @Path("path_id") String var, @Path("menu_id") String menu_id);

    @Headers("Accept: application/json")
    @DELETE("restaurant/{path_id}/menu/{menu_id}")
    Call<RestaurantResponse> deleteMenu(@Header("Authorization") String authToken, @Path("path_id") String var, @Path("menu_id") String menu_id);

}
