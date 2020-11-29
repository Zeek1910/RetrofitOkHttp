package zeek1910.com.myapplication.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface TableDao {


    @Insert(onConflict = REPLACE)
    void insert(TempTableItem tempTableItem);

    @Insert(onConflict = REPLACE)
    void insertToMainTable(TableItem tableItem);

    @Query("SELECT * FROM temp_table")
    List<TempTableItem> getAll();

    @Query("SELECT * FROM temp_table WHERE owner =:own")
    List<TempTableItem> getSheduleByLecturer(String own);

    @Query("DELETE FROM temp_table WHERE owner = :own")
    void delete(String own);

    @Query("DELETE FROM temp_table")
    void clearTable();


    @Query("SELECT * FROM shedule_table WHERE owner =:own")
    List<TableItem> getSheduleByLecturerFromSheduleTable(String own);

    @Query("DELETE FROM shedule_table WHERE owner = :own")
    void deleteFromSheduleTable(String own);


}
