package rohitnahata.mailingsystem;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import rohitnahata.mailingsystem.Models.StudentDetailsModel;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    private ArrayList<StudentDetailsModel> studentDetailsModelList;
    private ArrayList<StudentDetailsModel> temp;
    private ArrayList<StudentDetailsModel> className;



//    private FirebaseAuth mAuth;
//    // [END declare_auth]
//
//    // [START declare_auth_listener]
//    private FirebaseAuth.AuthStateListener mAuthListener;
//    int flag=0;//to check whether we need to call the Login Activity or directly the Mail Activity


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        StartAnimations();
    }


    private void StartAnimations(){
        Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.fade_in_splashscreen);
        anim1.reset();
        ImageView img1 =(ImageView) findViewById(R.id.splashScreenImage);
        img1.clearAnimation();
        img1.startAnimation(anim1);

        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.animation);
        anim2.reset();
        ImageView txt =(ImageView) findViewById(R.id.splashScreenText);
        txt.clearAnimation();
        txt.startAnimation(anim2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        className = ((App) this.getApplication()).getClassName();
        studentDetailsModelList = ((App) this.getApplication()).getStudentDetailsModelList();
//        mAuth = FirebaseAuth.getInstance();
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if(user!=null)
//                    flag=1;
//            }
//        };
        new PrefetchData().execute(App.BASE_URL);
    }

    private class PrefetchData extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            new Firebase(strings[0]).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    deleteData();
                    temp = new ArrayList<>(studentDetailsModelList);
                    for (DataSnapshot alert : dataSnapshot.getChildren()) {
                        String strClass = alert.getKey();
                        className_add(alert.getKey());
//                        className.add(alert.getKey());
                        for (DataSnapshot recipient : alert.getChildren()) {
                            String strEmail = (String) recipient.child("email_id").getValue();
                            String strName = (String) recipient.child("student_name").getValue();
                            String strUID = (String) recipient.child("id").getValue();
                            addData(strUID, strName, strEmail, strClass);
                        }
                    }
                    ((App) getApplication()).setStudentDetailsModelList(studentDetailsModelList);
                    ((App) getApplication()).setClassName(className);

                }

                private void className_add(String key) {
                    StudentDetailsModel classDetails = new StudentDetailsModel("", "All Students", "", key);
                    className.add(classDetails);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("Error");
                    studentDetailsModelList = new ArrayList<>(temp);
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

//            Intent i;
//            if(flag==0)
            new Handler().postDelayed(new Runnable() {
                public void run() {


                Intent i = new Intent(SplashScreen.this, Login.class);

                //            else
//                i=new Intent(SplashScreen.this,MainActivity.class);
                startActivity(i);

                SplashScreen.this.finish();
            }
        }, SPLASH_TIME_OUT);

        }


        public void deleteData() {
            if (studentDetailsModelList != null) {
                studentDetailsModelList.clear();
            }
            if (className != null) {
                className.clear();
            }
        }

        public void addData(String n1, String n2, String n3, String n4) {
            StudentDetailsModel studentDetailsModel = new StudentDetailsModel(n1, n2, n3, n4);
            studentDetailsModelList.add(studentDetailsModel);
        }


    }
}


