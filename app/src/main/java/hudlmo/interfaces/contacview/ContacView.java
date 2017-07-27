package hudlmo.interfaces.contacview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import hudlmo.interfaces.loginpage.R;
import hudlmo.models.Contac;
import hudlmo.models.ContacDetails;
import hudlmo.models.ContactsAdapter;

public class ContacView extends AppCompatActivity {

    Button  contactAddButton;
    ListView listContacts;

    ArrayList<Contac> arrayListContac;
    ContactsAdapter contactsAdapter;
    Contac contacts;
    final int C_View=1,C_Delete=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contac_view);

        arrayListContac = new ArrayList<Contac>();
        listContacts = (ListView)findViewById(R.id.listView);
        contactAddButton = (Button)findViewById(R.id.contacAddBtn);

        contactAddButton.setOnClickListener(new View.OnClickListener(){
            public  void onClick(View v){
                Intent intent = new Intent(ContacView.this,ContacData.class);
                startActivityForResult(intent,1);
            }
        });

        contactsAdapter=new ContactsAdapter(ContacView.this,arrayListContac);

        listContacts.setAdapter(contactsAdapter);

       listContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //registerForContextMenu(listContacts);

            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.listView) {
            menu.add(0, C_View, 1, "View");
            menu.add(0, C_Delete, 2, "Delete");

        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {


        switch (item.getItemId())
        {
            case C_View:

                Intent intent6=new Intent(ContacView.this,ContacDetails.class);
                AdapterView.AdapterContextMenuInfo info1 = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                int index1 = info1.position;

                intent6.putExtra("details", arrayListContac.get(index1));

                startActivity(intent6);

                break;

            case C_Delete:
                Toast.makeText(ContacView.this,"Delete",Toast.LENGTH_SHORT).show();

                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                int index = info.position;

                Log.e("index",index+" ");
                arrayListContac.remove(index);
                contactsAdapter.notifyDataSetChanged();

                break;

        }
        return  true;


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode==2) {

            contacts = (Contac) data.getSerializableExtra("data");

            arrayListContac.add(contacts);
            contactsAdapter.notifyDataSetChanged();
        }


    }
}

