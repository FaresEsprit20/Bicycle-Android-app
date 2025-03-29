package com.example.bicycle.ui.activities;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.R;
import com.example.bicycle.Retrofit.RetrofitClient;
import com.example.bicycle.Retrofit.UserService;
import com.example.bicycle.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.Calendar;
import java.util.Date;



public class SignInActivity extends AppCompatActivity {

    private UserService apiService;
    public static final String FNAME_KEY = "FNAME";
    public static final String LNAME_KEY = "LNAME";
    public static final String EMAIL_KEY = "EMAIL";
    public static final String PWD_KEY = "PASSWORD";
    public static final String PHONE_KEY = "PHONE";
    public static final String PASSWORD_KEY = "PASSWORD";
    public static final String CHECKED_KEY = "CHECKED";


        public static final String sharedPrefFile = "com.example.miniprojetandroid.shared";
        private SharedPreferences mPreferences;
        EditText email, password;
        private CheckBox cbRememberMe;
        public static User currentUser;
         Intent intent;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_in);

            Date currentTime = Calendar.getInstance().getTime();
            Log.e("date",currentTime.toString());
            apiService = RetrofitClient.getClient().create(UserService.class);
            intent = new Intent(this, MainActivity.class);
            email = (EditText) findViewById(R.id.edSignInEmail);
            password = (findViewById(R.id.edSignInPassword));
            cbRememberMe = findViewById(R.id.cbRememberMe);
            //TODO 1 get SharedPref And set EditText
            mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
            email.setText( mPreferences.getString(EMAIL_KEY,"") );
            password.setText( mPreferences.getString(PWD_KEY,"") );
            cbRememberMe.setChecked( mPreferences.getBoolean(CHECKED_KEY, false) );
        }

        public void goToSignUp(View view) {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        }

        public void SignIn(View view) {

            if(validator()) {
                //TODO 3 Save SharedPref
                if (cbRememberMe.isChecked()){
                    SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                    preferencesEditor.putString(EMAIL_KEY, email.getText().toString());
                    preferencesEditor.putString(PWD_KEY, password.getText().toString());
                    preferencesEditor.putBoolean(CHECKED_KEY, true);
                    preferencesEditor.apply();
                }else {
                    SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                    preferencesEditor.putString(EMAIL_KEY, "");
                    preferencesEditor.putString(PWD_KEY, "");
                    preferencesEditor.putBoolean(CHECKED_KEY, false);
                    preferencesEditor.clear();
                    preferencesEditor.apply();
                }

                User u = new User();
                u.setEmail(email.getText().toString());
                u.setPassword(password.getText().toString());

                apiService.loginUser(u).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                            User message = (User)response.body();
                             String rep = response.body().toString();
                            if(message.getEmail().equals(email.getText().toString())) {
                            currentUser = response.body();

                                SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                                preferencesEditor.putString("ID", String.valueOf(currentUser.getId()));
                                preferencesEditor.putString(EMAIL_KEY, currentUser.getEmail());
                                preferencesEditor.putString(PWD_KEY, currentUser.getPassword());
                                preferencesEditor.apply();

                            Log.e("aaaaaaaaaaaaa", "response : " + message);
                            Toast.makeText(SignInActivity.this, "SIGN In SUCCESSFUL !", Toast.LENGTH_LONG).show();
                            intent.putExtra(FNAME_KEY, currentUser.getName());
                            intent.putExtra(LNAME_KEY, currentUser.getLastName());
                            intent.putExtra(EMAIL_KEY, currentUser.getEmail());
                            intent.putExtra(PHONE_KEY, currentUser.getPhone());
                            intent.putExtra(PASSWORD_KEY, currentUser.getPassword());
                            Log.d("User", String.valueOf(currentUser.toString()));
                            startActivity(intent);
                            finish();

                        }else {
                            Log.e("aaaaaaaaaaaaa", "response : " + message);
                            Toast.makeText(SignInActivity.this, "WRONG EMAIL OR PASSWORD !", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
         }

        public void SignInFB(View view) {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        }

    public boolean validator(){
        if (email.getText().toString().length() == 0
                || password.getText().toString().length() == 0 ){
            Toast.makeText(this, "Email and password must not be empty !", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!email.getText().toString().contains("@")){
            Toast.makeText(this, "Email is not valid !", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}
