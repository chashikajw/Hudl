package hudlmo.interfaces.contacview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import hudlmo.interfaces.loginpage.R;
import hudlmo.models.Contac;

public class ContacData extends AppCompatActivity {

    EditText editName;
    ImageView contacImage;
    Button save;
    private int mGetImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contac_data);

        editName = (EditText)findViewById(R.id.editnameTxt);
        contacImage = (ImageView)findViewById(R.id.contacImage);
        save = (Button)findViewById(R.id.saveBtn);
    }

    public void SaveButton(){
        Contac contac = new Contac(editName.getText().toString(),mGetImage);

        Intent intent1 = new Intent(ContacData.this,ContacView.class);

        intent1.putExtra("data",contac); //Here we send the Object of Contac class.
        setResult(2,intent1);

        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mGetImage =data.getExtras().getInt("img",1);
        contacImage.setImageResource(mGetImage);

    }
}
