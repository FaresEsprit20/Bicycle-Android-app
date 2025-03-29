package com.example.bicycle.Retrofit;
import com.example.bicycle.models.Shop;

import java.util.List;


public interface ShopService {

    // get shops
    @GET("shops/")
    Call<List<Shop>> getShops();


}
