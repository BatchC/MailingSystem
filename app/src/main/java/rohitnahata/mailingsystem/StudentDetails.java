package rohitnahata.mailingsystem;

/**
 * Created by Rohit on 05/09/2016.
 */
public class StudentDetails {
    String classroom;
    String id;
    String name;
    String email_id;

    public StudentDetails(){

    }

    public StudentDetails(String id, String name, String email_id, String classroom) {
        this.id = id;
        this.name = name;
        this.email_id = email_id;
        this.classroom=classroom;
    }

    @Override
    public String toString() {
        return "StudentDetails{" +
                "classroom='" + classroom + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email_id='" + email_id + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }
}
