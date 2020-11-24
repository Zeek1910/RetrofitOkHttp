package zeek1910.com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zeek1910.com.myapplication.models.Group;
import zeek1910.com.myapplication.models.Lecturer;


public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://profkomstud.khai.edu/";

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.tv);

        SheduleParcer sheduleParcer = new SheduleParcer(text);
        sheduleParcer.execute("barsov-valerij-igorovich");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface service = retrofit.create(APIInterface.class);

        Callback<List<Lecturer>> callback1 = new Callback<List<Lecturer>>() {
            @Override
            public void onResponse(Call<List<Lecturer>> call, Response<List<Lecturer>> response) {

                for(int i = 0; i<response.body().size();i++){
                    Log.d("devcpp",response.body().get(i).toString());
                }
            }

            @Override
            public void onFailure(Call<List<Lecturer>> call, Throwable t) {
                Log.d("devcpp",t.getMessage().toString());
            }
        };

        Callback<List<Group>> callback2 = new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                for(int i = 0; i<response.body().size();i++){
                    Log.d("devcpp",response.body().get(i).toString());
                }
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {

            }
        };

        service.getLecturersByFaculty(1).enqueue(callback1);
        service.getGroupsByFaculty("1|3").enqueue(callback2);

    }

}