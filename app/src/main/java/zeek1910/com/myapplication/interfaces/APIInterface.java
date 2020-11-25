package zeek1910.com.myapplication.interfaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import zeek1910.com.myapplication.models.Group;
import zeek1910.com.myapplication.models.Lecturer;

public interface APIInterface {

    @GET("/schedule/lecturersbyfac/{faculty}")
    Call<List<Lecturer>> getLecturersByFaculty(@Path("faculty") Integer faculty);


    @GET("/schedule/groupsbyfac/{faculty}")
    Call<List<Group>> getGroupsByFaculty(@Path("faculty") String faculty);

}
