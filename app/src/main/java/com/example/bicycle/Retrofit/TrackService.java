package com.example.bicycle.Retrofit;
import com.example.bicycle.models.Record;
import com.example.bicycle.models.User;

import java.util.List;



public interface TrackService {

    //add a record
    @POST("records/add")
    Call<ResponseBody> addRecord(@Body Record record);

    //add a record
    @POST("records/get")
    Call<List<Record>> getRecords(@Body User user);

    //add a record
    @POST("records/delete")
    Call<ResponseBody> deleteRecord(@Body Record record);



}
