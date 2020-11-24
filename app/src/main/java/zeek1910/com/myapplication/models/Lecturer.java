package zeek1910.com.myapplication.models;

public class Lecturer {

    String slug;
    String strPos;
    String actName;
    int department;

    public Lecturer(String slug, String strPos, String actName, int department) {
        this.slug = slug;
        this.strPos = strPos;
        this.actName = actName;
        this.department = department;
    }

    public String getSlug() {
        return slug;
    }

    public String getStrPos() {
        return strPos;
    }

    public String getActName() {
        return actName;
    }

    public int getDepartment() {
        return department;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setStrPos(String strPos) {
        this.strPos = strPos;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Lecturers{" +
                "slug='" + slug + '\'' +
                ", strPos='" + strPos + '\'' +
                ", actName='" + actName + '\'' +
                ", department=" + department +
                '}';
    }
}
