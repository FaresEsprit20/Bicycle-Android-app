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
import com.example.bicycle.adapters.EventsAdapter;
import com.example.bicycle.models.Event;

import java.util.ArrayList;
import java.util.List;



public class EventsFragment extends Fragment implements EventsAdapter.Callback{

    private List<Event> events = new ArrayList<Event>();
    private EventsAdapter mAdapter;
    private RecyclerView recyclerView;
    private EventService apiService;
    public static Event event;

    public EventsFragment() {
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
        View v = inflater.inflate(R.layout.fragment_events, container, false);
        recyclerView = v.findViewById(R.id.recycler_events);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));

        apiService = RetrofitClient.getClient().create(EventService.class);
        mAdapter = new EventsAdapter(getActivity(), (ArrayList<Event>) events);
        fillData();
        recyclerView.setAdapter(mAdapter);
        mAdapter.setCallback(this);
        return v;
    }


    public void fillData(){
        Call<List<Event>> call = apiService.getEvents();
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if(response.isSuccessful()){
                    events.addAll(response.body());
                    Log.e("EVents LIST",events.toString());
                    for(Event e : events){
                        e.setImage(R.drawable.event);
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });

    }



    @Override
    public void onItemClicked(Event rent) {
        if (events.size() > 0 ) {
            Bundle bundle = new Bundle();
             bundle.putInt("event_id", rent.getId());
            bundle.putString("date_evt", rent.getDate());
            bundle.putString("adresse_evt", rent.getAdresse());
            bundle.putString("time_evt", rent.getTime());
            bundle.putInt("user", rent.getUser());

            EventDetailsFragment f = new EventDetailsFragment();
            f.setArguments(bundle);
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentsContainer, f)
                    .commit();
        }
    }




}