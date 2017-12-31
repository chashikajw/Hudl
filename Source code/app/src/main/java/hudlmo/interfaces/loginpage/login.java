package hudlmo.interfaces.loginpage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import hudlmo.interfaces.mainmenu.Mainmenu;
import hudlmo.interfaces.registerPage.Register;

public class login extends AppCompatActivity implements View.OnClickListener{

    private Button login_btn;
    private Button register_btn;
    private EditText passwordEt;
    private EditText emailEt;







    private  FirebaseAuth mAuth;
    //private FirebaseAuth.AuthStateListener mAuthListner;
    private ProgressDialog mProgress;
    //private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialising firebase auth object
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null)
        {
            //start profile activity  here
            finish();
            startActivity(new Intent(getApplicationContext(),Mainmenu.class));
        }


        emailEt =(EditText)findViewById(R.id.emailText);
        passwordEt = (EditText)findViewById(R.id.passwordText);
        login_btn = (Button)findViewById(R.id.loginBtn);
        register_btn = (Button)findViewById(R.id.registerBtn);

        mProgress = new ProgressDialog(this);




        //attaching listners to buttons
        register_btn.setOnClickListener(this);
        login_btn.setOnClickListener(this);



    }
    private void userLogin()
    {
        String email =  emailEt.getText().toString();
        String password = passwordEt.getText().toString();

        //both email & password fields empty
        if(TextUtils.isEmpty(email)&& TextUtils.isEmpty(password)){
            Toast.makeText(login.this, "Please enter email & Password",Toast.LENGTH_LONG).show();
            //stopping the function execution further
            return;
        }

        //if email is empty
        else if(TextUtils.isEmpty(email)) {
            Toast.makeText(login.this, "Please enter your email", Toast.LENGTH_LONG).show();
            //stopping the function execution further
            return;
        }
        //if password is empty
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(login.this, "Please enter password",Toast.LENGTH_LONG).show();
            //stop the function execution further
            return;
        }

        //if field validations are ok progress dialog will be shown
        mProgress.setMessage("Login...");
        mProgress.show();

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mProgress.dismiss();
                        if(task.isSuccessful())
                        {
                            //start main activity
                            finish();
                            startActivity(new Intent(getApplicationContext(),Mainmenu.class));

                        }
                        if(!task.isSuccessful()){

                            Toast.makeText(login.this, "Login Problem! Please retry.",Toast.LENGTH_LONG).show();
                        }


                    }
                });


    }



    @Override
    public void onClick(View v) {
        if (v== login_btn)
        {
            userLogin();
        }
        if(v==register_btn)
        {
            finish();
            startActivity(new Intent(this, Register.class));
        }

    }
}
