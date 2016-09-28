package rohitnahata.mailingsystem;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

import java.util.ArrayList;

import rohitnahata.mailingsystem.Models.StudentDetailsModel;
import rohitnahata.mailingsystem.RecyclerAdapters.StudentDetailsAdapter;
import rohitnahata.mailingsystem.Utils.DividerItemDecoration;


/**
 * A simple {@link Fragment} subclass.
 */
public class DatabaseFragment extends Fragment implements SearchView.OnQueryTextListener {

    StudentDetailsAdapter mAdapter;
    RecyclerView recyclerView;
    View view;
    private ArrayList<StudentDetailsModel> studentDetailsModelList;
    private ArrayList<StudentDetailsModel> temp;

    public DatabaseFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        temp = ((App) getContext().getApplicationContext()).getStudentDetailsModelList();
        studentDetailsModelList = new ArrayList<>(temp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_database, container, false);
        setHasOptionsMenu(true);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewDatabase);
        mAdapter = new StudentDetailsAdapter(studentDetailsModelList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
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
                        mAdapter.animateTo(studentDetailsModelList);
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
    public boolean onQueryTextSubmit(String query) {
        studentDetailsModelList.clear();
        studentDetailsModelList.addAll(temp);
        mAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(0);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        studentDetailsModelList.clear();
        studentDetailsModelList.addAll(temp);
        mAdapter.notifyDataSetChanged();
        final ArrayList<StudentDetailsModel> filteredModelList = filter(temp, newText);
        mAdapter.animateTo(filteredModelList);
        recyclerView.scrollToPosition(0);
        return true;
    }

    private ArrayList<StudentDetailsModel> filter(ArrayList<StudentDetailsModel> models, String query) {
        query = query.toLowerCase();

        final ArrayList<StudentDetailsModel> filteredModelList = new ArrayList<>();
        for (StudentDetailsModel model : models) {
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
