package hudlmo.interfaces.loginpage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import hudlmo.interfaces.registerPage.Register;

public class login extends AppCompatActivity {

    private static EditText username;
    private  static EditText password;
    private static Button login_btn;
    private static Button register_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void RegisterButton(View v){

        username =(EditText)findViewById(R.id.usernameText);
        password = (EditText)findViewById(R.id.passwordText);
        login_btn = (Button)findViewById(R.id.loginBtn);
        register_btn = (Button)findViewById(R.id.registerBtn);

        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}
