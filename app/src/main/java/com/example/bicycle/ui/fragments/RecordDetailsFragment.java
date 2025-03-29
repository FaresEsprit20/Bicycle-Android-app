package com.example.bicycle.ui.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.R;


public class RecordDetailsFragment extends Fragment {

    TextView lbadresse,lbtime,lbdistance,lbdaterecord;

    public RecordDetailsFragment() {
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
        View v = inflater.inflate(R.layout.fragment_record_details, container, false);
         lbadresse = v.findViewById(R.id.adresse);
         lbtime = v.findViewById(R.id.time);
         lbdistance = v.findViewById(R.id.distance);
         lbdaterecord = v.findViewById(R.id.daterecord);

        String adresse = getArguments().getString("adresse");
        String time = getArguments().getString("time");
        String distance = getArguments().getString("distance");
        String daterecord = getArguments().getString("daterecord");

        lbadresse.setText(" Adresse of recprd:     "+adresse);
        lbtime.setText(" Time in minutes:     "+time);
        lbdistance.setText("Distance in km :       "+distance);
        lbdaterecord.setText("Date of record:      "+daterecord);

        return v;

    }



}