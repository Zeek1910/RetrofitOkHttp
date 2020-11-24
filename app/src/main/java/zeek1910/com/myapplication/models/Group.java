package zeek1910.com.myapplication.models;

public class Group {
    String slug;
    String name;

    public Group(String slug, String name) {
        this.slug = slug;
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Groups{" +
                "slug='" + slug + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
