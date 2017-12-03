package hudlmo.interfaces.loginpage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Settings extends AppCompatActivity {

    private ListView mlistview;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("contacts");
        mlistview = (ListView)findViewById(R.id.grouplist2);


        FirebaseListAdapter<String> firebaseListAdapter = new FirebaseListAdapter<String>(
                this,
                String.class,
                android.R.layout.simple_list_item_1,
                mDatabase
        ) {
            @Override
            protected void populateView(View view, String model, int i) {

                TextView textview = (TextView)view.findViewById(android.R.id.text1);
                textview.setText(model);

            }
        };
        mlistview.setAdapter(firebaseListAdapter);

    }
}
