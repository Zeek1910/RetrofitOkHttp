package zeek1910.com.myapplication.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zeek1910.com.myapplication.adapters.TimeTableAdapter;
import zeek1910.com.myapplication.models.LecturerTableItem;
import zeek1910.com.myapplication.interfaces.APIInterface;
import zeek1910.com.myapplication.interfaces.OnRecyclerViewItemClickListener;
import zeek1910.com.myapplication.R;
import zeek1910.com.myapplication.models.Group;
import zeek1910.com.myapplication.models.Lecturer;

public class SearchFragment extends Fragment implements MaterialButtonToggleGroup.OnButtonCheckedListener, OnRecyclerViewItemClickListener {

    public static final String BASE_URL = "https://profkomstud.khai.edu/";

    private String  nomDenom = "";

    private RecyclerView recyclerView;
    private TimeTableAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private AutoCompleteTextView autoCompleteTextViewFacultys;
    private AutoCompleteTextView autoCompleteTextViewOwners;
    private MaterialButtonToggleGroup toggleGroup;
    private ProgressBar progressBar;
    //private TextInputLayout textInputLayoutFaculty, textInputLayoutOwner;
    private TextView textViewCurrentDay;
    private LinearLayout recyclerViewPanel;

    ArrayAdapter<String> adapterOwnersList;

    private List<Lecturer> lecturers;
    private List<Group> groups;
    private List<LecturerTableItem> data;
    private List<String[]> tempData;

    private Retrofit retrofit;
    private APIInterface service;
    Callback<List<Lecturer>> callbackFaculty;
    Callback<List<Group>> callback2;

    private int faculty = -1;

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
        tempData = new ArrayList<>();

        adapterOwnersList = new ArrayAdapter<String>(getContext(),R.layout.drop_down_menu_item);

        callbackFaculty = new Callback<List<Lecturer>>() {
            @Override
            public void onResponse(Call<List<Lecturer>> call, Response<List<Lecturer>> response) {
                lecturers.addAll(response.body());

                for (Lecturer lecturer:lecturers) {
                    adapterOwnersList.add(lecturer.getActName());
                }
                adapterOwnersList.notifyDataSetChanged();
                autoCompleteTextViewOwners.setEnabled(true);
            }

            @Override
            public void onFailure(Call<List<Lecturer>> call, Throwable t) {
                Log.d("devcpp", t.getMessage());
            }
        };

        callback2 = new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {

            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                Log.d("devcpp", t.getMessage());

            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_search, container, false);

        //AppBarLayout appBarLayout;
        //appBarLayout.setExpanded(true);

        autoCompleteTextViewFacultys = view.findViewById(R.id.facultySelect);
        autoCompleteTextViewOwners = view.findViewById(R.id.ownerSelect);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        //textInputLayoutFaculty = view.findViewById(R.id.textInputLayoutFaculty);
        //textInputLayoutOwner = view.findViewById(R.id.textInputLayoutOwner);
        textViewCurrentDay = view.findViewById(R.id.text_view_current_date);
        recyclerViewPanel = view.findViewById(R.id.recyclerViewPanel);
        recyclerViewPanel.setVisibility(View.GONE);

        ArrayAdapter<String> adapterFacultysList = new ArrayAdapter<String>(getContext(),R.layout.drop_down_menu_item);
        for(int i = 1; i <= 8; i++){
            adapterFacultysList.add("Факультет "+i);
        }
        autoCompleteTextViewFacultys.setAdapter(adapterFacultysList);
        autoCompleteTextViewFacultys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                faculty = i+1;
                if (faculty != -1){
                    lecturers.clear();
                    adapterOwnersList.clear();
                    autoCompleteTextViewOwners.dismissDropDown();
                    service.getLecturersByFaculty(faculty).enqueue(callbackFaculty);
                }
            }
        });

        autoCompleteTextViewOwners.setAdapter(adapterOwnersList);
        autoCompleteTextViewOwners.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager manager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(autoCompleteTextViewOwners.getWindowToken(), 0);
                autoCompleteTextViewOwners.clearFocus();
                String text = autoCompleteTextViewOwners.getText().toString();
                for (Lecturer lecturer:lecturers) {
                    if (lecturer.getActName().equals(text)){
                        new ParceShedule().execute(lecturer.getSlug(),
                                lecturer.getActName());
                    }
                }
                autoCompleteTextViewOwners.setEnabled(false);
                autoCompleteTextViewFacultys.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        toggleGroup = view.findViewById(R.id.materialButtonToggleGroup);
        toggleGroup.addOnButtonCheckedListener(this);

        recyclerView = view.findViewById(R.id.SearchRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onItemClick(int position) {
        //TODO Recycler item click listener
    }

    @Override
    public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
        if(isChecked){
            switch (checkedId){
                case R.id.materialBtn1:

                    break;
                case R.id.materialBtn2:

                    break;
            }
        }

    }

    String getDateAndDay(){
        String currentDayName = "";
        Calendar calendar= Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek){
            case 1:
                currentDayName = "Неділя";
                break;
            case 2:
                currentDayName = "Понеділок";
                break;
            case 3:
                currentDayName = "Вівторок";
                break;
            case 4:
                currentDayName = "Середа";
                break;
            case 5:
                currentDayName = "Четверг";
                break;
            case 6:
                currentDayName = "П'ятниця";
                break;
            case 7:
                currentDayName = "Субота";
                break;
        }
        //int month = calendar.get(Calendar.MONTH);
        //int day = calendar.get(Calendar.DATE);

        return currentDayName+" ("+nomDenom+")";
        //return ""+day+"."+month+" "+currentDayName+"("+nomDenom+")";
    }


    class ParceShedule extends AsyncTask<String, Void, Void> {

        private String fullName = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            recyclerViewPanel.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        }


        @Override
        protected Void doInBackground(String... strings) {
            data.clear();

            String url = BASE_URL + "schedule/lecturer/" +strings[0];
            fullName = strings[1];
            try {
                Document doc = Jsoup.connect(url).get();

                Elements temp = doc.getElementsByClass("x-head");
                nomDenom = temp.get(0).html().substring(temp.get(0).html().indexOf("(")+1);
                nomDenom = nomDenom.replace(")","");

                //Log.d("devcpp","elements-> "+nomDenom);

                Elements tables = doc.getElementsByClass("table");
                Element table = tables.get(0);

                Elements table_data = table.getElementsByTag("td");

                String[] days = new String[]{"Понеділок","Вівторок","Середа","Четвер","П'ятниця"};
                int currentDay = -1;

                int indexFirstLesson = 1;
                int indexSecondLesson = 2;
                int indexThirdLesson = 3;
                int indexFourthLesson = 4;
                int currentLesson = -1;

                for(int i = 0; i <table_data.size();i++){

                    if(table_data.get(i).html().equals("<i class=\"fal fa-alarm-exclamation\"></i>")){
                        table_data.remove(i);
                    }
                    if (table_data.get(i).html().equals("Понеділок")){
                        currentDay = 0;
                        continue;
                    }
                    if (table_data.get(i).html().equals("Вівторок")){
                        currentDay = 1;
                        continue;
                    }
                    if (table_data.get(i).html().equals("Середа")){
                        currentDay = 2;
                        continue;
                    }
                    if (table_data.get(i).html().equals("Четвер")){
                        currentDay = 3;
                        continue;
                    }
                    if (table_data.get(i).html().equals("П'ятниця")){
                        currentDay = 4;
                        continue;
                    }

                    if (table_data.get(i).html().equals("08:00 - 09:35")){
                        if(currentDay!=0){
                            sortData(days[currentDay-1],currentLesson);
                        }
                        currentLesson = indexFirstLesson;
                        continue;
                    }
                    if (table_data.get(i).html().equals("09:50 - 11:25")){
                        sortData(days[currentDay],currentLesson);
                        currentLesson = indexSecondLesson;
                        continue;
                    }
                    if (table_data.get(i).html().equals("11:55 - 13:30")){
                        sortData(days[currentDay],currentLesson);
                        currentLesson = indexThirdLesson;
                        continue;
                    }
                    if (table_data.get(i).html().equals("13:45 - 15:20")){
                        sortData(days[currentDay],currentLesson);
                        currentLesson = indexFourthLesson;
                        continue;
                    }

                    String[] lessonInfo = getLessonInfo(table_data.get(i).html());

                    tempData.add(lessonInfo);
                }
                sortData(days[currentDay],currentLesson);

            } catch (IOException e) {
                Log.d("devcpp",e.getMessage());
            }

            return null;
        }

        private void sortData(String currentDay, int currentLesson) {
            if (tempData.size() == 1) {
                LecturerTableItem item = new LecturerTableItem(currentDay,fullName,currentLesson,tempData.get(0)[1],tempData.get(0)[2],tempData.get(0)[0],tempData.get(0)[1],tempData.get(0)[2],tempData.get(0)[0]);
                data.add(item);
            }else if (tempData.size() == 2){
                LecturerTableItem item = new LecturerTableItem(currentDay,fullName,currentLesson,tempData.get(0)[1],tempData.get(0)[2],tempData.get(0)[0],tempData.get(1)[1],tempData.get(1)[2],tempData.get(1)[0]);
                data.add(item);
            }

            tempData.clear();
        }


        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);

            autoCompleteTextViewOwners.setEnabled(true);
            autoCompleteTextViewFacultys.setEnabled(true);
            adapter = new TimeTableAdapter(data);
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            textViewCurrentDay.setText(getDateAndDay());
            recyclerViewPanel.setVisibility(View.VISIBLE);
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