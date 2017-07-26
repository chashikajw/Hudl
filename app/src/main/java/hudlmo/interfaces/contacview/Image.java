package hudlmo.interfaces.contacview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import hudlmo.interfaces.loginpage.R;
import hudlmo.models.AdapterImages;

public class Image extends AppCompatActivity {

    private int [] mImages={R.drawable.img1,R.drawable.img2,R.drawable.img3,R.drawable.img4,
            R.drawable.img5,R.drawable.img6,R.drawable.img7,R.drawable.img1,R.drawable.img2,R.drawable.img3,R.drawable.img4,
            R.drawable.img5,R.drawable.img6,R.drawable.img7,R.drawable.img1,R.drawable.img2,R.drawable.img3,R.drawable.img4,
            R.drawable.img5,R.drawable.img6,R.drawable.img7

    };

    AdapterImages adapterImages;
    GridView gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        gridView=(GridView)findViewById(R.id.gridview);

        adapterImages=new AdapterImages(mImages,this);

        gridView.setAdapter(adapterImages);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Image.this, position + "", Toast.LENGTH_SHORT).show();

                Intent intent3 = new Intent();
                intent3.putExtra("img",mImages[position]);
                setResult(1,intent3);
                finish();
            }
        });
    }
}
