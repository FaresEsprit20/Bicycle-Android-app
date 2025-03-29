package com.example.bicycle.Retrofit;
import com.example.bicycle.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

import java.util.List;



public interface UserService {


    @POST("register/")
    Call<ResponseBody> createUser(@Body User user);

    @POST("login/")
    Call<User> loginUser(@Body User user);

    @GET("user/get")
    Call<User> getUser(@Query("email") String email);

    // get bikes
    @GET("users")
    Call<List<User>> getUsers();

    // update user
    @PUT("user/update")
    Call<ResponseBody> updateUser(
            @Body User user
    );


}

