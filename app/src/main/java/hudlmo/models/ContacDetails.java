package hudlmo.models;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import hudlmo.interfaces.loginpage.R;

public class ContacDetails extends AppCompatActivity {

    Contac contacdetails;
    private String contacName,contacEmail;
    private int contacImage;
    TextView detailsName,detailEmail;
    ImageView detailImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contac_details);

        detailImage = (ImageView)findViewById(R.id.details_image);
        detailsName = (TextView)findViewById(R.id.details_name);

        Intent intent9=new Intent();
        intent9 = getIntent();
        contacdetails = (Contac) intent9.getSerializableExtra("details");

        contacImage = contacdetails.getImageID();
        contacName = contacdetails.getName();

        detailsName.setText(contacName);
        detailImage.setImageResource(contacImage);
    }
}
