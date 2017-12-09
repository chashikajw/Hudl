package layout;


import hudlmo.interfaces.loginpage.ProfileView;
import hudlmo.interfaces.loginpage.R;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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


import de.hdodenhof.circleimageview.CircleImageView;
import hudlmo.interfaces.loginpage.Settings;
import hudlmo.models.Meeting;
import hudlmo.models.User;
import hudlmo.models.UsersActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class Upcoming extends Fragment {

    private RecyclerView meetingLIst;

    private DatabaseReference mMeetingDatabase;
    private DatabaseReference mUserDatabase;


    private FirebaseAuth mAuth;

    private String mCurrent_user_id;

    private View mMainView;

    private FirebaseRecyclerAdapter<Meeting, MeetingViewHolder> meetingRecyclerViewAdapter;


    public Upcoming() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMainView = inflater.inflate(R.layout.fragment_upcoming, container, false);

        meetingLIst = (RecyclerView) mMainView.findViewById(R.id.upcoming_list);
        mAuth = FirebaseAuth.getInstance();

        mCurrent_user_id = mAuth.getCurrentUser().getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        String meetingID = Integer.toString((int)System.currentTimeMillis());

        mMeetingDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrent_user_id).child("meetings");

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

        meetingRecyclerViewAdapter = new FirebaseRecyclerAdapter<Meeting,MeetingViewHolder>(

                Meeting.class,
                R.layout.users_single_layout,
                MeetingViewHolder.class,
                mMeetingDatabase


        ) {
            @Override
            protected void populateViewHolder(MeetingViewHolder MeetingViewHolder, Meeting meeting, int position) {

                MeetingViewHolder.setDisplayMeetingname(meeting.getMeetingName());
                MeetingViewHolder.setDisplayAdminName(meeting.getAdmin());
                // usersViewHolder.setUserImage(users.getThumb_image(), getApplicationContext());

                final String mName = meeting.getMeetingName();
                final String mAdmin = meeting.getAdmin();
                final String mDescription = meeting.getDescription();
                final int positon = position;

                MeetingViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        CharSequence options[] = new CharSequence[]{"Participate", "Reject"};

                        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                        builder.setTitle(mName);
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //Click Event for each item.
                                if (i == 0) {

                                    Intent profileIntent = new Intent(getContext(), Settings.class);
                                    profileIntent.putExtra("user_id", "fd");
                                    startActivity(profileIntent);

                                }

                                if (i == 1) {


                                    meetingRecyclerViewAdapter.getRef(positon).removeValue();

                                }

                            }
                        });

                        builder.show();

                    }
                });


            }
            /*
            @Override
            protected void populateViewHolder(final UsersViewHolder UsersViewHolder, Meeting meeting, int i) {

                UsersViewHolder.setDate(meeting.getDate());

                final String list_user_id = getRef(i).getKey();

                mUsersDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        final String userName = dataSnapshot.child("name").getValue().toString();
                        String userThumb = dataSnapshot.child("username").getValue().toString();

                        if(dataSnapshot.hasChild("online")) {

                            String userOnline = dataSnapshot.child("online").getValue().toString();
                            UsersViewHolder.setUserOnline(userOnline);

                        }

                        UsersViewHolder.setName(userName);
                        // UsersViewHolder.setUserImage(userThumb, getContext());

                        UsersViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                CharSequence options[] = new CharSequence[]{"Open Profile", "Send message"};

                                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                builder.setTitle("Select Options");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        //Click Event for each item.
                                        if(i == 0){

                                            Intent profileIntent = new Intent(getContext(), Settings.class);
                                            profileIntent.putExtra("user_id", list_user_id);
                                            startActivity(profileIntent);

                                        }

                                        if(i == 1){

                                            Intent chatIntent = new Intent(getContext(), Settings.class);
                                            chatIntent.putExtra("user_id", list_user_id);
                                            chatIntent.putExtra("user_name", userName);
                                            startActivity(chatIntent);

                                        }

                                    }
                                });

                                builder.show();

                            }
                        });


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }*/
        };

        meetingLIst.setAdapter(meetingRecyclerViewAdapter);


    }


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


      /*  public void setUserImage(String thumb_image, Context ctx){

            CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.user_single_image);
            Picasso.with(ctx).load(thumb_image).placeholder(R.drawable.default_avatar).into(userImageView);

        }*/

        public void setUserOnline(String online_status) {

            ImageView userOnlineView = (ImageView) mView.findViewById(R.id.user_single_online_icon);

            if(online_status.equals("true")){

                userOnlineView.setVisibility(View.VISIBLE);

            } else {

                userOnlineView.setVisibility(View.INVISIBLE);

            }

        }


    }




}