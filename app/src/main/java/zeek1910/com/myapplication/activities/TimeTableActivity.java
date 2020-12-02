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
import zeek1910.com.myapplication.models.LecturerTableItem;
import zeek1910.com.myapplication.db.RoomDB;

public class TimeTableActivity extends AppCompatActivity {

    public static final String KEY_FULLNAME = "KEY_FULLNAME";
    public static final String KEY_IS_SHOW_BUTTON = "KEY_IS_SHOW_BUTTON";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private TextView textView;
    private Button buttonFav;

    private boolean isFav = false;

    AppSettings appSettings;

    private String fullName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_time_table);

        appSettings = AppSettings.getInstance(getApplicationContext());

        buttonFav = findViewById(R.id.buttonAddToFav);

        Intent intent = getIntent();
        if(intent != null){
            fullName = intent.getStringExtra(KEY_FULLNAME);
            boolean isShowButton = intent.getBooleanExtra(KEY_IS_SHOW_BUTTON,true);
            if(!isShowButton) buttonFav.setVisibility(View.GONE);
            isFav = appSettings.checkFavorites(fullName);
            appSettings.setLastOwner(fullName);
        }

        if(isFav){
            buttonFav.setText("Dell form Fav");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    RoomDB database = RoomDB.getInstance(getBaseContext());
                    List<LecturerTableItem> data = database.tableDao().getSheduleByLecturer(fullName);
                    //adapter = new TimeTableActivityAdapter(data);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(adapter);
                        }
                    });
                }
            });
            thread.start();
        }else{
            buttonFav.setText("Add to Fav");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    RoomDB database = RoomDB.getInstance(getBaseContext());
                    List<LecturerTableItem> lecturerTableItems = new ArrayList<>();
                    List<LecturerTableItem> data = database.tableDao().getSheduleByLecturer(fullName);
                    for (LecturerTableItem item:data) {
                        //lecturerTableItems.add(new LecturerTableItem(item));
                    }
                    //adapter = new TimeTableActivityAdapter(lecturerTableItems);
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


        buttonFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFav){
                    appSettings.removeTimeTableFromFavorites(fullName);
                    buttonFav.setText("Add to Fav");
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            RoomDB database = RoomDB.getInstance(getBaseContext());
                            //database.tableDao().deleteFromSheduleTable(fullName);
                        }
                    });
                    thread.start();
                }else{
                    appSettings.addTimeTableToFavorites(fullName);
                    buttonFav.setText("Dell form Fav");
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            RoomDB database = RoomDB.getInstance(getBaseContext());
                            List<LecturerTableItem> tempTableItems = database.tableDao().getSheduleByLecturer(fullName);
                            for (LecturerTableItem item:tempTableItems) {
                                //database.tableDao().insertToMainTable(new LecturerTableItem(item));
                            }

                        }
                    });
                    thread.start();
                }
            }
        });

        textView = findViewById(R.id.textViewLecturerName);
        textView.setText(fullName);

        recyclerView = findViewById(R.id.recyclerViewFullTimeTable);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

    }

}