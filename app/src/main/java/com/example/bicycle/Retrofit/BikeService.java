package com.example.bicycle.Retrofit;

import com.example.bicycle.models.Bike;
import com.example.bicycle.models.Shop;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface BikeService {

    // Get all bikes
    @GET("bikes/")
    Call<List<Bike>> getBikes();

    // Get bikes by shop
    @POST("bikes/shops")
    Call<List<Bike>> getBikesByShop(@Body Shop shop);


}
