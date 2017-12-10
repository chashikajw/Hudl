package hudlmo.interfaces.History;

import hudlmo.interfaces.loginpage.R;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * Created by Shalini PC on 12/6/2017.
 */

public class HistoryView extends AppCompatActivity {
    private TextView convoName;
    private TextView date;
    private TextView description;
    private TextView time;
    private TextView duration;

    private String conName;
    private String conDate;
    private String conDescription;
    private String conTime;
    //private String conDuration;

    private DatabaseReference mDatabase;
    public FirebaseAuth mAuth;

    private ListView setDetail;
    private ArrayList<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_main);

        setDetail = findViewById(R.id.history_list); //************set the list name in the layout**********
        String mCurrentUser = FirebaseAuth.getInstance().getUid();

        // Instance of firebase database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("User").child(mCurrentUser).child("Meeting").child("History");
        
        mAuth = FirebaseAuth.getInstance();

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.history_single_layout, items);

        setDetail.setAdapter(arrayAdapter);

        //create child event listener to listen to the database
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //if the child added
                String value = dataSnapshot.getValue(String.class);

                items.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Set the message to read failure
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        convoName = (TextView)findViewById(R.id.convo_name_layout);
        date = (TextView)findViewById(R.id.date_layout);

        //get other activity details
        Bundle bundle = getIntent().getExtras();
        conName = bundle.getString("groupName");
        conDate = bundle.getString("date");
        conDescription = bundle.getString("description");
        conTime = bundle.getString("time");

        convoName.setText(conName);
        date.setText(conDate);
        description.setText(conDescription);
        time.setText(conTime);

    }
}