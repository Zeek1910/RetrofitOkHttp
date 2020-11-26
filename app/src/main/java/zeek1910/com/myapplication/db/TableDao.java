package zeek1910.com.myapplication.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import zeek1910.com.myapplication.models.TableItem;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface TableDao {


    @Insert(onConflict = REPLACE)
    void insert(TableItem tableItem);

    @Query("SELECT * FROM lecturer_shedule")
    List<TableItem> getAll();

    @Query("SELECT * FROM lecturer_shedule WHERE owner =:own")
    List<TableItem> getSheduleByLecturer(String own);

    @Query("DELETE FROM lecturer_shedule WHERE owner = :own")
    void delete(String own);

    @Query("DELETE FROM lecturer_shedule")
    void clearTable();
}
