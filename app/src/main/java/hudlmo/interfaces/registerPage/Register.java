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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hudlmo.interfaces.loginpage.R;
import hudlmo.interfaces.loginpage.login;
import hudlmo.interfaces.mainmenu.Mainmenu;

/**
 * Created by chashika on 10/5/17.
 */

public class Register extends AppCompatActivity {

    private static EditText nameEt;
    private static EditText usernameEt;
    private static EditText emailEt;
    private  static EditText passwordEt;
    private  static EditText confirm_passwordEt;
    private static Button register_btn;

    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameEt =(EditText)findViewById(R.id.nameTxt);
        usernameEt =(EditText)findViewById(R.id.usernamrTxt);
        emailEt =(EditText)findViewById(R.id.emailTxt);
        passwordEt = (EditText)findViewById(R.id.passwordTxt);
        confirm_passwordEt = (EditText)findViewById(R.id.confirmPasswordTxt);

        register_btn = (Button)findViewById(R.id.registerBtn);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mProgress = new ProgressDialog(this);

    }

    public void registerUser(View v){
        System.out.print("yep");
        StartRegister();

    }

    //validate register form and insert data to the database
    public void StartRegister(){
        String email = emailEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();
        String confirm_password = confirm_passwordEt.getText().toString().trim();
        final String username = usernameEt.getText().toString().trim();
        final String name = nameEt.getText().toString().trim();

        if(TextUtils.isEmpty(email)||TextUtils.isEmpty(password)||TextUtils.isEmpty(confirm_password)
            || TextUtils.isEmpty(username)||TextUtils.isEmpty(name)){
            Toast.makeText(Register.this, "Fields are empty",Toast.LENGTH_LONG).show();
        }
        else if(password.length() <4){
            Toast.makeText( Register.this, "Password should be at least 5 charcters",Toast.LENGTH_LONG).show();
        }
        else if(!password.equals(confirm_password)){
            Toast.makeText( Register.this, "Password is not matching",Toast.LENGTH_LONG).show();
        }

        //insert data to the database
        else {


            mProgress.setMessage("Signing up....");
            mProgress.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        String userId = mAuth.getCurrentUser().getUid();

                        DatabaseReference currnt_userDB = mDatabase.child(userId);
                        currnt_userDB.child("name").setValue(name);
                        currnt_userDB.child("username").setValue(username);
                        currnt_userDB.child("image").setValue("default");

                        mProgress.dismiss();
                        startActivity(new Intent(Register.this, Mainmenu.class));
                    } else {
                        mProgress.dismiss();
                        Toast.makeText(Register.this, "Sign up Problem", Toast.LENGTH_LONG).show();
                    }
                }

            });

        }
    }


}
