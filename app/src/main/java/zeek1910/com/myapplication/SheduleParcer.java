package zeek1910.com.myapplication;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import zeek1910.com.myapplication.models.Day;
import zeek1910.com.myapplication.models.Lesson;

public class SheduleParcer extends AsyncTask<String,Void,Void> {

    public static final String BASE_URL = "https://profkomstud.khai.edu/schedule/lecturer/";

    OkHttpClient client;
    List<Day> days;

    public SheduleParcer(TextView tv){
        client = new OkHttpClient();
        days = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("parce","Start getting data");
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            String res = getSheduleByLecturers(strings[0]);
            String[] list_days = res.split("<td>&#");
            days.add(parce(list_days[1]));
            days.add(parce(list_days[2]));
            days.add(parce(list_days[3]));
            days.add(parce(list_days[4]));
            days.add(parce(list_days[5]));


            printDay(days.get(1));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("parce","Data recived!");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

    }

    String getSheduleByLecturers(String url) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL+url)
                .build();

        try (okhttp3.Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }


    Day parce(String data){

        Day day = new Day();

        if(data.contains("08:00")){

            int index1 = data.indexOf("08:00 - 09:35");
            int index2 = data.indexOf("09:50 - 11:25");
            int index3 = data.indexOf("11:55 - 13:30");
            int index4 = data.indexOf("13:45 - 15:20");



            String lesson1 = data.substring(index1, index2);
            String lesson2 = data.substring(index2, index3);
            String lesson3 = data.substring(index3, index4);
            String lesson4 = data.substring(index4);
            List<String> lessons = new ArrayList<>();
            lessons.add(lesson1);
            lessons.add(lesson2);
            lessons.add(lesson3);
            lessons.add(lesson4);

            int i = 1;
            for (String lesson: lessons) {
                if(lesson.contains("<a")){
                    if(lesson.contains("<td class=\"x-blue2\">")) {
                        String[] list = lesson.split("<td class=\"x-blue2\">");
                        if (list[0].contains("<a")) {
                            //Log.d("parce","числитель");
                            switch (i){
                                case 1:
                                    day.setLesson1(getLesson(list[0]));
                                    break;
                                case 2:
                                    day.setLesson3(getLesson(list[0]));
                                    break;
                                case 3:
                                    day.setLesson5(getLesson(list[0]));
                                    break;
                                case 4:
                                    day.setLesson7(getLesson(list[0]));
                                    break;
                            }
                        }
                        if (list[1].contains("<a")) {
                            //Log.d("parce","знаменатель");
                            switch (i){
                                case 1:
                                    day.setLesson2(getLesson(list[1]));
                                    break;
                                case 2:
                                    day.setLesson4(getLesson(list[1]));
                                    break;
                                case 3:
                                    day.setLesson6(getLesson(list[1]));
                                    break;
                                case 4:
                                    day.setLesson8(getLesson(list[1]));
                                    break;
                            }
                        }
                    }else {
                        switch (i){
                            case 1:
                                day.setLesson1(getLesson(lesson));
                                day.setLesson2(getLesson(lesson));
                                break;
                            case 2:
                                day.setLesson3(getLesson(lesson));
                                day.setLesson4(getLesson(lesson));
                                break;
                            case 3:
                                day.setLesson5(getLesson(lesson));
                                day.setLesson6(getLesson(lesson));
                                break;
                            case 4:
                                day.setLesson7(getLesson(lesson));
                                day.setLesson8(getLesson(lesson));
                                break;
                        }
                    }
                }
                i++;
            }
        }else{
            Log.d("parce","Фрагмент не содердит информации");
        }

        return day;
    }

    Lesson getLesson(String data){
        int index = data.indexOf("data-content='");
        String temp = data.substring(index);
        index = temp.indexOf("<br>'");
        String groups = temp.substring(14,index);
        String[] groupsList = groups.split("<br>");

        index = data.indexOf("a>,");
        temp = data.substring(index);
        index = temp.indexOf("</td>");
        temp = temp.substring(4,index);
        String[] list = temp.split(",");

        return new Lesson(list[0],groupsList,list[1]);
    }


    void printDay(Day day){
        String res = "";
        if (day.getLesson1() != null){
            res+="1 - "+day.getLesson1().getName()+";";
        }
        if (day.getLesson2() != null){
            res+="2 - "+day.getLesson2().getName()+";";
        }
        if (day.getLesson3() != null){
            res+="3 - "+day.getLesson3().getName()+";";
        }
        if (day.getLesson4() != null){
            res+="4 - "+day.getLesson4().getName()+";";
        }
        if (day.getLesson5() != null){
            res+="5 - "+day.getLesson5().getName()+";";
        }
        if (day.getLesson6() != null){
            res+="6 - "+day.getLesson6().getName()+";";
        }
        if (day.getLesson7() != null){
            res+="7 - "+day.getLesson7().getName()+";";
        }
        if (day.getLesson8() != null){
            res+="8 - "+day.getLesson8().getName()+";";
        }

        Log.d("parce", res);
    }
}
