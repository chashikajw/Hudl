package layout;


import hudlmo.interfaces.History.HistoryView;
import hudlmo.interfaces.loginpage.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import hudlmo.models.HistoryC;

/**
 * Created by Shalini PC on 12/8/2017.
 */

public class History extends Fragment {

    private RecyclerView meetingList;

    private DatabaseReference mHistoryDatabase;
    private DatabaseReference mUserDatabase;


    private FirebaseAuth mAuth;

    private String mCurrent_user_id;

    private View mRootView;


    public History() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_history, container, false);

        meetingList = (RecyclerView) mRootView.findViewById(R.id.meeting_list);
        mAuth = FirebaseAuth.getInstance();

        mCurrent_user_id = mAuth.getCurrentUser().getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mHistoryDatabase = FirebaseDatabase.getInstance().getReference().child("Meeting").child(mCurrent_user_id);

        //offline synchronize
        mHistoryDatabase.keepSynced(true);

        meetingList.setHasFixedSize(true);
        meetingList.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inflate the layout for this fragment
        return mRootView;
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<HistoryC, HistoryViewHolder> HistoryRecyclerViewAdapter = new FirebaseRecyclerAdapter<HistoryC,HistoryViewHolder>(

                HistoryC.class,
                R.layout.history_single_layout,
                HistoryViewHolder.class,
                mHistoryDatabase


        ) {
            @Override
            protected void populateViewHolder(HistoryViewHolder historyViewHolder, HistoryC history, int position) {

                historyViewHolder.setDisplayDate(history.getDate());
                historyViewHolder.setDisplayConName(history.getConvoName());
                historyViewHolder.setDisplayDescription(history.getDescription());
                historyViewHolder.setDisplayTime(history.getStartTime());
                historyViewHolder.setDisplayDuration(history.getDuration());


                final String convoName = history.getConvoName();
                final String date = history.getDate();
                final String description = history.getDescription();
                final String time = history.getStartTime();
                final String duration = history.getDuration();

                historyViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent historyIntent = new Intent(getContext(), HistoryView.class);
                        historyIntent.putExtra("groupName", convoName);
                        historyIntent.putExtra("date", date);
                        historyIntent.putExtra("description", description);
                        historyIntent.putExtra("time", time);
                        historyIntent.putExtra("duration", duration);
                        startActivity(historyIntent);
                    }
                });

            }
        };

        meetingList.setAdapter(HistoryRecyclerViewAdapter);


    }


    public static class HistoryViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public HistoryViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDisplayDate(String date) {

            TextView DescriptionView = (TextView) mView.findViewById(R.id.date);
            DescriptionView.setText(date);

        }

        public void setDisplayConName(String cname) {

            TextView ConNameView = (TextView) mView.findViewById(R.id.convoName);
            ConNameView.setText(cname);
        }

        public void setDisplayDescription(String desc) {

            TextView DescriptionView = (TextView) mView.findViewById(R.id.description);
            DescriptionView.setText(desc);
        }

        public void setDisplayTime(String tme) {

            TextView TimeView = (TextView) mView.findViewById(R.id.time);
            TimeView.setText(tme);
        }

        public void setDisplayDuration(String dur) {

            TextView DurationView = (TextView) mView.findViewById(R.id.duration);
            DurationView.setText(dur);
        }


    }
}
