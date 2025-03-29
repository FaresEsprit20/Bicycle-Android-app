package com.example.bicycle.Retrofit;
import com.example.bicycle.models.Event;
import com.example.bicycle.models.Participants;

import java.util.List;


public interface EventService {

    //add a record
    @POST("events/add")
    Call<ResponseBody> addEvent(@Body Event evt);

    //add a record
    @POST("participate")
    Call<ResponseBody> participate(@Body Event evt);

   @GET("events/all")
   Call<List<Event>> getEvents();

    @POST("participants/alls")
    Call<List<Participants>> getParticipants(@Body Event e);



}
