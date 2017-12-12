package hudlmo.interfaces.loginpage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Settings extends AppCompatActivity {

    private ListView mlistview;
    private FirebaseAuth mAuth;
    private ImageButton mProfileImage;
    private EditText mProfileName;
    private Button mProfileStrBtn;
    private DatabaseReference mUsersDatabase;
    private static final int GALLERY_REQUEST = 1;
    private Uri mImageUri = null;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;
    private Toolbar mToolbar;
    private Firebase mRootRef;
    private TextView mNameView;
    private DatabaseReference mDatabase1;
    private ImageView mImageView;
    private DatabaseReference mDatabase2;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //toolbar
        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("User Profile");




        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("username");
        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("image");


        mImageView = (ImageView) findViewById(R.id.imageview);
        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String image1 = dataSnapshot.getValue().toString();
                Picasso.with(Settings.this).load(image1).into(mImageView);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mNameView = (TextView) findViewById(R.id.profile_name);
        mDatabase1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name1 = dataSnapshot.getValue().toString();
                mNameView.setText(name1);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        mProfileImage = (ImageButton) findViewById(R.id.profile_image);
        mProfileName =(EditText) findViewById(R.id.profile_displayName);
        mProfileStrBtn=(Button) findViewById(R.id.store_btn);
        mProgress = new ProgressDialog(this);

        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gallery =new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery, GALLERY_REQUEST);


            }
        });

        mProfileStrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startPosting();

            }
        });


        // DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("contacts");
        // mlistview = (ListView)findViewById(R.id.grouplist2);


        // FirebaseListAdapter<String> firebaseListAdapter = new FirebaseListAdapter<String>(
        // this,
        // String.class,
        // android.R.layout.simple_list_item_1,
        // mDatabase
        /// ) {
        // @Override
        //protected void populateView(View view, String model, int i) {

        // TextView textview = (TextView)view.findViewById(android.R.id.text1);
        // textview.setText(model);

    }
    // };
    // mlistview.setAdapter(firebaseListAdapter);


    // }

    private void startPosting() {

        mProgress.setMessage("uploaded");
        mProgress.show();

        final String title_val = mProfileName.getText().toString().trim();

        if(!TextUtils.isEmpty(title_val) && mImageUri != null){

            StorageReference filepath = mStorage.child("Hudl_Profiles").child(mImageUri.getLastPathSegment());

            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    mAuth = FirebaseAuth.getInstance();
                    String userId = mAuth.getCurrentUser().getUid();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                    DatabaseReference mData = mDatabase;
                    mDatabase.child("image").setValue(downloadUrl.toString());
                    mDatabase.child("username").setValue(title_val);
                    // Map<String, String> userData = new HashMap<String, String>();


                    // String value = mProfileName.getText().toString();
                    //Firebase childref = mRootRef.child("Users").child(userId).child("username");
                    //childref.setValue(value);




                    // DatabaseReference newPost = mDatabase.push();
                    // newPost.child("username").setValue(title_val);
                    // newPost.child("image").setValue(downloadUrl.toString());
                    mProgress.dismiss();

                }
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {

            mImageUri = data.getData();
            mProfileImage.setImageURI(mImageUri);
        }
    }
}
