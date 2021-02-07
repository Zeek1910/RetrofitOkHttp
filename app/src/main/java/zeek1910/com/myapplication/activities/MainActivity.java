package zeek1910.com.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;


import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.Calendar;

import zeek1910.com.myapplication.AppSettings;
import zeek1910.com.myapplication.R;
import zeek1910.com.myapplication.db.RoomDB;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private NavHostFragment navHostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavigationUI.setupWithNavController(bottomNavigationView,navHostFragment.getNavController());
        navHostFragment.getNavController().navigate(R.id.searchFragment);

    }



}