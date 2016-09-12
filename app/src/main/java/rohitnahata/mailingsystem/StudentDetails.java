package rohitnahata.mailingsystem;

import java.io.Serializable;

/**
 * Created by Rohit on 05/09/2016.
 */
public class StudentDetails implements Serializable {
    String classroom;
    String id;
    String name;
    String email_id;

    public StudentDetails(){

    }

//    public StudentDetails(String name,String email_id){
//        this.name=name;
//        this.email_id=email_id;
//        this.id="";
//        this.classroom="";
//    }


    public StudentDetails(String id, String name, String email_id, String classroom) {
        this.id = id;
        this.name = name;
        this.email_id = email_id;
        this.classroom=classroom;
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
