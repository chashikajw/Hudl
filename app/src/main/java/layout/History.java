package layout;



import hudlmo.interfaces.loginpage.R;

import android.content.DialogInterface;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hudlmo.models.Meeting;

/**
 * Created by Shalini PC on 12/6/2017.
 */



/**
 * A simple {@link Fragment} subclass.
 */
public class History extends Fragment {

    private RecyclerView historyMeetingList;

    private DatabaseReference mMeetingDatabase;
    private DatabaseReference mUserDatabase;
    private FirebaseAuth mAuth;
    private String mCurrent_user_id;
    private View mMainView;

    private FirebaseRecyclerAdapter<Meeting, MeetingViewHolder> hisotryRecyclerViewAdapter;


    public History() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMainView = inflater.inflate(R.layout.fragment_history, container, false);

        //initialise the meeting list

        historyMeetingList = (RecyclerView) mMainView.findViewById(R.id.history_meeting_list);


        //get the loged user's id
        mAuth = FirebaseAuth.getInstance();
        mCurrent_user_id = mAuth.getCurrentUser().getUid();

        //get the reference of Users
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        //get
        String meetingID = Integer.toString((int) System.currentTimeMillis());

        //get the reference of current users meeting History class
        mMeetingDatabase = mUserDatabase.child(mCurrent_user_id).child("meetings").child("history");

        //offline syncronize
        mMeetingDatabase.keepSynced(true);


        historyMeetingList.setHasFixedSize(true);
        historyMeetingList.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inflate the layout for this fragment
        return mMainView;
    }


    @Override
    public void onStart() {
        super.onStart();
        //define the recyle view to store meeting objects

        hisotryRecyclerViewAdapter = new FirebaseRecyclerAdapter<Meeting, MeetingViewHolder>(

                Meeting.class,
                R.layout.users_single_layout,
                MeetingViewHolder.class,
                mMeetingDatabase


        ) {
            @Override
            protected void populateViewHolder(MeetingViewHolder MeetingViewHolder, Meeting meeting, int position) {

                //get the details of recyleview object
                final String mName = meeting.getMeetingName();
                final String mAdmin = meeting.getInitiator();
                final String mDescription = meeting.getDescription();

                final long scheduletime = Long.parseLong(meeting.getSheduleDate());

                final String roomid = meeting.getRoomId();
                final int positon = position;

                //display data in one item(in single layout)

                MeetingViewHolder.setDisplayMeetingname(mName);
                MeetingViewHolder.setDisplayAdminName(mAdmin);
                MeetingViewHolder.setDisplayScheduleTime(scheduletime);


                MeetingViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //create alert dialog (with two clicks events) when click a meeting item

                        CharSequence options[] = new CharSequence[]{"Delete"};

                        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                        builder.setTitle(mName);
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //Click Event for each item.
                                if (i == 0) {
                                    //delete the meeting from database and list view
                                    hisotryRecyclerViewAdapter.getRef(positon).removeValue();
                                }

                            }
                        });

                        builder.show();

                    }
                });


            }

        };

        //set the recycle view for adapter

        historyMeetingList.setAdapter(hisotryRecyclerViewAdapter);


    }

    //create classs meting view holder to hold data in a item
    public static class MeetingViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public MeetingViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDisplayMeetingname(String groupname) {

            TextView meetingNameView = (TextView) mView.findViewById(R.id.user_single_name);
            meetingNameView.setText(groupname);

        }

        public void setDisplayAdminName(String admin) {

            TextView adminName = (TextView) mView.findViewById(R.id.user_single_status);
            adminName.setText(admin);

        }

        public void setDisplayScheduleTime(long sheduletime) {
            TextView meetingScheduletime = (TextView) mView.findViewById(R.id.user_single_timer);
            meetingScheduletime.setText((int) sheduletime);

        }
    }
}
