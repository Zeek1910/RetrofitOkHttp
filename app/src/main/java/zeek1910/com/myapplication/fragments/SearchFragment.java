package zeek1910.com.myapplication.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zeek1910.com.myapplication.interfaces.APIInterface;
import zeek1910.com.myapplication.interfaces.OnRecyclerViewItemClickListener;
import zeek1910.com.myapplication.R;
import zeek1910.com.myapplication.adapters.SearchRecyclerViewAdapter;
import zeek1910.com.myapplication.models.Group;
import zeek1910.com.myapplication.models.Lecturer;

public class SearchFragment extends Fragment implements View.OnClickListener, OnRecyclerViewItemClickListener {


    public static final String BASE_URL = "https://profkomstud.khai.edu/";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterLecturers;
    private RecyclerView.Adapter adapterGroups;
    private RecyclerView.LayoutManager layoutManager;

    private Button btnLecturer, btnGroup;
    private EditText editTextFaculty;
    private ProgressBar progressBar;

    private List<Lecturer> lecturers;
    private List<Group> groups;

    private Retrofit retrofit;
    private APIInterface service;
    Callback<List<Lecturer>> callback1;
    Callback<List<Group>> callback2;

    private int currentTypeAdapter = -1;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(APIInterface.class);

        lecturers = new ArrayList<>();
        groups = new ArrayList<>();

        adapterLecturers = new SearchRecyclerViewAdapter(lecturers, groups, SearchRecyclerViewAdapter.TYPE_LECTURERS,this::onItemClick);
        adapterGroups = new SearchRecyclerViewAdapter(lecturers, groups, SearchRecyclerViewAdapter.TYPE_GROUPS,this::onItemClick);

        callback1 = new Callback<List<Lecturer>>() {
            @Override
            public void onResponse(Call<List<Lecturer>> call, Response<List<Lecturer>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                lecturers.addAll(response.body());
                recyclerView.setAdapter(adapterLecturers);
                currentTypeAdapter = SearchRecyclerViewAdapter.TYPE_LECTURERS;
            }

            @Override
            public void onFailure(Call<List<Lecturer>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.d("devcpp", t.getMessage());
            }
        };

        callback2 = new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                groups.addAll(response.body());
                recyclerView.setAdapter(adapterGroups);
                currentTypeAdapter = SearchRecyclerViewAdapter.TYPE_GROUPS;
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.d("devcpp", t.getMessage());
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_search, container, false);

        btnLecturer = view.findViewById(R.id.button2);
        btnLecturer.setOnClickListener(this);
        btnGroup = view.findViewById(R.id.button);
        btnGroup.setOnClickListener(this);

        editTextFaculty = view.findViewById(R.id.editTextFaculty);

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        recyclerView = view.findViewById(R.id.SearchRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        int faculty = Integer.parseInt(editTextFaculty.getText().toString());
        switch (v.getId()){
            case R.id.button2:
                lecturers.clear();
                service.getLecturersByFaculty(faculty).enqueue(callback1);
                break;
            case R.id.button:
                groups.clear();
                service.getGroupsByFaculty("1|"+faculty).enqueue(callback2);
                break;
        }


    }

    @Override
    public void onItemClick(int position) {
        if (currentTypeAdapter == SearchRecyclerViewAdapter.TYPE_LECTURERS){
            Log.d("devcpp",lecturers.get(position).toString());
        }

        if (currentTypeAdapter == SearchRecyclerViewAdapter.TYPE_GROUPS){
            Log.d("devcpp",groups.get(position).toString());
        }
    }
}