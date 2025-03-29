package com.example.bicycle.Retrofit;
import com.example.bicycle.models.Location;
import com.example.bicycle.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import java.util.List;



public interface RentService {


    //add a rent
    @POST("location/add")
    Call<ResponseBody> addRent(@Body Location location);


    // get rents
    @POST("locations/")
    Call<List<Location>> getRents(@Body User user);



    // delete rent
    @POST("locations/delete")
    Call<ResponseBody> deleteRent(
            @Body Location location );


}
