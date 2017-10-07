package hudlmo.interfaces.loginpage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import hudlmo.interfaces.mainmenu.Mainmenu;
import hudlmo.interfaces.registerPage.Register;

public class login extends AppCompatActivity {

    private static EditText emailEt;
    private  static EditText passwordEt;
    private static Button login_btn;
    private static Button register_btn;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailEt =(EditText)findViewById(R.id.emailText);
        passwordEt = (EditText)findViewById(R.id.passwordText);
        login_btn = (Button)findViewById(R.id.loginBtn);
        register_btn = (Button)findViewById(R.id.registerBtn);

        mAuth = FirebaseAuth.getInstance();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //check user is alredy logged
                if(firebaseAuth.getCurrentUser() !=null){

                    startActivity(new Intent(login.this,Mainmenu.class));

                }
            }
        };


    }

    @Override
    protected  void onStart(){
        super.onStart();

        mAuth.addAuthStateListener(mAuthListner);
    }

    private void startSignIn(){

        String email =  emailEt.getText().toString();
        String password = passwordEt.getText().toString();

        //check fields are blank or not
        if(TextUtils.isEmpty(email)){
            Toast.makeText(login.this, "Email field is empty",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(login.this, "Password fields is empty",Toast.LENGTH_LONG).show();
        }

        else{
            //sign in with email and password
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(login.this, "Sign in Problem",Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
    }

    public void RegisterButton(View v){
        emailEt =(EditText)findViewById(R.id.emailText);
        passwordEt = (EditText)findViewById(R.id.passwordText);
        login_btn = (Button)findViewById(R.id.loginBtn);
        register_btn = (Button)findViewById(R.id.registerBtn);
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void LoginButton(View v){
        startSignIn();

    }
}
