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
import com.example.bicycle.Retrofit.RetrofitClient;
import com.example.bicycle.adapters.BikesAdapter;
import com.example.bicycle.models.Bike;
import com.example.bicycle.models.Shop;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentOne extends Fragment implements BikesAdapter.Callback{

    private BikeService apiService;
    private RecyclerView recyclerView;
    private List<Bike> bikes = new ArrayList<Bike>();
    private  BikesAdapter mAdapter;
    List<Bike> result = new ArrayList<Bike>();
    private Shop shop;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_one, container,false);
        recyclerView = rootView.findViewById(R.id.recycler_bikes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        apiService = RetrofitClient.getClient().create(BikeService.class);
        int shopid = getArguments().getInt("shop_id");
        double latitude = getArguments().getDouble("latitude");
        double longitude = getArguments().getDouble("longitude");
        String title = getArguments().getString("title");
        this.shop = new Shop(shopid,title,latitude,longitude);
        mAdapter = new BikesAdapter(getActivity(), (ArrayList<Bike>) bikes);
        fillData();
        recyclerView.setAdapter(mAdapter);
        mAdapter.setCallback(this);
        return rootView;

    }


    public void fillData(){
        Log.e("SHop ID", String.valueOf(shop.getId()));
        Call<List<Bike>> call = apiService.getBikesByShop(this.shop);
        call.enqueue(new Callback<List<Bike>>() {
            @Override
            public void onResponse(Call<List<Bike>> call, Response<List<Bike>> response) {
                if(response.isSuccessful()){
                    bikes.addAll(response.body());
                    for(Bike b : bikes){
                        if(b.id == 0){
                        bikes.clear();
                        }
                        Log.e("Bike LIST", String.valueOf(b.getImage()));
                    }
                    Log.e("Bike LIST", bikes.toString());
                      mAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<Bike>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }


    @Override
    public void onItemClicked(Bike bike) {
        if (bikes.size() > 0) {
            Bundle bundle = new Bundle();
            bundle.putInt("bike_id", bike.getId());
            bundle.putString("model", bike.getModel());
            bundle.putString("type", bike.getType());
            bundle.putString("price", bike.getPrice());
            bundle.putString("image", bike.getImage());
            bundle.putInt("shop_id", bike.getShop());
            DetailsFragment f = new DetailsFragment();
            f.setArguments(bundle);
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentsContainer, f)
                    .commit();
        }
    }




}