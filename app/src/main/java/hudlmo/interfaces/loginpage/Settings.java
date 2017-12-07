package hudlmo.interfaces.loginpage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
    private ProgressDialog mProgress;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();

        mStorage = FirebaseStorage.getInstance().getReference();

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

    private void startPosting() {

        mProgress.setMessage("uploaded");
        mProgress.show();

        String title_val = mProfileName.getText().toString().trim();

        if(!TextUtils.isEmpty(title_val) && mImageUri != null){

            StorageReference filepath = mStorage.child("Hudl_Profiles").child(mImageUri.getLastPathSegment());

            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUri = taskSnapshot.getDownloadUrl();
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
