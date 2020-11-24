package zeek1910.com.myapplication.models;

public class Day {

    private String name;

    private Lesson lesson1;
    private Lesson lesson2;
    private Lesson lesson3;
    private Lesson lesson4;
    private Lesson lesson5;
    private Lesson lesson6;
    private Lesson lesson7;
    private Lesson lesson8;

    public Day() {
    }

    public Day(String name, Lesson lesson1, Lesson lesson2,
               Lesson lesson3, Lesson lesson4,
               Lesson lesson5, Lesson lesson6,
               Lesson lesson7, Lesson lesson8) {
        this.name = name;
        this.lesson1 = lesson1;
        this.lesson2 = lesson2;
        this.lesson3 = lesson3;
        this.lesson4 = lesson4;
        this.lesson5 = lesson5;
        this.lesson6 = lesson6;
        this.lesson7 = lesson7;
        this.lesson8 = lesson8;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Lesson getLesson1() {
        return lesson1;
    }

    public void setLesson1(Lesson lesson1) {
        this.lesson1 = lesson1;
    }

    public Lesson getLesson2() {
        return lesson2;
    }

    public void setLesson2(Lesson lesson2) {
        this.lesson2 = lesson2;
    }

    public Lesson getLesson3() {
        return lesson3;
    }

    public void setLesson3(Lesson lesson3) {
        this.lesson3 = lesson3;
    }

    public Lesson getLesson4() {
        return lesson4;
    }

    public void setLesson4(Lesson lesson4) {
        this.lesson4 = lesson4;
    }

    public Lesson getLesson5() {
        return lesson5;
    }

    public void setLesson5(Lesson lesson5) {
        this.lesson5 = lesson5;
    }

    public Lesson getLesson6() {
        return lesson6;
    }

    public void setLesson6(Lesson lesson6) {
        this.lesson6 = lesson6;
    }

    public Lesson getLesson7() {
        return lesson7;
    }

    public void setLesson7(Lesson lesson7) {
        this.lesson7 = lesson7;
    }

    public Lesson getLesson8() {
        return lesson8;
    }

    public void setLesson8(Lesson lesson8) {
        this.lesson8 = lesson8;
    }

    @Override
    public String toString() {
        return "Day{" +
                "name='" + name + '\'' +
                ", lesson1=" + lesson1.toString() +
                ", lesson2=" + lesson2.toString() +
                ", lesson3=" + lesson3.toString() +
                ", lesson4=" + lesson4.toString() +
                ", lesson5=" + lesson5.toString() +
                ", lesson6=" + lesson6.toString() +
                ", lesson7=" + lesson7.toString() +
                ", lesson8=" + lesson8.toString() +
                '}';
    }
}
