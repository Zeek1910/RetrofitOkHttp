package zeek1910.com.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;


import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import zeek1910.com.myapplication.AppSettings;
import zeek1910.com.myapplication.R;
import zeek1910.com.myapplication.db.RoomDB;
import zeek1910.com.myapplication.models.Lecturer;
import zeek1910.com.myapplication.models.LecturerTableItem;


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
        //navHostFragment.getNavController().navigate(R.id.favoritesFragment);

        //AppSettings appSettings = new AppSettings(this);
        //appSettings.setDefaultOwner("Пявка Євгеній Валентинович");

        isLowerWeek();
    }

    public boolean isLowerWeek(){
        Calendar calendar= Calendar.getInstance();
        calendar.set(2021,1,18);
        calendar.get(Calendar.DAY_OF_YEAR);
        Log.d("devcpp","");
        return true;
    }



}