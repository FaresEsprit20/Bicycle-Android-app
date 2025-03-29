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
import android.widget.Toast;


import com.example.R;
import com.example.bicycle.Retrofit.TrackService;
import com.example.bicycle.adapters.TrackAdapter;
import com.example.bicycle.models.Record;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RecordListFragment extends Fragment implements TrackAdapter.Callback {

    private TrackService apiService;
    private RecyclerView recyclerView;
    private ArrayList<Record> records = new ArrayList<Record>();
    private TrackAdapter mAdapter;
    private SharedPreferences sp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_record_list, container, false);
        recyclerView = rootView.findViewById(R.id.recycler_record_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        apiService = RetrofitClient.getClient().create(TrackService.class);
        mAdapter = new TrackAdapter(getActivity(), (ArrayList<Record>) records);
        fillData();
        recyclerView.setAdapter(mAdapter);
        mAdapter.setCallback(this);

        return rootView;
    }


    public void fillData() {

        sp = getActivity().getSharedPreferences("com.example.miniprojetandroid.shared", Context.MODE_PRIVATE);
        User u = new User();
        u.setId(Integer.parseInt(sp.getString("ID", "")));
        Log.e("User ", u.toString());


        Call<List<Record>> call = apiService.getRecords(u);
        call.enqueue(new Callback<List<Record>>() {
            @Override
            public void onResponse(Call<List<Record>> call, Response<List<Record>> response) {
                if (response.isSuccessful()) {
                    records.addAll(response.body());
                    Log.e("Bike LIST", records.toString());
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Record>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });

    }


    @Override
    public void onItemClicked(Record record) {
        Bundle bundle = new Bundle();
        bundle.putString("adresse", record.getAddress());
        bundle.putString("time", record.getTime());
        bundle.putString("distance", record.getDistance());
        bundle.putString("daterecord", record.getDaterecord());

        RecordDetailsFragment f = new RecordDetailsFragment();
        f.setArguments(bundle);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentsContainer, f)
                .commit();

    }


    @Override
    public void ItemDelete(Record record) {
        Toast.makeText(getActivity(),"Deleted Record !",Toast.LENGTH_SHORT).show();
        Call<ResponseBody> call = apiService.deleteRecord(record);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                String message = null;
                try {
                    message = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(response.isSuccessful()  ){

                  //  Toast.makeText(getActivity(),"Deleted Record !",Toast.LENGTH_SHORT).show();
                    mAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });



        RecordFragment f = new RecordFragment();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentsContainer, f)
                .commit();
    }


}