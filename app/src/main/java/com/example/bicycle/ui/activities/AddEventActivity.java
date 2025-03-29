package com.example.bicycle.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.io.IOException;

public class AddEventActivity extends AppCompatActivity {

    public EditText TxtAdresse, TxtTitleEvt,TxtDate,TxtTime;
    public Button btnAdd;
    private SharedPreferences sp;
    private EventService apiService;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        TxtTitleEvt =findViewById(R.id.txtTitleEvt);
        TxtAdresse = findViewById(R.id.adresseEvt);
        TxtDate = findViewById(R.id.DateEvt);
        TxtTime = findViewById(R.id.TimeEvt);
        btnAdd = findViewById(R.id.btn_event_add);
        apiService = RetrofitClient.getClient().create(EventService.class);
        intent = new Intent(this, MainActivity.class);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validator()){
                    sp = getSharedPreferences("com.example.miniprojetandroid.shared", MODE_PRIVATE);
                    User u = new User();
                    u.setId(Integer.parseInt(sp.getString("ID","")));
                    Event e = new Event();
                    e.setTitle(TxtTitleEvt.getText().toString());
                    e.setAdresse(TxtAdresse.getText().toString());
                    e.setDate(TxtDate.getText().toString());
                    e.setTime(TxtTime.getText().toString());
                    e.setUser(u.getId());
                    apiService.addEvent(e).enqueue(new Callback<ResponseBody>() {
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
                                Toast.makeText(AddEventActivity.this,"Added to Events!",Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }



    public boolean validator() {
        if (TxtTime.getText().toString().length() == 0
                || TxtDate.getText().toString().length() == 0
                || TxtAdresse.getText().toString().length() == 0
                ) {
            Toast.makeText(this, "Data must not be empty !", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}