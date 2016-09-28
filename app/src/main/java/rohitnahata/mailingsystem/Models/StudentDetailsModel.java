package rohitnahata.mailingsystem.Models;


public class StudentDetailsModel {
    private String classroom;
    private String id;
    private String name;
    private String email_id;

    public StudentDetailsModel() {

    }

    public StudentDetailsModel(String id, String name, String email_id, String classroom) {
        this.id = id;
        this.name = name;
        this.email_id = email_id;
        this.classroom=classroom;
    }

    @Override
    public String toString() {
        return "StudentDetailsModel{" +
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

//    public void setEmail_id(String email_id) {
//        this.email_id = email_id;
//    }

    public String getClassroom() {
        return classroom;
    }

//    public void setClassroom(String classroom) {
//        this.classroom = classroom;
//    }
}
