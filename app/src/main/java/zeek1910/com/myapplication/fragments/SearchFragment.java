package zeek1910.com.myapplication.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.button.MaterialButtonToggleGroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zeek1910.com.myapplication.activities.TimeTableActivity;
import zeek1910.com.myapplication.db.RoomDB;
import zeek1910.com.myapplication.db.TempTableItem;
import zeek1910.com.myapplication.interfaces.APIInterface;
import zeek1910.com.myapplication.interfaces.OnRecyclerViewItemClickListener;
import zeek1910.com.myapplication.R;
import zeek1910.com.myapplication.adapters.SearchFragmentAdapter;
import zeek1910.com.myapplication.models.Group;
import zeek1910.com.myapplication.models.Lecturer;

public class SearchFragment extends Fragment implements View.OnClickListener, OnRecyclerViewItemClickListener {

    public static final String BASE_URL = "https://profkomstud.khai.edu/";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterLecturers;
    private RecyclerView.Adapter adapterGroups;
    private RecyclerView.LayoutManager layoutManager;

    private Button btnLecturer, btnGroup;
    private ProgressBar progressBar;
    private AutoCompleteTextView autoCompleteTextViewFacultys;
    private AutoCompleteTextView autoCompleteTextViewOwners;

    ArrayAdapter<String> adapterOwnersList;

    private List<Lecturer> lecturers;
    private List<Group> groups;
    private List<TempTableItem> data;

    private Retrofit retrofit;
    private APIInterface service;
    Callback<List<Lecturer>> callback1;
    Callback<List<Group>> callback2;

    private int currentTypeAdapter = -1;

    private int faculty = -1;

    String own, fullName;

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

        data = new ArrayList<>();

        adapterOwnersList = new ArrayAdapter<String>(getContext(),R.layout.drop_down_menu_item);

        adapterLecturers = new SearchFragmentAdapter(lecturers, groups, SearchFragmentAdapter.TYPE_LECTURERS,this::onItemClick);
        adapterGroups = new SearchFragmentAdapter(lecturers, groups, SearchFragmentAdapter.TYPE_GROUPS,this::onItemClick);

        callback1 = new Callback<List<Lecturer>>() {
            @Override
            public void onResponse(Call<List<Lecturer>> call, Response<List<Lecturer>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                lecturers.addAll(response.body());
                recyclerView.setAdapter(adapterLecturers);
                currentTypeAdapter = SearchFragmentAdapter.TYPE_LECTURERS;

                for (Lecturer lecturer:lecturers) {
                    adapterOwnersList.add(lecturer.getActName());
                }
                adapterOwnersList.notifyDataSetChanged();
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
                currentTypeAdapter = SearchFragmentAdapter.TYPE_GROUPS;
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

        autoCompleteTextViewFacultys = view.findViewById(R.id.facultySelect);
        ArrayAdapter<String> adapterFacultysList = new ArrayAdapter<String>(getContext(),R.layout.drop_down_menu_item);

        for(int i = 1; i <= 8; i++){
            adapterFacultysList.add("Факультет "+i);
        }
        autoCompleteTextViewFacultys.setAdapter(adapterFacultysList);
        autoCompleteTextViewFacultys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                faculty = i+1;
            }
        });

        autoCompleteTextViewOwners = view.findViewById(R.id.ownerSelect);
        autoCompleteTextViewOwners.setAdapter(adapterOwnersList);

        btnLecturer = view.findViewById(R.id.button2);
        btnLecturer.setOnClickListener(this);
        btnGroup = view.findViewById(R.id.button);
        btnGroup.setOnClickListener(this);

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
        switch (v.getId()){
            case R.id.button2:
                if (faculty != -1){
                    lecturers.clear();
                    service.getLecturersByFaculty(faculty).enqueue(callback1);
                }
                break;
            case R.id.button:
                if (faculty != -1) {
                    groups.clear();
                    service.getGroupsByFaculty("1|" + faculty).enqueue(callback2);
                }
                break;
        }


    }

    @Override
    public void onItemClick(int position) {
        if (currentTypeAdapter == SearchFragmentAdapter.TYPE_LECTURERS){
            //Log.d("devcpp",lecturers.get(position).toString());
            fullName = lecturers.get(position).getActName();
            own = lecturers.get(position).getSlug();
            new ParceShedule().execute(lecturers.get(position).getSlug());
        }

        if (currentTypeAdapter == SearchFragmentAdapter.TYPE_GROUPS){
            //Log.d("devcpp",groups.get(position).toString());
        }
    }


    class ParceShedule extends AsyncTask<String, Void, Void> {

        RoomDB database;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Void doInBackground(String... strings) {
            database = RoomDB.getInstance(getContext());
            database.tableDao().clearTable();
            data.clear();
            String url = BASE_URL + "schedule/lecturer/" +strings[0];
            parce(url);

            return null;
        }


        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);
            Intent intent = new Intent(getContext(), TimeTableActivity.class);
            intent.putExtra(TimeTableActivity.KEY_FULLNAME, fullName);
            startActivity(intent);
        }

        void parce(String url){
            try {
                Document doc = Jsoup.connect(url).get();

                Elements tables = doc.getElementsByClass("table");
                Element table = tables.get(0);

                Elements table_data = table.getElementsByTag("td");
                //Log.d("devcpp","table_data size -> "+table_data.size());

                String currentDay = "";

                int indexFirstLesson = 1;
                int indexSecondLesson = 3;
                int indexThirdLesson = 5;
                int indexFourthLesson = 7;
                int currentLesson = -1;

                for(int i = 0; i <table_data.size();i++){
                    //Log.d("devcpp",i+" - "+table_data.get(i).html());
                    if(table_data.get(i).html().equals("<i class=\"fal fa-alarm-exclamation\"></i>")){
                        table_data.remove(i);
                    }
                    if (table_data.get(i).html().equals("Понеділок")){
                        currentDay ="Понеділок";
                        continue;
                    }
                    if (table_data.get(i).html().equals("Вівторок")){
                        currentDay = "Вівторок";
                        continue;
                    }
                    if (table_data.get(i).html().equals("Середа")){
                        currentDay = "Середа";
                        continue;
                    }
                    if (table_data.get(i).html().equals("Четвер")){
                        currentDay = "Четвер";
                        continue;
                    }
                    if (table_data.get(i).html().equals("П'ятниця")){
                        currentDay = "П'ятниця";
                        continue;
                    }

                    if (table_data.get(i).html().equals("08:00 - 09:35")){
                        currentLesson = indexFirstLesson;
                        if(data.size()!=0){
                            sortData();
                        }
                        continue;
                    }
                    if (table_data.get(i).html().equals("09:50 - 11:25")){
                        currentLesson = indexSecondLesson;
                        sortData();
                        continue;
                    }
                    if (table_data.get(i).html().equals("11:55 - 13:30")){
                        currentLesson = indexThirdLesson;
                        sortData();
                        continue;
                    }
                    if (table_data.get(i).html().equals("13:45 - 15:20")){
                        currentLesson = indexFourthLesson;
                        sortData();
                        continue;
                    }

                    String[] lessonInfo = getLessonInfo(table_data.get(i).html());

                    data.add(new TempTableItem(currentDay,fullName,currentLesson,lessonInfo[1],lessonInfo[2],lessonInfo[0]));
                    currentLesson++;
                }

                for(int i=0; i< data.size();i++){
                    //Log.d("devcpp", data.get(i).toString());
                    database.tableDao().insert(data.get(i));
                }

            } catch (IOException e) {
                Log.d("devcpp",e.getMessage());
            }
        }

        private void sortData() {
            if (data.size() % 2 != 0) {
                TempTableItem item = new TempTableItem(data.get(data.size() - 1));
                item.setLessonNumber(item.getLessonNumber() + 1);
                data.add(item);
            }
        }


        String[] getLessonInfo(String str){
            String[] result = {"","",""};
            if(!str.equals("<i class=\"fal fa-calendar-minus\"></i>")){
                String[] list = str.split(",");
                if (list[0].contains("<a")){
                    if (list.length >= 3){
                        int indexBegin = list[0].indexOf("data-content=\"")+14;
                        int indexEnd = list[0].indexOf("<br>\">");
                        String groups = list[0].substring(indexBegin, indexEnd);
                        groups = groups.replace("<br>",",");
                        result[0] = groups;
                        if(list.length == 3){
                            result[1] = list[1];
                            result[2] = list[2];
                        }else if(list.length > 3){
                            result[2] = list[list.length-1];
                            for(int i = 1; i<list.length-1;i++){
                                result[1] = result[1] + list[i];
                            }
                        }
                    }
                }else{
                    result[0] = list[0];
                    if(list.length > 3){
                        result[2] = list[list.length-1];
                        for(int i = 1; i<list.length-1;i++){
                            result[1] = result[1] + "," +list[i];
                        }
                        result[1] = result[1].substring(2);
                    }else{
                        result[1] = list[1];
                        result[2] = list[2];
                    }
                }
            }
            return result;
        }
    }
}