package com.example.bicycle.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.R;
import com.example.bicycle.Retrofit.RentService;
import com.example.bicycle.adapters.RentsAdapter;
import com.example.bicycle.models.Bike;
import com.example.bicycle.models.Location;
import com.example.bicycle.models.User;

import java.util.ArrayList;
import java.util.List;



public class FragmentTwo extends Fragment implements RentsAdapter.Callback{

    private RentService apiService;
    private RecyclerView recyclerView;
    private ArrayList<Bike> bikes = new ArrayList<Bike>();
    private ArrayList<Location> locations = new ArrayList<Location>();
    private RentsAdapter mAdapter;
    private SharedPreferences sp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_two, container,false);
        recyclerView = rootView.findViewById(R.id.recycler_rents);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));

        apiService = RetrofitClient.getClient().create(RentService.class);
        mAdapter = new RentsAdapter(getActivity(), (ArrayList<Location>) locations);
        fillData();
        recyclerView.setAdapter(mAdapter);
        mAdapter.setCallback(this);

        return rootView;
    }


    public void fillData(){

       /* Bike b1 = new Bike(1,"ECO", "RTT" , "55", R.drawable.ruebike );
        Bike b2 = new Bike(2,"AAA", "Sport" , "66",  R.drawable.ruebike );
        Location l = new Location(1,"Marsa", b1);
        locations.add(new Location(1,"Marsa", b1));
        locations.add(new Location(2,"Sousse", b2));  */
        sp = getActivity().getSharedPreferences("com.example.miniprojetandroid.shared", Context.MODE_PRIVATE);
        User u = new User();
        u.setId(Integer.parseInt(sp.getString("ID","")));
        Log.e("User " , u.toString());


        Call<List<Location>> call = apiService.getRents(u);
        call.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                if(response.isSuccessful()  ){
                    locations.addAll(response.body());
                    for(Location rent: locations){
                        if(rent.id == 0) {
                            locations.clear();
                        }
                        Bike b = new Bike();
                        b.setModel(rent.getModel());
                        b.setType(rent.getType());
                        b.setPrice(rent.getPrice());
                        b.setImage(rent.getImage());
                       rent.setBike(b);

                    }
                    Log.e("Bike LIST", locations.toString());

                    mAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });

    }


    @Override
    public void onItemClicked(Location rent) {
        if (locations.size() >0) {
            Bundle bundle = new Bundle();
            bundle.putInt("location_id", rent.getId());
            bundle.putString("datelocation", rent.getDateLocation());
            bundle.putString("adresselocation", rent.getAddressLocation());
            bundle.putString("totalprice", rent.getTotalprice());
            bundle.putInt("user_id", rent.getUser_id());
            bundle.putInt("bike_id", rent.getBike_id());
            bundle.putString("model", rent.getBike().getModel());
            bundle.putString("type", rent.getBike().getType());
            bundle.putString("price", rent.getBike().getPrice());
            bundle.putString("title", rent.getTitle());
            bundle.putInt("shop_id", rent.getId());
            bundle.putString("image", rent.getBike().getImage());
            RentDetailsFragment f = new RentDetailsFragment();
            f.setArguments(bundle);
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentsContainer, f)
                    .commit();
        }
    }



}