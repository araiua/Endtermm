package Model;

public class Movie {
    public int mid;
    public String title;
    public int year;

    public Movie(int mid, String title, int year) {
        this.mid = mid;
        this.title = title;
        this.year = year;
    }

    public String toString() {
        return "Movie{id=" + mid + ", title=" + title + ", year=" + year + "}";
    }
}