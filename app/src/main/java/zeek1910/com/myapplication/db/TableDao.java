package zeek1910.com.myapplication.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import zeek1910.com.myapplication.models.LecturerTableItem;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface TableDao {

    @Insert(onConflict = REPLACE)
    void insert(LecturerTableItem lecturerTableItem);

    @Query("SELECT * FROM lecturer_time_table")
    List<LecturerTableItem> getAll();

    @Query("SELECT * FROM lecturer_time_table WHERE owner =:own")
    List<LecturerTableItem> getSheduleByLecturer(String own);

    @Query("DELETE FROM lecturer_time_table WHERE owner = :own")
    void delete(String own);

    @Query("DELETE FROM lecturer_time_table")
    void clearTable();

}
