package com.example.bicycle.ui.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import com.example.R;
import com.example.bicycle.ui.fragments.DetailsFragment;
import com.example.bicycle.ui.fragments.FragmentOne;
import com.example.bicycle.ui.fragments.FragmentThree;
import com.example.bicycle.ui.fragments.FragmentTwo;
import com.example.bicycle.ui.fragments.RentFragment;
import com.example.bicycle.ui.fragments.ShopsFragment;


public class RentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);
    }

    public void showFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentsContainer,fragment)
                .commit();
    }

    public void openFragOne(View view) {
        showFragment(new FragmentOne());
    }

    public void openFragTwo(View view) {

        showFragment(new FragmentTwo());
    }

    public void openFragShops(View view) {

        showFragment(new ShopsFragment());
    }

    public void openFragThree(View view) {
        showFragment(new FragmentThree());
    }

    public void openFragRent(View view) {
        showFragment(new RentFragment());
    }

    public void openDetailsFrag(View view) {
        showFragment(new DetailsFragment());
    }


}
