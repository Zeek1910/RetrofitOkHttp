package zeek1910.com.myapplication;

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

    @Query("SELECT * FROM teachers_shedule")
    List<TableItem> getAll();

    @Delete
    void reset(List<TableItem> tableItems);
}
