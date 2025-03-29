package com.example.bicycle.Retrofit;
import com.example.bicycle.models.Shop;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import java.util.List;


public interface ShopService {

    // get shops
    @GET("shops/")
    Call<List<Shop>> getShops();


}
