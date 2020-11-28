package zeek1910.com.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zeek1910.com.myapplication.AppSettings;
import zeek1910.com.myapplication.R;
import zeek1910.com.myapplication.adapters.TimeTableActivityAdapter;
import zeek1910.com.myapplication.db.RoomDB;
import zeek1910.com.myapplication.fragments.SearchFragment;
import zeek1910.com.myapplication.models.TableItem;

public class TimeTableActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<TableItem> data;

    private TextView textView;
    private Button buttonFav;

    private boolean isFav = false;

    AppSettings appSettings;

    private String own = "";
    private String fullName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_time_table);

        appSettings = new AppSettings(getApplicationContext());

        buttonFav = findViewById(R.id.buttonAddToFav);

        Intent intent = getIntent();
        if(intent != null){
            own = intent.getStringExtra(SearchFragment.KEY_OWNER);
            fullName = intent.getStringExtra(SearchFragment.KEY_FULLNAME);
            isFav = appSettings.checkFavorites(fullName);
        }

        if(isFav){
            buttonFav.setText("Dell form Fav");
        }else{
            buttonFav.setText("Add to Fav");
        }


        buttonFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFav){
                    appSettings.removeTimeTableFromFavorites(fullName);
                    buttonFav.setText("Add to Fav");
                }else{
                    appSettings.addTimeTableToFavorites(fullName);
                    buttonFav.setText("Dell form Fav");
                }
            }
        });

        textView = findViewById(R.id.textViewLecturerName);
        textView.setText(fullName);

        data = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerViewFullTimeTable);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                RoomDB database = RoomDB.getInstance(getBaseContext());
                data = database.tableDao().getSheduleByLecturer(own);
                adapter = new TimeTableActivityAdapter(data);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        });
        thread.start();

    }
}