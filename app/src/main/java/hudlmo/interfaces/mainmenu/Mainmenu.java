package hudlmo.interfaces.mainmenu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import hudlmo.interfaces.Video.VideoCoference;

import hudlmo.interfaces.createMeeting.CreateMeeting;
import hudlmo.interfaces.loginpage.AddContacts;
import hudlmo.interfaces.loginpage.R;
import hudlmo.interfaces.loginpage.Settings;
import hudlmo.interfaces.loginpage.login;

public class Mainmenu extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;



    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               startActivity(new Intent(Mainmenu.this, CreateMeeting.class));
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mainmenu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(Mainmenu.this, Settings.class));
        }
        if (id == R.id.action_addcontact) {
            startActivity(new Intent(Mainmenu.this, AddContacts.class));
        }
        if (id == R.id.action_Logout) {
            FirebaseAuth fAuth = FirebaseAuth.getInstance();
            fAuth.signOut();
            //firebaseAuth.signOut();
            //startActivity(new Intent(this,login.class));
            startActivity(new Intent(this,login.class));
        }


        return super.onOptionsItemSelected(item);
    }





    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         *
         */

        private FirebaseAuth mAuth;

        private DatabaseReference mDatabase;
        private static final String ARG_SECTION_NUMBER = "section_number";

        private View rootViewm = null;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();

            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            rootViewm = inflater.inflate(R.layout.fragment_mainmenu, container, false);
            if (getArguments().getInt(ARG_SECTION_NUMBER)==0){

                View rootView = inflater.inflate(R.layout.fragment_mainmenu, container, false);
                TextView textView = (TextView) rootView.findViewById(R.id.section_label);





                return rootView;
            }
            else if (getArguments().getInt(ARG_SECTION_NUMBER)==1){

                View rootView = inflater.inflate(R.layout.fragment_groups, container, false);
                TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                return rootView;
            }
            else if (getArguments().getInt(ARG_SECTION_NUMBER)==2){

                View rootView = inflater.inflate(R.layout.fragment_history, container, false);
                TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                return rootView;
            }
            else if (getArguments().getInt(ARG_SECTION_NUMBER)==3){

                View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);
                TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                return rootView;
            }

            mAuth = FirebaseAuth.getInstance();
            String userId = mAuth.getCurrentUser().getUid();
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("roomId");





            Button button = (Button) rootViewm.findViewById(R.id.call);
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    try{
                        mDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String roomid = (String) dataSnapshot.getValue();

                                Intent intent = new Intent(getActivity(), VideoCoference.class);
                                intent.putExtra("roomId", roomid);
                                startActivity(intent);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                System.out.println("The read failed: " + databaseError.getCode());
                            }
                        });
                    }catch (Exception e){

                    }







                }
            });



            return rootViewm;
        }





    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Groups";
                case 1:
                    return "History";
                case 2:
                    return "Contacts";
                case 3:
                    return "Upcoming";
            }
            return null;
        }
    }
}
