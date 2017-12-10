package hudlmo.interfaces.createMeeting;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.service.chooser.ChooserTarget;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.api.model.StringList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import hudlmo.interfaces.loginpage.R;
import hudlmo.interfaces.loginpage.login;
import hudlmo.models.User;


public class AddParticipants extends AppCompatActivity implements View.OnClickListener {

    Button createButton , contactsButton, selectButton, deleteButton, addEmailButton ;
    String group_name,description_,date_text,time_text;
    int index;
    ListView emailListView , contactsListView1;
    private ArrayList<String> arrayList1;
    private ArrayList<String> arrayList2;
    private ArrayList<String> arrayList3;
    private ArrayList<String> arrayList4;
    //private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;
    private EditText addEmailText;
    String[] checkedList;
    String[] allEmail = {};

    private DatabaseReference mNotification;
    private DatabaseReference usersref;
    private DatabaseReference reqstUser;
    private FirebaseAuth mAuth;
    private DataSnapshot dataSnapshot;
    int count;
    String[] check = new String[8];
    String[] emailList = new String[8];
    int contactLength;

    String[] contacts;
    String[] stringArray;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    EditText inputSearch;

    private ArrayAdapter mAdapter;
    ArrayAdapter<String> itemsAdapter2;
    ArrayAdapter<String> itemsAdapter4;
    FirebaseListAdapter<User> firebaseListAdapter;


    ///@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_add_participants );



        //notification refernce
        mNotification = FirebaseDatabase.getInstance().getReference().child("Notifications");
        mAuth = FirebaseAuth.getInstance();
        reqstUser = FirebaseDatabase.getInstance().getReference().child("UserIndex");
        usersref = FirebaseDatabase.getInstance().getReference().child("Users");


        contactsListView1 = (ListView)findViewById ( R.id.contactsListView1 );
        emailListView = (ListView)findViewById(R.id.emailListView);
        inputSearch = (EditText)findViewById(R.id.inputSearch);
        addEmailText = (EditText) findViewById(R.id.addEmailText) ;

       setContacts();


        //Select Button
        selectButton = (Button) findViewById(R.id.selectButton);
        selectButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setCheckItemsEmailArrray();
            }
        });

        //Add email button
        addEmailButton = (Button)findViewById(R.id.addEmailButton);
        addEmailButton.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View v) {
                String newItem = addEmailText.getText ().toString ();
                arrayList2.add ( newItem );
                itemsAdapter2.notifyDataSetChanged ();
                Toast.makeText(AddParticipants.this,"Add "+newItem,Toast.LENGTH_LONG).show();
            }
        });

        //Delete item from email listview
        emailListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                CharSequence options[] = new CharSequence[]{ "Delete"};

                final AlertDialog.Builder builder = new AlertDialog.Builder(AddParticipants.this);

                builder.setTitle("Delete Email");

                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            //itemsAdapter2.getItemId(position).removeValue();
                            arrayList2.remove(position);
                            //emailListView.removeViewAt(position);
                        }
                    }
                });
                builder.show();
                }
        });

 /*       //Hide Contact and Email lists
        addEmailText.setOnClickListener ( new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                contactsListView1.setVisibility(View.GONE);
                emailListView.setVisibility(View.GONE);
            }


        } );
*/
        //Add Emails to EmailListView
        arrayList2 = new ArrayList<> ( Arrays.asList(allEmail) );
        itemsAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList2);
        emailListView.setAdapter(itemsAdapter2);


        //create Button
        createButton = (Button)findViewById(R.id.createButton);
        createButton.setOnClickListener ( this );

    }

    //set selected items from contact listview to email listview
    public void setCheckItemsEmailArrray(){
        SparseBooleanArray checked = contactsListView1.getCheckedItemPositions();
        int k=0;
        contactLength = check.length;
        //int j=contactsListView1.getAdapter().getCount();
        //Toast.makeText(AddParticipants.this,contactLength,Toast.LENGTH_LONG).show();
        checkedList = new String[contactLength];

        for(int i=0;i<contactLength;i++){
            String item = emailList[i];
            if (checked.get(i)){
                checkedList[k]=item;


                arrayList2.add(item);

                k++;
            }
        }
        itemsAdapter2.notifyDataSetChanged ();



    }

    //Create Contact Listview
    public void setContacts(){

        //get user list from firebase database
        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();

        contactsListView1 = (ListView)findViewById(R.id.contactsListView1);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("contacts");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        firebaseListAdapter = new FirebaseListAdapter<User>(
                this,
                User.class,
                android.R.layout.simple_list_item_multiple_choice,
                databaseReference

        ) {
            @Override
            protected void populateView(View view, User user, int i) {
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setText(user.getUsername());
                check[i]=user.getUsername();
                emailList[i] = user.getEmail();
            }


        };
        contactsListView1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        contactsListView1.setAdapter(firebaseListAdapter);
    }



    public void onClick(View view) {

        //send email

        if (view.getId()==R.id.createButton){


            //get details from Create Meeting
            Bundle bundle = getIntent().getExtras();
            group_name = bundle.getString("group_name");
            description_ = bundle.getString("description_");
            date_text = bundle.getString("date_text");
            time_text = bundle.getString("time_text");


            Intent intent = new Intent( Intent.ACTION_SEND);

            String[] to = arrayList2.toArray(new String[0]);

            intent.putExtra ( Intent.EXTRA_EMAIL,to );
            intent.putExtra ( Intent.EXTRA_SUBJECT,"Meeting Invitation" );
            intent.putExtra ( Intent.EXTRA_TEXT,group_name+"\n"+description_+"\n"+date_text+"\n"+time_text );
            intent.setType ( "message/rfc822" );
            startActivity (Intent.createChooser ( intent,"Send Email" ));


 //send notifications
            final HashMap<String, String> notificationData = new HashMap<>();
            String CurrntUserId = mAuth.getCurrentUser().getUid();
            //calculate unique number
            final String roomId = Integer.toString((int) System.currentTimeMillis());
            notificationData.put("from", CurrntUserId);
            notificationData.put("roomId", roomId);
            //notificationData.put("roomID",roomId);
            notificationData.put("type", "meeting creation");

            String[] sendUser = {"hiru"};


            //store evey participants deatials

            try {

                for (int i = 0; i < sendUser.length; i++) {


                    DatabaseReference reqst_userDB = reqstUser.child(sendUser[i]);


                    reqst_userDB.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.

                            final String reqstUid = dataSnapshot.getValue().toString();
                            usersref.child(reqstUid).child("roomId").setValue(roomId);


                            mNotification.child(reqstUid).push().setValue(notificationData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //usersref.child(reqstUid).child("roomID").setValue(roomId);

                                }
                            });

                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });

                    //set room


                }


            } catch (Exception e) {
                Log.d("myTag", "error");

            }
        }
    }
}