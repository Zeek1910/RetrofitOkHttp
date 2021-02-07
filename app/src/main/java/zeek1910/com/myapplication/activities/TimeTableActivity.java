package zeek1910.com.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zeek1910.com.myapplication.AppSettings;
import zeek1910.com.myapplication.R;
import zeek1910.com.myapplication.adapters.TimeTableAdapter;
import zeek1910.com.myapplication.models.LecturerTableItem;
import zeek1910.com.myapplication.db.RoomDB;

public class TimeTableActivity extends AppCompatActivity {

    public static final String KEY_FULLNAME = "KEY_FULLNAME";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private TextView textView;

    private TimeTableAdapter adapter;

    private List<LecturerTableItem> data;

    AppSettings appSettings;

    private String fullName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        appSettings = new AppSettings(this);

        Intent intent = getIntent();
        if(intent != null){
            fullName = intent.getStringExtra(KEY_FULLNAME);
            //appSettings.setLastOwner(fullName);
        }

        textView = findViewById(R.id.textViewLecturerName);
        textView.setText(fullName);

        recyclerView = findViewById(R.id.recyclerViewFullTimeTable);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        getDatafromDB();
    }

    void getDatafromDB(){
        RoomDB database = RoomDB.getInstance(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                data = database.tableDao().getSheduleByLecturer(fullName);
                adapter = new TimeTableAdapter(data);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        }).start();

    }

}