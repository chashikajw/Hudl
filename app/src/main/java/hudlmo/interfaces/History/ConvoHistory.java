package hudlmo.interfaces.History;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.Comparator;

import hudlmo.interfaces.loginpage.R;
import hudlmo.interfaces.History.HistoryListClass;
import static hudlmo.interfaces.History.ConvoHistoryListClass.convoHistory;

/**
 * Created by Shalini PC on 12/6/2017.
 */

public class ConvoHistory extends AppCompatActivity {
    private static final String TAG = "Database1";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference db_ref = database.getReference().child("Users");
    RadioGroup radiogroup;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_view);

        radiogroup = (RadioGroup) findViewById(R.id.radioGrp);
        // text1 = (TextView) findViewById(R.id.textView1);
        Button SelectBtn = (Button) findViewById(R.id.SelectBtn);
        //Button signoutbtn = (Button) findViewById(R.id.signoutbtn);


        radiogroup.clearCheck();    //this clears the radiogroup everytime datachanges to that it don't duplicate
        radiogroup.removeAllViews();


        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                radiogroup.clearCheck();    //this clears the radiogroup everytime datachanges to that it don't duplicate
                radiogroup.removeAllViews();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                HistoryListClass.historyList.clear();    //this clears the list.. so it's repopuated again when data changes
                convoHistory.clear();

                for (DataSnapshot historySnap : dataSnapshot.getChildren()) {
                    String key = historySnap.getKey();
                    String convoName = (String) historySnap.child("groupName").getValue();
                    String description = (String) historySnap.child("description").getValue();
                    String date = (String) historySnap.child("date").getValue();
                    String time = (String) historySnap.child("time").getValue();
                    String duration = (String) historySnap.child("duration").getValue();
                    String compDate = (String) historySnap.child("comp_date").getValue();
                    Log.d(TAG, key + " " + convoName + " " + date);

                    HistoryClass job = new HistoryClass(key,convoName,date,time,description,duration);

                    HistoryListClass.historyList.add(job);   //adding job object to a list

                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
