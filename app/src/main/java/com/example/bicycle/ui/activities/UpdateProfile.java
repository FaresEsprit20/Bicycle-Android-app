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
import com.example.bicycle.Retrofit.UserService;
import com.example.bicycle.models.User;

import java.io.IOException;



public class UpdateProfile extends AppCompatActivity {

    private EditText txtLastName, txtFirstName, txtEmail, txtPhoneNumber,txtpassword,txtpasswordRepeat;
    private UserService apiService;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        apiService = RetrofitClient.getClient().create(UserService.class);
        txtLastName = findViewById(R.id.ed_last_name);
        txtFirstName = findViewById(R.id.ed_first_name);
        txtEmail = findViewById(R.id.ed_email);
        txtPhoneNumber = findViewById(R.id.ed_phone_number);
        txtpassword = findViewById(R.id.txtpassword);
        txtpasswordRepeat = findViewById(R.id.txtpasswordRepeat);
        sp = getSharedPreferences("com.example.miniprojetandroid.shared", MODE_PRIVATE);
        Button btn_next = findViewById(R.id.btn_save);
        checkAndFillData(getIntent());

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(validator()){

                    User u = new User();
                    u.setId(Integer.parseInt(sp.getString("ID","")));
                    u.setName(txtFirstName.getText().toString());
                    u.setLastName(txtLastName.getText().toString());
                    u.setEmail(txtEmail.getText().toString());
                    u.setPassword(txtpassword.getText().toString());
                    u.setPhone(txtPhoneNumber.getText().toString());

                    apiService.updateUser(u).enqueue(new Callback<ResponseBody>() {
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
                                Toast.makeText(UpdateProfile.this, "Profile Updated Successfully !", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.putExtra(SignInActivity.LNAME_KEY, txtLastName.getText().toString());
                                intent.putExtra(SignInActivity.FNAME_KEY, txtFirstName.getText().toString());
                                intent.putExtra(SignInActivity.EMAIL_KEY, txtEmail.getText().toString());
                                intent.putExtra(SignInActivity.PHONE_KEY, txtPhoneNumber.getText().toString());
                                setResult(RESULT_OK,intent);
                                finish();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });

                }


            }
        });

    }


    public boolean validator() {

        if (txtFirstName.getText().toString().length() == 0
                || txtLastName.getText().toString().length() == 0
                || txtEmail.getText().toString().length() == 0
                || txtpassword.getText().toString().length() == 0
                || txtpasswordRepeat.getText().toString().length() == 0
                || txtPhoneNumber.getText().toString().length() == 0 ) {
            Toast.makeText(this, "Data must not be empty !", Toast.LENGTH_SHORT).show();
            return false;
        }

        if ((!txtEmail.getText().toString().contains("@")) && (txtEmail.getText().toString().length() <5   )) {
            Toast.makeText(this, "Email is not valid !", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!txtpassword.getText().toString().equals(txtpasswordRepeat.getText().toString())   ) {
            Toast.makeText(this, "Password mismatch !", Toast.LENGTH_SHORT).show();
            return false;
        }

        if ( txtpassword.getText().toString().length() < 6 || txtpasswordRepeat.getText().toString().length() > 30  ) {
            Toast.makeText(this, "Password must be between 6 and  30 characters !", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (  txtPhoneNumber.getText().toString().length() != 8  ) {
            Toast.makeText(this, "Phone must be 8 characters !", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }



    public void checkAndFillData(Intent intent){

        if( intent.hasExtra(SignInActivity.FNAME_KEY) ){
            txtFirstName.setText( intent.getStringExtra(SignInActivity.FNAME_KEY));
        }

        if( intent.hasExtra(SignInActivity.LNAME_KEY) ){
            txtLastName.setText( intent.getStringExtra(SignInActivity.LNAME_KEY));
        }

        if( intent.hasExtra(SignInActivity.EMAIL_KEY) ){
            txtEmail.setText( intent.getStringExtra(SignInActivity.EMAIL_KEY));
        }

        if( intent.hasExtra(SignInActivity.PASSWORD_KEY) ){
            txtpassword.setText( intent.getStringExtra(SignInActivity.PASSWORD_KEY));
        }

        if( intent.hasExtra(SignInActivity.PASSWORD_KEY) ){
            txtpasswordRepeat.setText( intent.getStringExtra(SignInActivity.PASSWORD_KEY));
        }

        if( intent.hasExtra(SignInActivity.PHONE_KEY) ){
            txtPhoneNumber.setText( intent.getStringExtra(SignInActivity.PHONE_KEY));
        }
    }

    @Override
    public void finish() {
        super.finish();
    }


}