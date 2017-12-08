package hudlmo.interfaces.loginpage;

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

public class ProfileView extends AppCompatActivity {

    private static TextView emailEt;
    private  static TextView usernameEt;
    private String mname;
    private String musername;
    private String memail;

    private DatabaseReference mDatabase;
    public FirebaseAuth mAuth;

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);

        emailEt = (TextView)findViewById(R.id.emaltxt);
        usernameEt = (TextView)findViewById(R.id.usernametxt);

        //get other activity details
        Bundle bundle = getIntent().getExtras();
        mname = bundle.getString("name");
        musername = bundle.getString("username");
        memail = bundle.getString("email");

        emailEt.setText(memail);
        usernameEt.setText(musername);




    }


    public void ContactSave(View v){
        try{
            mProgress.setMessage("saving....");
            mProgress.show();
            String email = emailEt.getText().toString().trim();
            String username = usernameEt.getText().toString().trim();

            String userId = mAuth.getCurrentUser().getUid();

            //creste unique number
            String contacID = Integer.toString((int)System.currentTimeMillis());
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("contacts").child(contacID);
            mDatabase.child("username").setValue( musername);
            mDatabase.child("email").setValue(memail);

            mProgress.dismiss();
        }catch(Exception e){
            Toast.makeText(ProfileView.this, "connction error",Toast.LENGTH_LONG).show();
        }

    }
}
