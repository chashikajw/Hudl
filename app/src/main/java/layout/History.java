package layout;


import hudlmo.interfaces.Video.VideoCoference;
import hudlmo.interfaces.loginpage.ProfileView;
import hudlmo.interfaces.loginpage.R;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import hudlmo.interfaces.loginpage.Settings;
import hudlmo.models.Meeting;
import hudlmo.models.User;
import hudlmo.models.UsersActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class History extends Fragment {

    private RecyclerView meetingLIst;
    private DatabaseReference mMeetingDatabase;
    private DatabaseReference mUserDatabase;
    private FirebaseAuth mAuth;
    private String mCurrent_user_id;
    private View mMainView;
    private FirebaseRecyclerAdapter<Meeting, MeetingViewHolder> meetingRecyclerViewAdapter;


    public History() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMainView = inflater.inflate(R.layout.fragment_history, container, false);

        //initialise the meeting list
        meetingLIst = (RecyclerView) mMainView.findViewById(R.id.history_meeting_list);

        //get the loged user's id
        mAuth = FirebaseAuth.getInstance();
        mCurrent_user_id = mAuth.getCurrentUser().getUid();

        //get the reference of Users
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        //get
        String meetingID = Integer.toString((int)System.currentTimeMillis());

        //get the reference of current users meeting History class
        mMeetingDatabase = mUserDatabase.child(mCurrent_user_id).child("meetings").child("history");

        //offline syncronize
        mMeetingDatabase.keepSynced(true);


        meetingLIst.setHasFixedSize(true);
        meetingLIst.setLayoutManager(new LinearLayoutManager(getContext()));
        // Inflate the layout for this fragment
        return mMainView;
    }




    @Override
    public void onStart() {
        super.onStart();
        //define the recyle view to store meeting objects
        meetingRecyclerViewAdapter = new FirebaseRecyclerAdapter<Meeting,MeetingViewHolder>(
                Meeting.class,
                R.layout.users_single_layout,
                MeetingViewHolder.class,
                mMeetingDatabase


        ) {
            @Override
            protected void populateViewHolder(MeetingViewHolder MeetingViewHolder,Meeting meeting, int position) {

                //get the details of recyleview object
                final String mName = meeting.getMeetingName();
                final String mAdmin = meeting.getInitiator();
                final String mDescription = meeting.getDescription();
                final long sheduletime = Long.parseLong(meeting.getSheduleDate());
                final String roomid = meeting.getRoomId();
                final int positon = position;

                //display data in one item(in single layout)
                MeetingViewHolder.setDisplayMeetingname(mAdmin);
                MeetingViewHolder.setDisplayAdminName(mDescription);
                // usersViewHolder.setUserImage(users.getThumb_image(), getApplicationContext());


                MeetingViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //create alert dialog (with two clicks events) when click a meeting item
                        CharSequence options[] = new CharSequence[]{"Participate", "Reject"};
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                        builder.setTitle(mName);
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //Click Event for each item.
                                if (i == 0) {
                                    //send meeting data to the conference(participation)
                                    Intent profileIntent = new Intent(getContext(), VideoCoference.class);
                                    profileIntent.putExtra("sheduletime", sheduletime);
                                    profileIntent.putExtra("roomid", roomid);
                                    startActivity(profileIntent);
                                }
                                if (i == 1) {
                                    //delete the meeting from database and list view
                                    meetingRecyclerViewAdapter.getRef(positon).removeValue();

                                }

                            }
                        });

                        builder.show();

                    }
                });


            }

        };

        //set the recycle view for adapter
        meetingLIst.setAdapter(meetingRecyclerViewAdapter);

    }

    //create classs meting view holder to hold data in a item
    public static class MeetingViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public MeetingViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDisplayMeetingname(String groupname){

            TextView meetingNameView = (TextView) mView.findViewById(R.id.user_single_name);
            meetingNameView.setText(groupname);

        }

        public void setDisplayAdminName(String admin){


            TextView adminName = (TextView) mView.findViewById(R.id.user_single_status);
            adminName.setText(admin);


        }

        public void setCountdown(String countdownTostart){

            TextView meetingNameView = (TextView) mView.findViewById(R.id.user_single_timer);
            meetingNameView.setText(countdownTostart);

        }



    }


}