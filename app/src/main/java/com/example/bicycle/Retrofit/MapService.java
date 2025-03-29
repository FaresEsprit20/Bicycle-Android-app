package com.example.bicycle.Retrofit;
import com.example.bicycle.models.BikeCyclist;
import com.example.bicycle.models.Circuit;
import com.example.bicycle.models.Community;
import com.example.bicycle.models.Shop;

import java.util.List;


public interface MapService {


    // get shops
    @GET("shops/")
    Call<List<Shop>> getShops();

    // get communities
    @GET("communities/")
    Call<List<Community>> getCommunities();

    // get cyclists
    @GET("cyclists/")
    Call<List<BikeCyclist>> getCyclists();

    // get circuits
    @GET("circuits/")
    Call<List<Circuit>> getCircuits();



}
