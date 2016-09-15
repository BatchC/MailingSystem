package rohitnahata.mailingsystem;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import rohitnahata.mailingsystem.Models.PreviousMailModel;
import rohitnahata.mailingsystem.Models.StudentDetails;

/**
 * Created by Rohit on 04/09/2016.
 */

public class App extends android.app.Application {

    public static final String PREVIOUS_MAIL_MODEL_LIST = "previousMailList";
    public static final String STUDENTS_LIST = "previousStudentsList";
    public static final String CLASSROOM_LIST = "classroomList";


    public static String BASE_URL = "https://mailing-system-c6780.firebaseio.com/";
    SharedPreferences sharedPrefs;
    Editor editor;
    Gson gson;
    private ArrayList<StudentDetails> studentDetailsList;
    private ArrayList<String> className;
    private ArrayList<PreviousMailModel> previousMailModelList;

    public ArrayList<PreviousMailModel> getPreviousMailModelList() {
        String json = sharedPrefs.getString(PREVIOUS_MAIL_MODEL_LIST, null);
        Type type = new TypeToken<ArrayList<PreviousMailModel>>() {
        }.getType();
        previousMailModelList = gson.fromJson(json, type);
        return previousMailModelList;

    }

    public void setPreviousMailModelList(ArrayList<PreviousMailModel> previousMailModelList) {
        editor = sharedPrefs.edit();
        String json = gson.toJson(previousMailModelList);
        editor.putString(PREVIOUS_MAIL_MODEL_LIST, json);
        editor.apply();
        this.previousMailModelList = previousMailModelList;
    }

    public ArrayList<StudentDetails> getStudentDetailsList() {
        String json = sharedPrefs.getString(PREVIOUS_MAIL_MODEL_LIST, null);
        Type type = new TypeToken<ArrayList<PreviousMailModel>>() {
        }.getType();
        previousMailModelList = gson.fromJson(json, type);
        return studentDetailsList;
    }

    public void setStudentDetailsList(ArrayList<StudentDetails> studentDetailsList) {
        editor = sharedPrefs.edit();
        String json = gson.toJson(previousMailModelList);
        editor.putString(PREVIOUS_MAIL_MODEL_LIST, json);
        editor.apply();
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
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        gson = new Gson();
    }
}
