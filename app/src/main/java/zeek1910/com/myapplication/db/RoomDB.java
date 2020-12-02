package zeek1910.com.myapplication.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import zeek1910.com.myapplication.models.LecturerTableItem;

@Database(entities = { LecturerTableItem.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    private static RoomDB database;

    private static final String DATABASE_NAME = "database";

    public static synchronized RoomDB getInstance(Context context){
        if(database == null){
            database = Room.databaseBuilder(context,RoomDB.class,DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract TableDao tableDao();
}
