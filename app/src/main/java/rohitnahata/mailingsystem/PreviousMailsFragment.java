package rohitnahata.mailingsystem;


import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import rohitnahata.mailingsystem.Adapters.PreviousMailsAdapter;
import rohitnahata.mailingsystem.Models.PreviousMailModel;
import rohitnahata.mailingsystem.Utils.ItemClickSupport;


/**
 * A simple {@link Fragment} subclass.
 */
public class PreviousMailsFragment extends Fragment implements SearchView.OnQueryTextListener{

    private View view;
    private ArrayList<PreviousMailModel> previousMailModelList;
    private ArrayList<PreviousMailModel> tempList;
    private RecyclerView recyclerView;
    private PreviousMailsAdapter mAdapter;


    public PreviousMailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_previous_mails, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        tempList = ((App) getContext().getApplicationContext()).getPreviousMailModelList();
        previousMailModelList = new ArrayList<>(tempList);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new PreviousMailsAdapter(previousMailModelList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int pos, View v) {
                Intent i = new Intent(getContext(), DetailActivity.class);
                //LOAD DATA
                i.putExtra("RECIPIENTS", previousMailModelList.get(pos).getStrRecipients());
                i.putExtra("BODY", previousMailModelList.get(pos).getStrBody());
                i.putExtra("SUBJECT", previousMailModelList.get(pos).getStrSubject());
                i.putExtra("TIME", previousMailModelList.get(pos).getStrTime_sent());
                i.putExtra("ATTACHMENTS", previousMailModelList.get(pos).getStrAttachments());
                //START ACTIVITY
                startActivity(i);
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu, inflater);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        mAdapter.animateTo(previousMailModelList);
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
    public boolean onQueryTextChange(String newText) {
        previousMailModelList.clear();
        previousMailModelList.addAll(tempList);
        mAdapter.notifyDataSetChanged();
        final List<PreviousMailModel> filteredModelList = filter(previousMailModelList, newText);
        mAdapter.animateTo(filteredModelList);
        recyclerView.scrollToPosition(0);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        previousMailModelList.clear();
        previousMailModelList.addAll(tempList);
        mAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(0);
        return false;
    }

    private List<PreviousMailModel> filter(List<PreviousMailModel> models, String query) {
//                previousMailModelList=new ArrayList<>(tempList);

        query = query.toLowerCase();

        final List<PreviousMailModel> filteredModelList = new ArrayList<>();
        for (PreviousMailModel model : models) {
            final String recipients = model.getStrRecipients().toLowerCase();
            final String subject = model.getStrSubject().toLowerCase();
            final String body = model.getStrBody().toLowerCase();
            if (recipients.contains(query)) {
                filteredModelList.add(model);
            }
            else if (subject.contains(query)) {
                filteredModelList.add(model);
            }
            else if (body.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }


}
