package rohitnahata.mailingsystem;

import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;

import rohitnahata.mailingsystem.Models.PreviousMailModel;
import rohitnahata.mailingsystem.Models.StudentDetails;
import rohitnahata.mailingsystem.Utils.TinyDB;

/**
 * Created by Rohit on 04/09/2016.
 */

public class App extends android.app.Application {


    public static String BASE_URL = "https://mailing-system-c6780.firebaseio.com/";
    TinyDB tinyDB;
    private ArrayList<StudentDetails> studentDetailsList;
    private ArrayList<String> className;
    private ArrayList<PreviousMailModel> previousMailModelList;

    public ArrayList<PreviousMailModel> getPreviousMailModelList() {
        previousMailModelList = tinyDB.getListObjectMail("mailList", PreviousMailModel.class);
        return previousMailModelList;
    }

    public void setPreviousMailModelList(ArrayList<PreviousMailModel> previousMailModelList) {
        tinyDB.putListObjectMail("classList", previousMailModelList);
        this.previousMailModelList = previousMailModelList;
    }

    public ArrayList<StudentDetails> getStudentDetailsList() {
        return studentDetailsList;
    }

    public void setStudentDetailsList(ArrayList<StudentDetails> studentDetailsList) {
        this.studentDetailsList = studentDetailsList;
    }

    public ArrayList<String> getClassName() {
        return className;
    }

    public void setClassName(ArrayList<String> className) {
        this.className = className;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        if (!FirebaseApp.getApps(this).isEmpty()) {
            Firebase.getDefaultConfig().setPersistenceEnabled(true);
        }
        studentDetailsList = new ArrayList<>();
        className = new ArrayList<>();
        tinyDB = new TinyDB(getBaseContext());
        previousMailModelList = tinyDB.getListObjectMail("mailList", PreviousMailModel.class);
        if (previousMailModelList == null)
            previousMailModelList = new ArrayList<>();


    }
}
