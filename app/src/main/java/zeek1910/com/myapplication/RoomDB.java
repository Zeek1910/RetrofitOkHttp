package zeek1910.com.myapplication;

import androidx.room.Database;
import androidx.room.Entity;

import zeek1910.com.myapplication.models.TableItem;

@Database(entities = {TableItem.class}, version = 1, exportSchema = false)
public class RoomDB {
}
