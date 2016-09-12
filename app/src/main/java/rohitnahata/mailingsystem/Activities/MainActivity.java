    package rohitnahata.mailingsystem.Activities;

    import android.app.Activity;
    import android.app.Dialog;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.content.res.Resources;
    import android.database.Cursor;
    import android.graphics.Bitmap;
    import android.graphics.drawable.BitmapDrawable;
    import android.support.v7.app.AlertDialog;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;

    import android.support.design.widget.TabLayout;
    import android.support.v4.app.Fragment;
    import android.support.v4.app.FragmentManager;
    import android.support.v4.app.FragmentPagerAdapter;
    import android.support.v4.view.ViewPager;
    import android.support.v7.widget.DefaultItemAnimator;
    import android.support.v7.widget.LinearLayoutManager;
    import android.support.v7.widget.RecyclerView;
    import android.support.v7.widget.Toolbar;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.view.Window;
    import android.widget.Button;
    import android.widget.EditText;

    import java.util.ArrayList;
    import java.util.List;

    import rohitnahata.mailingsystem.Fragments.DatabaseFragment;
    import rohitnahata.mailingsystem.Fragments.MailFragment;
    import rohitnahata.mailingsystem.Fragments.PreviousMailsFragment;
    import rohitnahata.mailingsystem.R;


    public class MainActivity extends AppCompatActivity {


        EditText recipients,body,subject;
//        RecyclerView rv;
//        MyAdapter adapter;
//        ArrayList<PreviousMailDBData> previousMailDBDatas=new ArrayList<>();



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            //noinspection ConstantConditions
            getSupportActionBar().setTitle("Mailing System");
            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
            setupViewPager(viewPager);


            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);

//            rv= (RecyclerView) findViewById(R.id.recycler_view);
//            //SET ITS PROPS
//            rv.setLayoutManager(new LinearLayoutManager(this));
//            rv.setItemAnimator(new DefaultItemAnimator());
//            //ADAPTER
//            adapter=new MyAdapter(this,previousMailDBDatas);
//            retrieve();

    //        final Resources res = getResources();
    //        final int tileSize = res.getDimensionPixelSize(R.dimen.letter_tile_size);
    //
    //        final LetterTileProvider tileProvider = new LetterTileProvider(this);
    //        final Bitmap letterTile = tileProvider.getLetterTile("name", "key", tileSize, tileSize);
    //
    //        getActionBar().setIcon(new BitmapDrawable(getResources(), letterTile));


        }

    //    private void showDialog()
    //    {
    //        Dialog d=new Dialog(this);
    //        //NO TITLE
    //        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
    //        //layout of dialog
    //        d.setContentView(R.layout.custom_layout);
    //        nameTxt= (EditText) d.findViewById(R.id.nameEditTxt);
    //        posTxt= (EditText) d.findViewById(R.id.posEditTxt);
    //        Button savebtn= (Button) d.findViewById(R.id.saveBtn);
    //        Button retrieveBtn= (Button) d.findViewById(R.id.retrieveBtn);
    //        //ONCLICK LISTENERS
    //        savebtn.setOnClickListener(new View.OnClickListener() {
    //            @Override
    //            public void onClick(View v) {
    //                save(nameTxt.getText().toString(),posTxt.getText().toString());
    //            }
    //        });
    //        retrieveBtn.setOnClickListener(new View.OnClickListener() {
    //            @Override
    //            public void onClick(View v) {
    //                retrieve();
    //            }
    //        });
    //        //SHOW DIALOG
    //        d.show();
    //    }
    //    //SAVE
    //    private void save(String name,String pos)
    //    {
    //        DBAdapter db=new DBAdapter(this);
    //        //OPEN
    //        db.openDB();
    //        //INSERT
    //        long result=db.add(name,pos);
    //        if(result>0)
    //        {
    //            nameTxt.setText("");
    //            posTxt.setText("");
    //        }else
    //        {
    //            Snackbar.make(nameTxt,"Unable To Insert",Snackbar.LENGTH_SHORT).show();
    //        }
    //        //CLOSE
    //        db.close();
    //        //refresh
    //        retrieve();
    //    }


        //RETRIEVE
//        private void retrieve()
//        {
//            DBAdapter db=new DBAdapter(this);
//            //OPEN
//            db.openDB();
//            previousMailDBDatas.clear();
//            //SELECT
//            Cursor c=db.getAllPreviousMails();
//            //LOOP THROUGH THE DATA, ADDING IT TO ARRAYLIST
//            while (c.moveToNext())
//            {
//                int id=c.getInt(0);
//                String recipient=c.getString(1);
//                String subject=c.getString(2);
//                String body=c.getString(3);
////                PreviousMailDBData p=new PreviousMailDBData(id,recipient,subject,body);
//                //ADD TO DB
////                previousMailDBDatas.add(p);
//            }
//            //SET ADAPTER TO RV
//            if(!(previousMailDBDatas.size()<1))
//            {
//                rv.setAdapter(adapter);
//            }
//        }
//        @Override
//        protected void onResume() {
//            super.onResume();
//            retrieve();
//        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_toolbar_main, menu);

            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();
            return id == R.id.settings || super.onOptionsItemSelected(item);

        }

        @Override
        public void onBackPressed() {

            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                            homeIntent.addCategory( Intent.CATEGORY_HOME );
                            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(homeIntent);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();



        }

        private void setupViewPager(ViewPager viewPager) {
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new MailFragment(), "Email");
            adapter.addFragment(new DatabaseFragment(), "Classrooms");
            adapter.addFragment(new PreviousMailsFragment(), "Sent Mails");
            viewPager.setAdapter(adapter);
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


