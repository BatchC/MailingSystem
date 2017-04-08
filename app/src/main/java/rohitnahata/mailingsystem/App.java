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
import rohitnahata.mailingsystem.Models.StudentDetailsModel;


class App extends android.app.Application {

    public static final String BASE_URL = "https://mailing-system-cbbc1.firebaseio.com/";
    private static final String PREVIOUS_MAIL_MODEL_LIST = "previousMailList";
    private static final String STUDENTS_LIST = "previousStudentsList";
    private static final String CLASSROOM_LIST = "classroomList";
    private SharedPreferences sharedPrefs;
    private Editor editor;
    private Gson gson;
    private ArrayList<StudentDetailsModel> studentDetailsModelList;
    private ArrayList<StudentDetailsModel> className;
    private ArrayList<PreviousMailModel> previousMailModelList;

    public ArrayList<PreviousMailModel> getPreviousMailModelList() {
        String json = sharedPrefs.getString(PREVIOUS_MAIL_MODEL_LIST, null);
        Type type = new TypeToken<ArrayList<PreviousMailModel>>() {
        }.getType();
        previousMailModelList = gson.fromJson(json, type);
        if (previousMailModelList != null)
            return previousMailModelList;
        else
            return new ArrayList<>();

    }

    public void setPreviousMailModelList(ArrayList<PreviousMailModel> previousMailModelList) {
        editor = sharedPrefs.edit();
        String json = gson.toJson(previousMailModelList);
        editor.putString(PREVIOUS_MAIL_MODEL_LIST, json);
        editor.apply();
        this.previousMailModelList = previousMailModelList;
    }

    public ArrayList<StudentDetailsModel> getStudentDetailsModelList() {
        String json = sharedPrefs.getString(STUDENTS_LIST, null);
        Type type = new TypeToken<ArrayList<StudentDetailsModel>>() {
        }.getType();
        studentDetailsModelList = gson.fromJson(json, type);
        if (studentDetailsModelList != null)
            return studentDetailsModelList;
        else
            return new ArrayList<>();
    }

    public void setStudentDetailsModelList(ArrayList<StudentDetailsModel> studentDetailsModelList) {
        editor = sharedPrefs.edit();
        String json = gson.toJson(studentDetailsModelList);
        editor.putString(STUDENTS_LIST, json);
        editor.apply();
        this.studentDetailsModelList = studentDetailsModelList;
    }

    public ArrayList<StudentDetailsModel> getClassName() {
        String json = sharedPrefs.getString(CLASSROOM_LIST, null);
        Type type = new TypeToken<ArrayList<StudentDetailsModel>>() {
        }.getType();
        className = gson.fromJson(json, type);
        if (className != null)
            return className;
        else
            return new ArrayList<>();
    }

    public void setClassName(ArrayList<StudentDetailsModel> className) {
        editor = sharedPrefs.edit();
        String json = gson.toJson(className);
        editor.putString(CLASSROOM_LIST, json);
        editor.apply();
        this.className = className;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        if (!FirebaseApp.getApps(this).isEmpty()) {
            Firebase.getDefaultConfig().setPersistenceEnabled(true);
        }
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        gson = new Gson();
    }
}
