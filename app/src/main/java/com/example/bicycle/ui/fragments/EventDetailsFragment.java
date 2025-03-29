package com.example.bicycle.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.R;
import com.example.bicycle.Retrofit.EventService;
import com.example.bicycle.Retrofit.RetrofitClient;
import com.example.bicycle.models.Event;
import com.example.bicycle.models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventDetailsFragment extends Fragment {

    private TextView lbtime, lbadresse, lbdate;
    private Button btnParticipate , btnParticipants;
    public static Event event;
    private EventService apiService;
    private SharedPreferences sp;


    public EventDetailsFragment() {
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
        View v = inflater.inflate(R.layout.fragment_event_details, container, false);

        lbtime = v.findViewById(R.id.TimeD);
        lbdate = v.findViewById(R.id.DateD);
        lbadresse = v.findViewById(R.id.AdresseD);
        btnParticipate = v.findViewById(R.id.btnParticipate);
        btnParticipants = v.findViewById(R.id.btnP);
        apiService = RetrofitClient.getClient().create(EventService.class);

        int id = getArguments().getInt("event_id");
        String adresse = getArguments().getString("adresse_evt");
        String date = getArguments().getString("date_evt");
        String time = getArguments().getString("time_evt");
        int user = getArguments().getInt("user");
          String title ="";
        event = new Event(id,title,adresse,date,time);
        Log.e("ddddddddd",event.toString());

        lbdate.setText("Date :       "+event.getDate());
        lbtime.setText("Time :         "+event.getTime());
        lbadresse.setText("adresse :         "+event.getAdresse());

        btnParticipate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp = getActivity().getSharedPreferences("com.example.miniprojetandroid.shared", Context.MODE_PRIVATE);
                User u = new User();
                u.setId(Integer.parseInt(sp.getString("ID","")));
                event.setUser(u.getId());

                apiService.participate(event).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {



                        Toast.makeText(getActivity(),"Added to Participants!",Toast.LENGTH_SHORT).show();
                        EventsFragment f = new EventsFragment();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragmentsContainer, f )
                                .commit();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

            }
        });

        btnParticipants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Bundle bundle = new Bundle();
                bundle.putInt("event_id", event.getId());
                ParticipantsFragment f = new ParticipantsFragment();
                f.setArguments(bundle);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentsContainer, f )
                        .commit();
            }
        });




        return v;
    }





}