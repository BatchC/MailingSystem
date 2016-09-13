package rohitnahata.mailingsystem;

import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;

/**
 * Created by Rohit on 04/09/2016.
 */
public class App extends android.app.Application {


    public static String BASE_URL = "https://mailing-system-c6780.firebaseio.com/";
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        if(!FirebaseApp.getApps(this).isEmpty()){
            Firebase.getDefaultConfig().setPersistenceEnabled(true);
        }
    }
}
