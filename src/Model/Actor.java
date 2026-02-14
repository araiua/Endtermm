package Model;

public class Actor {
    public int aid;
    public String firstName;
    public String lastName;

    public Actor(int aid, String firstName, String lastName) {
        this.aid = aid;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String toString() {
        return "Actor{id=" + aid + ", firstName=" + firstName + ", lastName=" + lastName + "}";
    }
}