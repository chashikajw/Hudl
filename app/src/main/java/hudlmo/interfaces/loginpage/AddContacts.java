package hudlmo.interfaces.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import hudlmo.interfaces.VideoMain;
import hudlmo.interfaces.mainmenu.Mainmenu;
import hudlmo.interfaces.registerPage.Register;

public class AddContacts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);


    }

    public void callStart(View v){

        startActivity(new Intent( AddContacts.this,VideoMain.class));
    }



}
