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
import android.widget.EditText;
import android.widget.Toast;

import com.example.R;
import com.example.bicycle.Retrofit.TrackService;
import com.example.bicycle.models.Record;
import com.example.bicycle.models.User;

import java.io.IOException;



public class RecordFragment extends Fragment {

    private SharedPreferences sp;
    Button btnRecord;
    EditText txtAddress, txtDistance, txtTime;
    private TrackService apiService;


    public RecordFragment() {
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
        View v = inflater.inflate(R.layout.fragment_record, container, false);
        // Inflate the layout for this fragment
        apiService = RetrofitClient.getClient().create(TrackService.class);
       btnRecord = v.findViewById(R.id.btn_record);
       txtAddress = v.findViewById(R.id.adresse);
       txtDistance = v.findViewById(R.id.distance);
       txtTime = v.findViewById(R.id.Time);

       btnRecord.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               sp = getActivity().getSharedPreferences("com.example.miniprojetandroid.shared", Context.MODE_PRIVATE);
               User u = new User();
               u.setId(Integer.parseInt(sp.getString("ID","")));
           Record r = new Record();
           r.setUser_id(String.valueOf(u.getId()));
           r.setDistance(txtDistance.getText().toString());
           r.setAddress(txtAddress.getText().toString());
           r.setTime(txtTime.getText().toString());


               apiService.addRecord(r).enqueue(new Callback<ResponseBody>() {
                   @Override
                   public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                       String message = null;
                       try {
                           message = response.body().string();
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                       int status = response.code();
                       if(message.contains("OK")) {
                           Log.e("aaaaaaaaaaaaa", "response : " + message);
                           Toast.makeText(getActivity(),"Added to Records!",Toast.LENGTH_SHORT).show();
                           RecordListFragment f = new RecordListFragment();
                           getActivity().getSupportFragmentManager()
                                   .beginTransaction()
                                   .replace(R.id.fragmentsContainer, f )
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