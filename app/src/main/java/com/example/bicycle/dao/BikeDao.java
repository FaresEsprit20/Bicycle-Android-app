package com.example.bicycle.dao;
import com.example.bicycle.models.Bike;

import java.util.List;


@Dao
public interface BikeDao {

    @Query("SELECT * FROM bike_table")
    List<Bike> getAll();

    @Insert
    void insertOne(Bike bike);

    @Query("DELETE FROM bike_table")
    public void deleteAll();

    @Delete
    void delete(Bike bike);


}
