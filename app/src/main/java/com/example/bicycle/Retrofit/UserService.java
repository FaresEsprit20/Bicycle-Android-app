package com.example.bicycle.Retrofit;
import com.example.bicycle.models.User;

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

