package hudlmo.interfaces.loginpage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import hudlmo.interfaces.mainmenu.Mainmenu;
import hudlmo.interfaces.registerPage.Register;

public class login extends AppCompatActivity {

    private static EditText usernameEt;
    private  static EditText passwordEt;
    private static Button login_btn;
    private static Button register_btn;

    public login(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void RegisterButton(View v){
        usernameEt =(EditText)findViewById(R.id.usernameText);
        passwordEt = (EditText)findViewById(R.id.passwordText);
        login_btn = (Button)findViewById(R.id.loginBtn);
        register_btn = (Button)findViewById(R.id.registerBtn);
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void LoginButton(View v){
        usernameEt =(EditText)findViewById(R.id.usernameText);
        passwordEt = (EditText)findViewById(R.id.passwordText);
        login_btn = (Button)findViewById(R.id.loginBtn);
        register_btn = (Button)findViewById(R.id.registerBtn);
        String username =  usernameEt.getText().toString();
        String password = passwordEt.getText().toString();
        String type = "login";

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type,username, password );
        Intent intent = new Intent(this, Mainmenu.class);
        startActivity(intent);
    }
}
