package rohitnahata.mailingsystem.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

import rohitnahata.mailingsystem.Activities.MainActivity;
import rohitnahata.mailingsystem.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DatabaseFragment extends Fragment {

    Firebase mRootRef;
    DatabaseReference databaseReference;
    ArrayList<String> messages=new ArrayList<>();
    TextView email_id;
    private ListView listView;
    View view;

    public DatabaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_database, container, false);
        setHasOptionsMenu(true);
        listView=(ListView)view.findViewById(R.id.listView1);
        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://mailing-system-c6780.firebaseio.com/DEPARTMENT/COMPS/TE/Student/2014130007");
        email_id=(TextView) view.findViewById(R.id.email_id);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar_class_structure, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseListAdapter<String> firebaseListAdapter=new FirebaseListAdapter<String>
                (((MainActivity)(getActivity())),
                        String.class,android.R.layout.simple_list_item_1
                        ,databaseReference){

            @Override
            protected void populateView(View v, String model, int position) {
                email_id.setText(model);
            }


        };
        listView.setAdapter(firebaseListAdapter);


//        messageRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Map<String,String> map=dataSnapshot.getValue(Map.class);
//                String email_id_string=map.get("email_id");
//                String name_string=map.get("student_name");
////                String message=dataSnapshot.getValue(String.class);
////                Log.v("firebase",message);
//                email_id.setText(email_id_string+"  "+name_string);
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
    }
}
