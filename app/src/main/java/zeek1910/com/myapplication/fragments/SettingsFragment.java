package zeek1910.com.myapplication.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import zeek1910.com.myapplication.R;
import zeek1910.com.myapplication.db.RoomDB;


public class SettingsFragment extends Fragment implements View.OnClickListener{

    private Button btnClearDatabase;

    public SettingsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        btnClearDatabase = view.findViewById(R.id.button_clear_database);
        btnClearDatabase.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_clear_database:
                clearDataBase();
                break;
        }
    }

    private void clearDataBase() {
        RoomDB database = RoomDB.getInstance(getContext());
        new Thread(new Runnable() {
            @Override
            public void run() {
                database.tableDao().clearTable();
            }
        }).start();
        Toast.makeText(getContext(),"База даних очищена", Toast.LENGTH_SHORT).show();
    }
}