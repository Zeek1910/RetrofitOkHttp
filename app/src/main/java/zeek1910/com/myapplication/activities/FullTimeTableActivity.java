package zeek1910.com.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import zeek1910.com.myapplication.R;
import zeek1910.com.myapplication.adapters.FullTimeTableRecyclerViewAdapter;
import zeek1910.com.myapplication.db.RoomDB;
import zeek1910.com.myapplication.fragments.SearchFragment;
import zeek1910.com.myapplication.models.TableItem;

public class FullTimeTableActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<TableItem> data;

    private String own = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_time_table);

        Intent intent = getIntent();
        if(intent != null){
            own = intent.getStringExtra(SearchFragment.KEY_OWNER);
        }

        data = new ArrayList<>();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("devcpp", ""+own);
                RoomDB database = RoomDB.getInstance(getBaseContext());
                data = database.tableDao().getAll();
                Log.d("devcpp", ""+data.size());
                recyclerView.setAdapter(adapter);
            }
        });
        thread.start();

        recyclerView = findViewById(R.id.recyclerViewFullTimeTable);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FullTimeTableRecyclerViewAdapter(data);


    }
}