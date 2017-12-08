package hudlmo.interfaces.History;

import hudlmo.interfaces.loginpage.R;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


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
    private String conDuration;

    private DatabaseReference mDatabase;
    public FirebaseAuth mAuth;

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_view);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);

        convoName = (TextView)findViewById(R.id.convo_name_layout);
        date = (TextView)findViewById(R.id.date_layout);

        //get other activity details
        Bundle bundle = getIntent().getExtras();
        conName = bundle.getString("groupName");
        conDate = bundle.getString("date");
        conDescription = bundle.getString("description");
        conTime = bundle.getString("time");
        conDuration = bundle.getString("duration");

        convoName.setText(conName);
        date.setText(conDate);
        description.setText(conDescription);
        time.setText(conTime);
        duration.setText(conDuration);



    }
}
