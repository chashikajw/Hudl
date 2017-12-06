package hudlmo.interfaces.History;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import hudlmo.interfaces.loginpage.R;

/**
 * Created by Shalini PC on 12/6/2017.
 */

public class HistoryView extends AppCompatActivity {
    TextView convoName;
    TextView description;
    TextView date;
    TextView time;
    TextView duration;

    ScrollView mainScrollView;
    HistoryClass history;

    // *********** Firebase Database Connection *********** //

    FirebaseDatabase firebase = FirebaseDatabase.getInstance();
    DatabaseReference db_ref = firebase.getReference().child("Users");

    //*****************************************************//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_main);

        mainScrollView = (ScrollView) findViewById(R.id.scrollviewInfo);

        convoName = (TextView) findViewById(R.id.convoName);
        description = (TextView) findViewById(R.id.description);
        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);
        duration = (TextView) findViewById(R.id.duration);

        int s = getIntent().getIntExtra("selectedJob", 0);

        history = ConvoHistoryListClass.convoHistory.get(s);

        convoName.setText(history.getConvoName());
        description.setText(history.getDescription());
        date.setText(history.getDate());
        time.setText(history.getStartTime());
        duration.setText(history.duration);
    }
}
