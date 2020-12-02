package zeek1910.com.myapplication.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "lecturer_time_table")
public class LecturerTableItem implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "day_name")
    private String dayName;

    @ColumnInfo(name = "owner")
    private String owner;

    @ColumnInfo(name = "lesson_number")
    private int lessonNumber;

    @ColumnInfo(name = "top_lesson_name")
    private String topLessonName;

    @ColumnInfo(name = "top_lesson_room")
    private String topLessonRoom;

    @ColumnInfo(name = "top_lesson_group")
    private String topLessonGroup;

    @ColumnInfo(name = "bot_lesson_name")
    private String botLessonName;

    @ColumnInfo(name = "bot_lesson_room")
    private String botLessonRoom;

    @ColumnInfo(name = "bot_lesson_group")
    private String botLessonGroup;

    public LecturerTableItem() {
    }

    @Ignore
    public LecturerTableItem(String dayName, String owner, int lessonNumber,
                             String topLessonName, String topLessonRoom,
                             String topLessonGroup, String botLessonName,
                             String botLessonRoom, String botLessonGroup) {
        this.dayName = dayName;
        this.owner = owner;
        this.lessonNumber = lessonNumber;
        this.topLessonName = topLessonName;
        this.topLessonRoom = topLessonRoom;
        this.topLessonGroup = topLessonGroup;
        this.botLessonName = botLessonName;
        this.botLessonRoom = botLessonRoom;
        this.botLessonGroup = botLessonGroup;
    }

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

    public String getTopLessonName() {
        return topLessonName;
    }

    public void setTopLessonName(String topLessonName) {
        this.topLessonName = topLessonName;
    }

    public String getTopLessonRoom() {
        return topLessonRoom;
    }

    public void setTopLessonRoom(String topLessonRoom) {
        this.topLessonRoom = topLessonRoom;
    }

    public String getTopLessonGroup() {
        return topLessonGroup;
    }

    public void setTopLessonGroup(String topLessonGroup) {
        this.topLessonGroup = topLessonGroup;
    }

    public String getBotLessonName() {
        return botLessonName;
    }

    public void setBotLessonName(String botLessonName) {
        this.botLessonName = botLessonName;
    }

    public String getBotLessonRoom() {
        return botLessonRoom;
    }

    public void setBotLessonRoom(String botLessonRoom) {
        this.botLessonRoom = botLessonRoom;
    }

    public String getBotLessonGroup() {
        return botLessonGroup;
    }

    public void setBotLessonGroup(String botLessonGroup) {
        this.botLessonGroup = botLessonGroup;
    }

    @Override
    public String toString() {
        return "LecturerTableItem{" +
                "dayName='" + dayName + '\'' +
                ", owner='" + owner + '\'' +
                ", lessonNumber=" + lessonNumber +
                ", topLessonName='" + topLessonName + '\'' +
                ", topLessonRoom='" + topLessonRoom + '\'' +
                ", topLessonGroup='" + topLessonGroup + '\'' +
                ", botLessonName='" + botLessonName + '\'' +
                ", botLessonRoom='" + botLessonRoom + '\'' +
                ", botLessonGroup='" + botLessonGroup + '\'' +
                '}';
    }
}
