package zeek1910.com.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;


import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.List;

import zeek1910.com.myapplication.R;
import zeek1910.com.myapplication.SheduleParcer;
import zeek1910.com.myapplication.db.RoomDB;
import zeek1910.com.myapplication.models.TableItem;


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

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                RoomDB database = RoomDB.getInstance(getBaseContext());
                //database.tableDao().clearTable();
                List<TableItem> items = database.tableDao().getAll();
                Log.d("devcpp", ""+items.size());
            }
        });
        thread.start();


        //SheduleParcer sheduleParcer = new SheduleParcer(this);
        //sheduleParcer.execute("barsov-valerij-igorovich");


    }

}