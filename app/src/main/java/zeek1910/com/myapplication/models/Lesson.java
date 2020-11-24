package zeek1910.com.myapplication.models;

import java.util.Arrays;
import java.util.Objects;

public class Lesson {

    private String name;
    private String groups;
    private String room;

    public Lesson(String name, String groups, String room) {
        this.name = name;
        this.groups = groups;
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "name='" + name + '\'' +
                ", groups=" + groups + '\'' +
                ", room='" + room + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(name, lesson.name) &&
                Objects.equals(groups, lesson.groups) &&
                Objects.equals(room, lesson.room);
    }
}
