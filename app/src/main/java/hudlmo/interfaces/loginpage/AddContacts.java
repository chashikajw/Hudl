package hudlmo.interfaces.loginpage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class AddContacts extends AppCompatActivity {

    private static EditText emailEt;
    private  static EditText usernameEt;
    private Toolbar mToolbar;

    private DatabaseReference mDatabase;
    public FirebaseAuth mAuth;

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_contact);

        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        emailEt =(EditText)findViewById(R.id.emailText);
        usernameEt = (EditText)findViewById(R.id.usernameText);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);

       /* //set up toolbar
        mtoolbar = (Toolbar)findViewById(R.id.contacttoolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Save Groups");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); */





    }


    public void saveContact(View v){
        try{
            mProgress.setMessage("saving....");
            mProgress.show();
            String email = emailEt.getText().toString().trim();
            String username = usernameEt.getText().toString().trim();

            String userId = mAuth.getCurrentUser().getUid();

            //creste unique number
            String contacID = Integer.toString((int)System.currentTimeMillis());
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("contacts").child(contacID);
            mDatabase.child("username").setValue(username);
            mDatabase.child("email").setValue(email);

            mProgress.dismiss();
        }catch(Exception e){
            Toast.makeText(AddContacts.this, "connction error",Toast.LENGTH_LONG).show();
        }

    }



}
