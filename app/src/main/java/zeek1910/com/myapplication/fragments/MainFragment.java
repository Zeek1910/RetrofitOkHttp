package zeek1910.com.myapplication.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Calendar;
import java.util.List;

import zeek1910.com.myapplication.AppSettings;
import zeek1910.com.myapplication.R;
import zeek1910.com.myapplication.adapters.TimeTableAdapter;
import zeek1910.com.myapplication.db.RoomDB;
import zeek1910.com.myapplication.models.LecturerTableItem;


public class MainFragment extends Fragment {

    private List<LecturerTableItem> data;

    private RecyclerView recyclerView;
    private LinearLayout panel;
    private TimeTableAdapter adapter;

    public MainFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = view.findViewById(R.id.MainRecyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        panel = view.findViewById(R.id.upPanel);

        return view;
    }


    void loadData(){
        AppSettings appSettings = new AppSettings(getContext());
        String defaultOwner = appSettings.getDefaultOwner();
        RoomDB database = RoomDB.getInstance(getContext());
        new Thread(new Runnable() {
            @Override
            public void run() {
                int currentDay = getCurrentDay();
                int currentTime = getCurrentTime();
                if (currentTime >= 16){
                    if (++currentDay>5){
                        currentDay = 2;
                    }
                }
                //Log.d("devcpp","defaultOwner->"+defaultOwner+" getDayNameByNumber(currentDay)->"+getDayNameByNumber(currentDay));
                data = database.tableDao().getSheduleByLecturerByDay(defaultOwner,getDayNameByNumber(currentDay));
                //Log.d("devcpp",""+data.size());
                adapter = new TimeTableAdapter(data);
                //adapter.notifyDataSetChanged();
            }
        }).start();
    }

    String getDayNameByNumber(int dayOfWeek){
        String dayName = "";
        switch (dayOfWeek){
            case 1:
                dayName = "Неділя";
                break;
            case 2:
                dayName = "Понеділок";
                break;
            case 3:
                dayName = "Вівторок";
                break;
            case 4:
                dayName = "Середа";
                break;
            case 5:
                dayName = "Четвер";
                break;
            case 6:
                dayName = "П'ятниця";
                break;
            case 7:
                dayName = "Субота";
                break;
        }
        return dayName;
    }

    int getCurrentTime(){
        Calendar calendar= Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    int getCurrentDay(){
        Calendar calendar= Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
}