package hudlmo.interfaces.createMeeting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import hudlmo.interfaces.loginpage.R;

public class AddParticipants extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_add_participants );
    }
}
