package rohitnahata.mailingsystem;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import rohitnahata.mailingsystem.Adapters.StudentDetailsAdapter;
import rohitnahata.mailingsystem.Models.StudentDetails;
import rohitnahata.mailingsystem.Utils.DividerItemDecoration;
import rohitnahata.mailingsystem.Utils.TinyDB;


/**
 * A simple {@link Fragment} subclass.
 */
public class DatabaseFragment extends Fragment implements SearchView.OnQueryTextListener {


    StudentDetails studentDetails;
    String strEmail, strUID, strName, strClass;
    StudentDetailsAdapter mAdapter;
    RecyclerView recyclerView;
    View view;
    //    SharedPreferences sharedPreferences;
    TinyDB tinyDB;
    private ArrayList<StudentDetails> studentDetailsList = new ArrayList<>();
    private ArrayList<StudentDetails> temp = new ArrayList<>();
    private ArrayList<String> className = new ArrayList<>();



    public DatabaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_database, container, false);
        setHasOptionsMenu(true);
        tinyDB = new TinyDB(getContext());
        tinyDB.putListObject("classStudents", temp);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewDatabase);
        mAdapter = new StudentDetailsAdapter(studentDetailsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);
        final MenuItem item = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        mAdapter.animateTo(studentDetailsList);
                        recyclerView.scrollToPosition(0);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });

    }
    @Override
    public void onStart() {
        super.onStart();
//        sharedPreferences=get

        new Firebase(App.BASE_URL).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                deleteData();
                for (DataSnapshot alert : dataSnapshot.getChildren()) {
                    strClass = alert.getKey();
                    className.add(alert.getKey());
                    for (DataSnapshot recipient : alert.getChildren()) {
                        strEmail = (String) recipient.child("email_id").getValue();
                        strName = (String) recipient.child("student_name").getValue();
                        strUID = (String) recipient.child("id").getValue();
                        addData(strUID, strName, strEmail, strClass);
                    }
                }
                mAdapter.notifyDataSetChanged();
                String[] classNameArray;
                classNameArray = className.toArray(new String[className.size()]);
                tinyDB.putListString("className", classNameArray);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    public void deleteData() {
        studentDetailsList.clear();
        className.clear();
        temp.clear();
    }

    public void addData(String n1, String n2, String n3, String n4) {
        studentDetails = new StudentDetails(n1, n2, n3, n4);
        studentDetailsList.add(studentDetails);
        temp.add(studentDetails);

    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        studentDetailsList.clear();
        studentDetailsList.addAll(temp);
        mAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(0);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        studentDetailsList.clear();
        studentDetailsList.addAll(temp);
        mAdapter.notifyDataSetChanged();
        final List<StudentDetails> filteredModelList = filter(temp, newText);
        mAdapter.animateTo(filteredModelList);
        recyclerView.scrollToPosition(0);
        return true;
    }

    private List<StudentDetails> filter(List<StudentDetails> models, String query) {
        query = query.toLowerCase();

        final List<StudentDetails> filteredModelList = new ArrayList<>();
        for (StudentDetails model : models) {
            final String text = model.getName().toLowerCase();
            final String id = model.getId().toLowerCase();
            final String classroom = model.getClassroom().toLowerCase();
            final String email_id = model.getEmail_id().toLowerCase();

            if (text.contains(query)) {
                filteredModelList.add(model);
            } else if (id.contains(query)) {
                filteredModelList.add(model);
            } else if (classroom.contains(query)) {
                filteredModelList.add(model);
            } else if (email_id.contains(query)) {
                filteredModelList.add(model);
            }

        }
        return filteredModelList;
    }
}
