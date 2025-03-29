package com.example.bicycle.ui.fragments;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.R;
import com.example.bicycle.Retrofit.RentService;
import com.example.bicycle.Retrofit.RetrofitClient;
import com.example.bicycle.models.Bike;
import com.example.bicycle.models.Location;
import com.example.bicycle.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RentFragment extends Fragment {

    Button btnRent;
    EditText AdresseLocation, Hours;
    TextView TotalPrice;
    private RentService apiService;

    public RentFragment() {
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
        View v = inflater.inflate(R.layout.fragment_rent, container, false);
        // Inflate the layout for this fragment
        apiService = RetrofitClient.getClient().create(RentService.class);
        btnRent = v.findViewById(R.id.btn_rent);
        AdresseLocation = v.findViewById(R.id.txtAdresselocation);
        Hours = v.findViewById(R.id.txtHoures);
        TotalPrice = v.findViewById(R.id.txtTotalPrice);
        String  bikeprice = getArguments().getString("price");

        btnRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  bikeprice = getArguments().getString("price");
                String  totalprice = String.valueOf(  Integer.parseInt(bikeprice) *  Integer.parseInt(Hours.getText().toString())  ) ;

                int userid = getArguments().getInt("user_id");
                int bikeid = getArguments().getInt("bike_id");
                User u = new User();
                u.setId(userid);
                Bike b = new Bike();
                b.setId(bikeid);
                Location loc = new Location(AdresseLocation.getText().toString(),Hours.getText().toString(), totalprice,  bikeid, userid);
                loc.setTotalprice(totalprice);
                Log.e("LOCATION",loc.toString());
                TotalPrice.setText(totalprice);
                Log.e("LOCATION",totalprice);

                apiService.addRent(loc).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        String message = null;
                        int status = response.code();
                            Log.e("aaaaaaaaaaaaa", "response : " + message);
                            if(status == 200){
                                Toast.makeText(getActivity()," Rent Added Successfully !  Visit one of our shops these 48 hours to get your Bike ! ",Toast.LENGTH_LONG).show();
                                FragmentTwo f = new FragmentTwo();
                                getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragmentsContainer, f)
                                        .commit();
                            }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

            }
        });

        return v;
    }



}