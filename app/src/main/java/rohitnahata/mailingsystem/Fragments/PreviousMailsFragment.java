package rohitnahata.mailingsystem.Fragments;


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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rohitnahata.mailingsystem.Activities.DetailActivity;
import rohitnahata.mailingsystem.PreviousMailModel;
import rohitnahata.mailingsystem.PreviousMailsAdapter;
import rohitnahata.mailingsystem.R;
import rohitnahata.mailingsystem.Utils.DividerItemDecoration;
import rohitnahata.mailingsystem.Utils.ItemClickSupport;


/**
 * A simple {@link Fragment} subclass.
 */
public class PreviousMailsFragment extends Fragment implements SearchView.OnQueryTextListener{

    private List<PreviousMailModel> previousMailModelList = new ArrayList<>();
    private List<PreviousMailModel> tempList;
    private RecyclerView recyclerView;
    private PreviousMailsAdapter mAdapter;
    private ArrayList<String> strAttachments;


    public PreviousMailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_previous_mails, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

        mAdapter = new PreviousMailsAdapter(previousMailModelList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        prepareData();
        tempList=new ArrayList<>(previousMailModelList);

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int pos, View v) {
                Intent i=new Intent(getContext(),DetailActivity.class);
                //LOAD DATA
                i.putExtra("RECIPIENTS",previousMailModelList.get(pos).getStrRecipients());
                i.putExtra("BODY",previousMailModelList.get(pos).getStrBody());
                i.putExtra("SUBJECT",previousMailModelList.get(pos).getStrSubject());
                i.putExtra("TIME",previousMailModelList.get(pos).getStrTime_sent());
                i.putExtra("ATTACHMENTS",previousMailModelList.get(pos).getStrAttachments());
                //START ACTIVITY
                startActivity(i);
                Toast.makeText(getContext(), "efef",Toast.LENGTH_SHORT).show();
            }
        });

        setHasOptionsMenu(true);
        return v;
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
//        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<PreviousMailModel> filteredModelList = filter(previousMailModelList, newText);
        mAdapter.animateTo(filteredModelList);
        recyclerView.scrollToPosition(0);
//        previousMailModelList=new ArrayList<>(tempList);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
//         previousMailModelList=new ArrayList<>(tempList);
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


    public void prepareData(){
        PreviousMailModel previousMailModel;
        previousMailModel = new PreviousMailModel(
                "Row ewfewf", "wfwefRofwrfwfRofrff", "ergregergerg reg re gergre ger g reg",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);
        strAttachments=new ArrayList<String>(3);
        strAttachments.add("eff");
        strAttachments.add("wefwef");
        previousMailModel = new PreviousMailModel(
                "Row ewfewf", "wfwefRofwrfwfRofrff", "ergregergerg reg re gergre ger g reg",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);
        strAttachments=null;
        previousMailModel = new PreviousMailModel(
                "Aow ewfewf", "wfwefRofwrfwfRofrff", "ergregergerg reg re gergre ger g reg",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);
        strAttachments=null;
        previousMailModel = new PreviousMailModel(
                "Sow ewfi3jf43 43ruj43r 43r 43r84nr43 ru3434ewf", "wfwefRofwrfwfRofrff", "ergregergerg reg re gergre ger g reg",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);
        strAttachments=null;
        previousMailModel = new PreviousMailModel(
                "Tow ewfewf", "wfwefRofwrfwfRofrff", "ergregergerg reg re gergre ger g reg",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);
        strAttachments=null;
        previousMailModel = new PreviousMailModel(
                "Zow ewfewf", "wfwefRofwrfwfwnfwnjf wfiwjifw f wifnwifw fwufniwefb Rofrff", "ergregergergwrfwr rfewv erv r3v  reg re gergre ger g reg",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);
        previousMailModel = new PreviousMailModel(
                "Row ewfewf", "wfwefRofwrfwfRofrff", "ergregergerg reg re gergre ger g reg",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);
        strAttachments=new ArrayList<String>(3);
        strAttachments.add("eff");
        strAttachments.add("wefwef");
        previousMailModel = new PreviousMailModel(
                "Row ewfewf", "wfwefRofwrfwfRofrff", "ergregergerg reg re gergre ger g reg",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);
        strAttachments=null;
        previousMailModel = new PreviousMailModel(
                "Aow ewfewf", "wfwefRofwrfwfRofrff", "ergregergerg reg re gergre ger g reg",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);

        previousMailModel = new PreviousMailModel(
                "Sow ewfi3jf43 43ruj43r 43r 43r84nr43 ru3434ewf", "wfwefRofwrfwfRofrff", "ergregergerg reg re gergre ger g reg",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);

        previousMailModel = new PreviousMailModel(
                "Tow ewfewf", "wfwefRofwrfwfRofrff", "ergregergerg reg re gergre ger g reg",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);

        previousMailModel = new PreviousMailModel(
                "Zow ewfewf", "wfwefRofwrfwfwnfwnjf wfiwjifw f wifnwifw fwufniwefb Rofrff", "ergregergergwrfwr rfewv erv r3v  reg re gergre ger g reg",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);
        previousMailModel = new PreviousMailModel(
                "Row ewfewf", "wfwefRofwrfwfRofrff", "ergregergerg reg re gergre ger g reg",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);
        strAttachments=new ArrayList<String>(3);
        strAttachments.add("eff");
        strAttachments.add("wefwef");
        previousMailModel = new PreviousMailModel(
                "Row ewfewf", "wfwefRofwrfwfRofrff", "ergregergerg reg re gergre ger g reg",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);
        strAttachments=null;
        previousMailModel = new PreviousMailModel(
                "Aow ewfewf", "wfwefRofwrfwfRofrff", "ergregergerg reg re gergre ger g reg",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);

        previousMailModel = new PreviousMailModel(
                "Sow ewfi3jf43 43ruj43r 43r 43r84nr43 ru3434ewf", "wfwefRofwrfwfRofrff", "ergregergerg reg re gergre ger g reg",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);

        previousMailModel = new PreviousMailModel(
                "Tow ewfewf", "wfwefRofwrfwfRofrff", "ergregergerg reg re gergre ger g reg",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);

        previousMailModel = new PreviousMailModel(
                "Zow ewfewf", "wfwefRofwrfwfwnfwnjf wfiwjifw f wifnwifw fwufniwefb Rofrff", "ergregergergwrfwr rfewv erv r3v  reg re gergre ger g reg",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);
        previousMailModel = new PreviousMailModel(
                "Row ewfewf", "wfwefRofwrfwfRofrff", "ergregergerg reg re gergre ger g reg",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);
        strAttachments=new ArrayList<String>(3);
        strAttachments.add("eff");
        strAttachments.add("wefwef");
        previousMailModel = new PreviousMailModel(
                "Row ewfewf", "wfwefRofwrfwfRofrff", "ergregergerg reg re gergre ger g reg",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);
        strAttachments=null;
        previousMailModel = new PreviousMailModel(
                "Aow ewfewf", "wfwefRofwrfwfRofrff", "ergregergerg reg re gergre ger g reg",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);

        previousMailModel = new PreviousMailModel(
                "Sow ewfi3jf43 43ruj43r 43r 43r84nr43 ru3434ewf", "wfwefRofwrfwfRofrff", "ergregergerg reg re gergre ger g reg",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);

        previousMailModel = new PreviousMailModel(
                "Tow ewfewf", "wfwefRofwrfwfRofrff", "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Minus, omnis! Consequuntur magni doloribus velit perspiciatis natus ducimus quos quod alias adipisci perferendis dolorum, reiciendis provident, illo expedita quidem fugiat, quaerat tempore! Mollitia obcaecati veritatis laboriosam possimus ullam amet dolore, blanditiis eius nostrum excepturi laborum voluptatibus vero modi eum consectetur, quis fugiat neque distinctio consequuntur, doloribus sunt animi maiores perferendis quaerat! Cum quis illo voluptates enim, voluptatem sapiente delectus! Architecto rerum, dolore iste adipisci. Quibusdam numquam aperiam velit atque, dolore sed, nesciunt, error quos porro iure sunt accusantium maxime consequatur magni? Quidem atque ullam, fugiat quam commodi vero, voluptas? Dicta consequuntur, ipsum neque omnis, error earum deserunt odit maiores, numquam harum iure. Deserunt ipsam tempora doloremque delectus rerum, nemo, optio omnis quod repudiandae deleniti voluptatem dolorum recusandae explicabo. Quis veniam repellat odio ipsam quod laboriosam assumenda rerum id non atque doloremque aspernatur, illum nobis unde, amet illo, eligendi doloribus! Neque enim non inventore sed facilis necessitatibus saepe eligendi reprehenderit error sunt repellat id voluptate vitae porro temporibus distinctio delectus maiores, nostrum accusamus animi odio. Cum asperiores quaerat ipsa aperiam optio deleniti id. Eius nam illo, consectetur, quaerat in non eaque dolores dolorum voluptatum delectus praesentium accusamus quae ut maxime fugiat vero recusandae, amet ea! Ab veniam vero perferendis molestias minima non aliquam, dicta magnam numquam reprehenderit, reiciendis iste voluptatibus, iure odio eveniet odit. Ratione aspernatur harum consequuntur, corrupti consequatur, quam eos, accusamus, ipsa dolore optio eius aliquid nam omnis labore. Ipsa ab aperiam, magnam cupiditate nihil velit sapiente sed neque illum delectus blanditiis voluptates. Dolorem ut voluptates veritatis id aperiam aspernatur laborum repellendus quia illo neque itaque quibusdam corporis eveniet eaque error hic molestias ad minima enim accusantium, porro, nobis nulla accusamus iure! Sapiente ab, ipsam ea consequatur facere, ipsa distinctio iure quibusdam natus voluptatibus harum eveniet tenetur, saepe, quam fuga repellendus. Non necessitatibus et libero facere ratione sit quae, vitae at, quo expedita repellendus quibusdam sint voluptatibus. Dolorem cumque dicta officiis iste a deleniti aut expedita unde iusto nemo. Doloribus eveniet saepe et omnis nisi, vero sit, labore corporis soluta eius ratione porro beatae tenetur totam. Quia commodi, quam veritatis accusamus tempora officia temporibus expedita quaerat tenetur reiciendis aperiam eveniet libero, placeat dolore deleniti ab eos, asperiores fuga quas enim explicabo, alias est mollitia ex eligendi? Maxime aliquid incidunt velit debitis ipsa, quod itaque minus, distinctio cum ratione repellat nesciunt ipsam, quas reprehenderit quaerat cumque culpa! Perspiciatis earum laboriosam at iure ex, quod eos consequuntur obcaecati ad assumenda. Distinctio voluptatibus, nemo dignissimos voluptatum ab laudantium minus nesciunt neque quaerat voluptas praesentium expedita earum corporis cupiditate soluta, dicta aliquam iusto rerum, consectetur deserunt incidunt, itaque quisquam dolorum quo tenetur. Eaque eum id veritatis odio ab dolore tempore ducimus enim reiciendis et consectetur, est minima fuga, pariatur necessitatibus nam, cumque corporis porro magni. In rem cumque aut, magnam est reiciendis, magni odit aperiam eaque, tenetur sapiente suscipit deleniti debitis, laboriosam eos voluptate sit nulla optio temporibus nam mollitia iure? Fugiat, numquam placeat ut, eum ducimus soluta beatae impedit enim perferendis debitis sed!",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);

        previousMailModel = new PreviousMailModel(
                "Zow ewfewf", "wfwefRofwrfwfwnfwnjf wfiwjifw f wifnwifw fwufniwefb Rofrff", "ergregergergwrfwr rfewv erv r3v  reg re gergre ger g reg",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);
        previousMailModel = new PreviousMailModel(
                "Row ewfewf", "wfwefRofwrfwfRofrff", "ergregergerg reg re gergre ger g reg",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);
        strAttachments=new ArrayList<String>(3);
        strAttachments.add("eff");
        strAttachments.add("wefwef");
        previousMailModel = new PreviousMailModel(
                "Row ewfewf", "wfwefRofwrfwfRofrff", "ergregergerg reg re gergre ger g reg",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);
        strAttachments=null;
        previousMailModel = new PreviousMailModel(
                "Aow ewfewf", "wfwefRofwrfwfRofrff", "ergregergerg reg re gergre ger g reg",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);

        previousMailModel = new PreviousMailModel(
                "Sow ewfi3jf43 43ruj43r 43r 43r84nr43 ru3434ewf", "wfwefRofwrfwfRofrff", "ergregergerg reg re gergre ger g reg",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);

        previousMailModel = new PreviousMailModel(
                "Tow ewfewf", "wfwefRofwrfwfRofrff", "ergregergerg reg re gergre ger g reg",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);

        previousMailModel = new PreviousMailModel(
                "Zow ewfewf", "wfwefRofwrfwfwnfwnjf wfiwjifw f wifnwifw fwufniwefb Rofrff", "ergregergergwrfwr rfewv erv r3v  reg re gergre ger g reg",
                "5:30pm",strAttachments);
        previousMailModelList.add(previousMailModel);



    }



}
