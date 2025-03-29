package com.example.bicycle.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.R;
import com.example.bicycle.ui.fragments.EventsFragment;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        EventsFragment f = new EventsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentsContainer,f)
                .commit();
    }





}