package com.example.bicycle.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.R;
import com.example.bicycle.Retrofit.UserService;
import com.example.bicycle.models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SignUpActivity extends AppCompatActivity {

    private UserService apiService;
    private EditText edSignUpFirstName, edSignUpLastName, edSignUpEmail, edSignUpPassword, edSignUpPhoneNumber, edSignUpPasswordRepeat;
    List<User> users = new ArrayList<User>();
    List<User> result = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        apiService = RetrofitClient.getClient().create(UserService.class);
        edSignUpFirstName = findViewById(R.id.edSignUpFirstName);
        edSignUpLastName = findViewById(R.id.edSignUpLastName);
        edSignUpEmail = findViewById(R.id.edSignUpEmail);
        edSignUpPassword = findViewById(R.id.edSignUpPassword);
        edSignUpPasswordRepeat = findViewById(R.id.edSignUpPasswordRepeat);
        edSignUpPhoneNumber = findViewById(R.id.edSignUpPhoneNumber);
        getUsersList();

    }


    public void getUsersList(){
       /* Call<List<User>> call = apiService.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                  result  = response.body();

                    for(User user: result){
                        User u = new User();
                        u.setId(user.getId());
                        u.setName(user.getName());
                        u.setLastName(user.getLastName());
                        u.setEmail(user.getEmail());
                        u.setPassword(user.getPassword());
                        u.setPhone(user.getPhone());
                        users.add(u);
                    }
                    Log.e("USERS LIST", users.toString());
                    //listView.setAdapter(new UserAdapter(MainActivity.this, R.layout.list_user, list));
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });*/

    }



    public void doSignUp(View view) {
        if (validator()) {
            User u = new User();
            u.setName(edSignUpFirstName.getText().toString());
            u.setLastName(edSignUpLastName.getText().toString());
            u.setEmail(edSignUpEmail.getText().toString());
            u.setPassword(edSignUpPassword.getText().toString());
            u.setPhone(edSignUpPhoneNumber.getText().toString());
            apiService.createUser(u).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    String message = null;
                    try {
                        message = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int status = response.code();
                    if(message.contains("EXIST")) {
                        Log.e("aaaaaaaaaaaaa", "response : " + message);
                        Toast.makeText(SignUpActivity.this, "USER ALREADT EXIST !", Toast.LENGTH_SHORT).show();
                    }else if ( message.contains("OK") ){
                        Log.e("aaaaaaaaaaaaa", "response : " + message);
                        Toast.makeText(SignUpActivity.this, "SIGN UP SUCCESSFUL !", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }


    public boolean validator() {

        if (edSignUpFirstName.getText().toString().length() == 0
                || edSignUpLastName.getText().toString().length() == 0
                || edSignUpEmail.getText().toString().length() == 0
                || edSignUpPassword.getText().toString().length() == 0
                || edSignUpPhoneNumber.getText().toString().length() == 0 ) {
            Toast.makeText(this, "Data must not be empty !", Toast.LENGTH_SHORT).show();
            return false;
        }

        if ((!edSignUpEmail.getText().toString().contains("@")) && (edSignUpEmail.getText().toString().length() <5   )) {
            Toast.makeText(this, "Email is not valid !", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!edSignUpPassword.getText().toString().equals(edSignUpPasswordRepeat.getText().toString())   ) {
            Toast.makeText(this, "Password mismatch !", Toast.LENGTH_SHORT).show();
            return false;
        }

        if ( edSignUpPassword.getText().toString().length() < 6 || edSignUpPassword.getText().toString().length() >= 30  ) {
            Toast.makeText(this, "Password must be between 6 and  30 characters !", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (  edSignUpPhoneNumber.getText().toString().length() != 8  ) {
            Toast.makeText(this, "Phone must be 8 characters !", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}



