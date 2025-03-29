package com.example.bicycle.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.R;
import com.example.bicycle.models.Bike;


public class FavDetailsFragment extends Fragment {


    TextView lbmodel,lbtype,lbprice;
    private SharedPreferences sp;

    public static Bike bike;

    public FavDetailsFragment() {
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
        View v = inflater.inflate(R.layout.fragment_fav_details, container, false);

        lbmodel = v.findViewById(R.id.model);
        lbtype = v.findViewById(R.id.type);
        lbprice = v.findViewById(R.id.price);

        int id = getArguments().getInt("bike_id");
        String model = getArguments().getString("model");
        String type = getArguments().getString("type");
        String price = getArguments().getString("price");
        String image = getArguments().getString("image");
        bike = new Bike(id,model,type,price,image);
        Log.e("ddddddddd",bike.toString());

        lbmodel.setText("Model :       "+bike.getModel());
        lbtype.setText("Bike :         "+bike.getType());
        lbprice.setText("Price per hour :         "+bike.getPrice());


        return v;
    }


}