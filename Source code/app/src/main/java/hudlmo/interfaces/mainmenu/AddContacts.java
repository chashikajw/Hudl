package hudlmo.interfaces.mainmenu;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hudlmo.interfaces.loginpage.R;


public class AddContacts extends AppCompatActivity {

    private static EditText emailEt;
    private  static EditText usernameEt;
    private Toolbar mToolbar;

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseContact;


    public FirebaseAuth mAuth;

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_contact);

        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // ActionBar bar = getActionBar();
       // bar.setBackgroundDrawable(new ColorDrawable(0x008dc7));


        emailEt =(EditText)findViewById(R.id.emailText);
        usernameEt = (EditText)findViewById(R.id.usernameText);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);


    }


    public void saveContact(View v){
        try{
            //mProgress.setMessage("saving....");
            //mProgress.show();

            final String email = emailEt.getText().toString().trim();
            String username = usernameEt.getText().toString().trim();

            String userId = mAuth.getCurrentUser().getUid();

 /*           mDatabaseContact = FirebaseDatabase.getInstance().getReference().child("UniqueID");
            mDatabaseContact.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(email)) {//I need the verification here.
                        Toast.makeText(AddContacts.this, "Installed",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(AddContacts.this, "Not Installed",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
*/


            //creste unique number
            String contacID = Integer.toString((int)System.currentTimeMillis());
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("contacts").child(contacID);
            mDatabase.child("username").setValue(username);
            mDatabase.child("email").setValue(email);

            //mProgres
            mProgress.setMessage("Saving...");
            mProgress.show();


        }catch(Exception e){
            Toast.makeText(AddContacts.this, "connction error",Toast.LENGTH_LONG).show();
        }

    }



}
