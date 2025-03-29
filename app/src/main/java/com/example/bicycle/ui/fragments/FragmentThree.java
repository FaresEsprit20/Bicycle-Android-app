package com.example.bicycle.ui.fragments;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.bicycle.Retrofit.BikeService;
import com.example.bicycle.adapters.FavouritesAdapter;
import com.example.bicycle.models.Bike;


public class FragmentThree extends Fragment implements FavouritesAdapter.Callback{

    private BikeService apiService;
    private RecyclerView recyclerView;
    private List<Bike> bikes = new ArrayList<Bike>();
    private FavouritesAdapter mAdapter;
    private com.example.miniprojetandroid.database.AppDataBase database;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_three, container,false);
        database = com.example.miniprojetandroid.database.AppDataBase.getAppDatabase(getActivity().getApplicationContext());
        recyclerView = rootView.findViewById(R.id.recycler_favourites);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        apiService = RetrofitClient.getClient().create(BikeService.class);
        fillData();
        mAdapter = new FavouritesAdapter(getActivity(), (ArrayList<Bike>) bikes);
        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);
        mAdapter.setCallback(this);
        return rootView;

    }



    public void fillData(){
//       database.bikeDao().deleteAll();;
        bikes = database.bikeDao().getAll();
        Log.e("favourites",bikes.toString());
        /*bikes.add(new Bike(1,"ECO", "RTT" , "44", R.drawable.ruebike ));
        bikes.add(new Bike(2,"AAA", "RTT" , "33",  R.drawable.ruebike ));
        bikes.add(new Bike(3,"BBB", "RUE" , "11",  R.drawable.ruebike ));
        bikes.add(new Bike(4,"EEE", "SPORT" , "25",  R.drawable.ruebike ));
        bikes.add(new Bike(5,"CCC", "SPORT" , "77", R.drawable.ruebike ));
        Log.e("USERS LIST", bikes.toString());*/
    }


    @Override
    public void onItemClicked(Bike bike) {
        Bundle bundle = new Bundle();
        bundle.putInt("bike_id",bike.getId());
        bundle.putString("model", bike.getModel());
        bundle.putString("type", bike.getType());
        bundle.putString("price", bike.getPrice());
        bundle.putString("image", bike.getImage());
        FavDetailsFragment f = new FavDetailsFragment();
        f.setArguments(bundle);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentsContainer, f )
                .commit();
    }








}