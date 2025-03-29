package com.example.bicycle.ui.fragments;

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
import com.example.bicycle.Retrofit.RentService;
import com.example.bicycle.models.Bike;
import com.example.bicycle.models.Location;
import com.example.bicycle.models.User;

import java.io.IOException;



public class RentDetailsFragment extends Fragment {

    private RentService apiService;
    Button btnDelete;
    TextView txtDatelocation, txtAdresselocation, txtHours, txtTotalprice, BikeShop , txtBikemodel,txtBiketype,txtBikeprice;

    public RentDetailsFragment() {
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
        View v = inflater.inflate(R.layout.fragment_rent_details, container, false);
        // Inflate the layout for this fragment
        apiService = RetrofitClient.getClient().create(RentService.class);
           btnDelete = v.findViewById(R.id.btnDelete);
           txtDatelocation = v.findViewById(R.id.datelocation);
           txtAdresselocation = v.findViewById(R.id.adresselocation);
           txtBikemodel = v.findViewById(R.id.bikemodel);
           txtBiketype = v.findViewById(R.id.biketype);
           txtBikeprice = v.findViewById(R.id.bikeprice);
           txtHours = v.findViewById(R.id.hours);
           txtTotalprice = v.findViewById(R.id.pricetotal);
           BikeShop = v.findViewById(R.id.bikeshop);
           Bike bike;
           User user = new User();
        final Location rent;
        int id = getArguments().getInt("location_id");
        int userid = getArguments().getInt("user_id");
        int bikeid = getArguments().getInt("bike_id");
        String model = getArguments().getString("model");
        String type = getArguments().getString("type");
        Log.e("type",type);
        String price = getArguments().getString("price");
        String totalprice = getArguments().getString("totalprice");
        String datelocation = getArguments().getString("datelocation");
        String adresselocation = getArguments().getString("adresselocation");
        String image = getArguments().getString("image");
        String title = getArguments().getString("title");
        bike = new Bike(bikeid,model,type,price,image);
        user.setId(userid);
        rent = new Location(adresselocation, "22", bike, user);
        rent.setDateLocation(datelocation);
        rent.setId(id);

        Log.e("ddddddddd",bike.toString());

        txtDatelocation.setText("Date de Location :       "+rent.getDateLocation());
        txtAdresselocation.setText("Adresse de Location :       "+rent.getAddressLocation());
        txtHours.setText("Total Hours:   "+rent.getHours());
        txtTotalprice.setText(" Total Price  :"+totalprice);
        txtBikemodel.setText("Bike Model :     "+rent.getBike().getModel());
        txtBiketype.setText("Bike Type :          "+rent.getBike().getType());
        txtBikeprice.setText("Bike price per hour:   "+rent.getBike().getPrice()+"  DT");
        BikeShop.setText("SHOP :        "+title);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(),"Deleted Rent !",Toast.LENGTH_SHORT).show();
                Call<ResponseBody> call = apiService.deleteRent(rent);
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
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("ERROR: ", t.getMessage());
                    }
                });

                FragmentTwo f = new FragmentTwo();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentsContainer, f)
                        .commit();
            }
        });

        return v;
    }



}