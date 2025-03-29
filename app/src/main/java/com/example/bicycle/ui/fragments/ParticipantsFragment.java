package com.example.bicycle.ui.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.R;
import com.example.bicycle.Retrofit.EventService;
import com.example.bicycle.Retrofit.RetrofitClient;
import com.example.bicycle.adapters.ParticipantsAdapter;
import com.example.bicycle.models.Event;
import com.example.bicycle.models.Participants;

import java.util.ArrayList;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ParticipantsFragment extends Fragment {

    private EventService apiService;
    private RecyclerView recyclerView;
    private ArrayList<Participants> participants = new ArrayList<Participants>();
    private ParticipantsAdapter mAdapter;


    public ParticipantsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_participants, container, false);
        recyclerView = v.findViewById(R.id.recycler_participants);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        apiService = RetrofitClient.getClient().create(EventService.class);
        mAdapter = new ParticipantsAdapter(getActivity(), (ArrayList<Participants>) participants);
        fillData();
        recyclerView.setAdapter(mAdapter);
        return v;
    }



    public void fillData(){
        int id = getArguments().getInt("event_id");
        Event event = new Event();
        event.setId(id);
        Call<List<Participants>> call = apiService.getParticipants(event);
        call.enqueue(new Callback<List<Participants>>() {
            @Override
            public void onResponse(Call<List<Participants>> call, Response<List<Participants>> response) {
                if (response.isSuccessful()) {
                    participants.addAll(response.body());
                    Log.e("Bike LIST", participants.toString());
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Participants>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });


    }



}