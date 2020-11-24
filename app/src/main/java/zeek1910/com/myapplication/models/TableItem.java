package zeek1910.com.myapplication.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "teachers_shedule")
public class TableItem implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "day_name")
    private String dayName;

    @ColumnInfo(name = "owner")
    private String owner;

    @ColumnInfo(name = "lesson_number")
    private int lessonNumber;

    @ColumnInfo(name = "lesson_name")
    private String lessonName;

    @ColumnInfo(name = "lesson_room")
    private String lessonRoom;

    @ColumnInfo(name = "lesson_group")
    private String lessonGroup;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(int lessonNumber) {
        this.lessonNumber = lessonNumber;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getLessonRoom() {
        return lessonRoom;
    }

    public void setLessonRoom(String lessonRoom) {
        this.lessonRoom = lessonRoom;
    }

    public String getLessonGroup() {
        return lessonGroup;
    }

    public void setLessonGroup(String lessonGroup) {
        this.lessonGroup = lessonGroup;
    }
}
