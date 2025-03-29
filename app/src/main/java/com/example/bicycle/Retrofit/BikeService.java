package com.example.bicycle.Retrofit;
import android.telecom.Call;

import com.example.bicycle.models.Bike;
import com.example.bicycle.models.Shop;

import java.util.List;



public interface BikeService {


    // get bikes
    @GET("bikes/")
    Call<List<Bike>> getBikes();

    // get bikes by shop
    @POST("bikes/shops")
    Call<List<Bike>> getBikesByShop(@Body Shop shop);


}
