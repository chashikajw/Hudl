package hudlmo.interfaces.registerPage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import hudlmo.interfaces.loginpage.R;
import hudlmo.interfaces.loginpage.login;
import hudlmo.interfaces.mainmenu.Mainmenu;

/**
 * Created by chashika on 10/5/17.
 */

public class Register extends AppCompatActivity implements View.OnClickListener {

    private Button registerBtn;
    private EditText usernameTxt;
    private EditText nameTxt;
    private EditText passwordTxt;
    private EditText confirmPasswordTxt;
    private EditText emailTxt;



    private ProgressDialog mProgress;

    //defining firebase object
    private FirebaseAuth mAuth;
    //defining db
    private DatabaseReference mDatabase;

    private DatabaseReference mDatabaseindex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initializing Firebase object
        mAuth = FirebaseAuth.getInstance();

        //if user already logged in start profile activity here
        if (mAuth.getCurrentUser() != null) {
            //start profile activity  here
            finish();
            startActivity(new Intent(getApplicationContext(), Mainmenu.class));

        }


        mProgress = new ProgressDialog(this);

        //get the firebase database reference
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseindex = FirebaseDatabase.getInstance().getReference().child("UserIndex");


        registerBtn = (Button) findViewById(R.id.registerBtn);

        nameTxt= (EditText) findViewById(R.id.nameTxt);
        usernameTxt= (EditText) findViewById(R.id.usernameTxt);
        emailTxt = (EditText) findViewById(R.id.emailTxt);
        passwordTxt = (EditText) findViewById(R.id.passwordTxt);
        confirmPasswordTxt = (EditText) findViewById(R.id.confirmPasswordTxt);

        //attaching listner to rehister Button
        registerBtn.setOnClickListener(this);
    }


    private void registerUser(){
        String name = nameTxt.getText().toString().trim();
        final String uname = usernameTxt.getText().toString().trim();
        String email = emailTxt.getText().toString().trim();
        String password = passwordTxt.getText().toString().trim();
        String confirm_pw = confirmPasswordTxt.getText().toString().trim();

        final UserDetails newUser = new UserDetails(name,uname,email,password,confirm_pw);


        if (TextUtils.isEmpty(newUser.getName())){
            //password is empty
            Toast.makeText(this, "Please enter Name", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;

        }
        if (TextUtils.isEmpty(newUser.getUname())){
            //password is empty
            Toast.makeText(this, "Please enter User Name", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;

        }

        if(TextUtils.isEmpty(newUser.getEmail())){
            //email is empty
            Toast.makeText(this, "Please enter email",Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;


        }

        if (TextUtils.isEmpty(newUser.getPassword())){
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;

        }
        if(newUser.getPassword().length() <5)
        {
            //if password too short
            Toast.makeText(Register.this, "Password should be at least 5 characters", Toast.LENGTH_LONG).show();
            //stopping the function execution further
            return;
        }

        if (TextUtils.isEmpty(newUser.getComfirm_pw())){
            //confirm password is empty
            Toast.makeText(this, "Please confirm password", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;

        }


        if (!newUser.getPassword().equals(newUser.getComfirm_pw())){
            //passwords mismatch
            Toast.makeText(this, "Passwords does not match", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;

        }


        //if validations are ok a progress bar will be shown
        mProgress.setMessage("Registering User...");
        mProgress.show();

        //creating a new user
        mAuth.createUserWithEmailAndPassword(newUser.getEmail(),newUser.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //user is successfully registered and logged in

                            //EmailVerification();

                            FirebaseUser user = mAuth.getCurrentUser();
                            DatabaseReference currnt_userDB = mDatabase.child(user.getUid());
                            currnt_userDB.child("name").setValue(newUser.getName());
                            currnt_userDB.child("username").setValue(newUser.getUname());
                            currnt_userDB.child("email").setValue(newUser.getEmail());
                            currnt_userDB.child("meetings").setValue("default");
                            currnt_userDB.child("image").setValue("default");



                            String userId = mAuth.getCurrentUser().getUid();

                            mDatabaseindex.child(newUser.getUname()).setValue(userId);


                            //to notifications
                            String deviceToken = FirebaseInstanceId.getInstance().getToken();
                            currnt_userDB.child("device_token").setValue(deviceToken);

                            finish();

                            startActivity(new Intent(getApplicationContext(),Mainmenu.class));
                        }

                        else
                        {
                            Toast.makeText(Register.this,"Registration Failed! Please try again.",Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }

    //when a newly registered user have to confirm their email address using the verification email
    //this code implements to send that verification email.
    //once the user click that link their email address is verified.
    private void EmailVerification() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Register.this,"Check your Email for Verification", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                    }
                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        if (v == registerBtn)
        {
            registerUser();
        }

    }
}
