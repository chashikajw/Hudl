package layout;



import hudlmo.interfaces.History.HistoryView;
import hudlmo.interfaces.Video.VideoCoference;
import hudlmo.interfaces.loginpage.R;

import android.content.DialogInterface;
import android.content.Intent;
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


import hudlmo.interfaces.loginpage.Settings;
import hudlmo.models.HistoryC;
import hudlmo.models.Meeting;

/**
 * Created by Shalini PC on 12/8/2017.
 */

public class History extends Fragment {

    private RecyclerView meetingList;

    //new database references for hisotry and user
    private DatabaseReference mHistoryDatabase;
    private DatabaseReference mUserDatabase;

    private FirebaseRecyclerAdapter<Meeting, HistoryViewHolder> HistoryRecyclerViewAdapter;

    private FirebaseAuth mAuth;

    private String mCurrent_user_id;

    private View mRootView;


    public History() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //set the main view to history fragment
        mRootView = inflater.inflate(R.layout.fragment_history, container, false);

        //add the meeting history summary to main page of history
        meetingList = (RecyclerView) mRootView.findViewById(R.id.meeting_list);
        mAuth = FirebaseAuth.getInstance();

        //get the current user id and current user's references to the database
        mCurrent_user_id = mAuth.getCurrentUser().getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        //get the first conversation history as history
        //called child("1")
        mHistoryDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrent_user_id).child("Meeting").child("History");

        //offline synchronize
        mHistoryDatabase.keepSynced(true);

        //set the list wiev to the device sizes
        meetingList.setHasFixedSize(true);
        meetingList.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inflate the layout for this fragment
        return mRootView;
    }


    @Override
    public void onStart() {
        super.onStart();

        HistoryRecyclerViewAdapter = new FirebaseRecyclerAdapter<Meeting,HistoryViewHolder>(

                Meeting.class,
                R.layout.history_single_layout,
                HistoryViewHolder.class,
                mHistoryDatabase


        ) {
            @Override
            protected void populateViewHolder(HistoryViewHolder historyViewHolder,Meeting meeting, int position) {

                //create inner class "HistoryViewHolder" to hold the data
                historyViewHolder.setDisplayMeetingname(meeting.getMeetingName());
                historyViewHolder.setDisplayDate(meeting.getSheduleDate());

                //set the data to variables
                final String mName = meeting.getMeetingName();
                final String mAdmin = meeting.getInitiator();
                final String mDescription = meeting.getDescription();
                final String sheduletime = meeting.getSheduleDate();
                final String roomid = meeting.getRoomId();
                final int positon = position;


                historyViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]{"View","Delete"};

                        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                        builder.setTitle(mName);
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (i == 0) {
                                    // if user clicks View, all details will be shown : i = 0 = "View"
                                    Intent profileIntent = new Intent(getContext(), HistoryView.class);
                                    profileIntent.putExtra("sheduletime", sheduletime);
                                    profileIntent.putExtra("roomid", roomid);
                                    startActivity(profileIntent);

                                }
                                if (i == 1) {
                                    // if user clicks Delete, the item will be delete : i = 1 = "Delete"
                                    HistoryRecyclerViewAdapter.getRef(positon).removeValue();
                                }
                            }
                        });

                        builder.show();

                    }
                });


            }

        };

        meetingList.setAdapter(HistoryRecyclerViewAdapter);
    }


    //Inner class to hold data
    public static class HistoryViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public HistoryViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setDisplayMeetingname(String groupname){

            TextView meetingNameView = (TextView) mView.findViewById(R.id.convo_name_layout);
            meetingNameView.setText(groupname);

        }

        public void setDisplayDate(String sdate){
            TextView conDate = (TextView) mView.findViewById(R.id.date_layout);
            conDate.setText(sdate);

        }
    }
}
