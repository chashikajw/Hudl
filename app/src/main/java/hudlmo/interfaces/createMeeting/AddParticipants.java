package hudlmo.interfaces.createMeeting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import hudlmo.interfaces.loginpage.R;


public class AddParticipants extends AppCompatActivity implements View.OnClickListener {

    Button createButton , contactsButton ;
    int index;
    ListView emailListView , contactsListView1;
    private ArrayList<String> arrayList;
    private ArrayList<String> arrayList2;
    private ArrayAdapter<String> adapter;
    private EditText addEmailText;
    private DatabaseReference mNotification;
    private DatabaseReference reqstUser;
    private FirebaseAuth mAuth;

    ///@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_add_participants );

        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();

        contactsListView1 = (ListView)findViewById(R.id.contactsListView1);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("contacts");



        FirebaseListAdapter<String> firebaseListAdapter = new FirebaseListAdapter<String>(
                this,
                String.class,
                android.R.layout.simple_list_item_multiple_choice,
                databaseReference


        ) {
            @Override
            protected void populateView(View view, String s, int i) {
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setText(s);
            }
        };
        contactsListView1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        contactsListView1.setAdapter(firebaseListAdapter);

/*
        //Contact ListView
        contactsListView1 = (ListView)findViewById ( R.id.contactsListView1 );
        final String[] contacts = {"Sammani","Chashika","Piyumi","Aravind","Shalini","Prabhath"};
        final String[] contactsMails = {"Sammanianu123@gmail.com","chashikajw007@gmail.com","piyumisenevirathne@gmail.com","aravinth9611991@gmail.com","nimeshika94@gmail.com","DRAPIROSHAN@gmail.com"};
        arrayList2 = new ArrayList<> ( Arrays.asList(contacts) );
        adapter = new ArrayAdapter<String> ( this,android.R.layout.simple_list_item_1 ,arrayList2);
        contactsListView1.setAdapter(adapter); //same adapter?

        // ListView Buttons
        final ListView contactsListView1 = (ListView)findViewById ( R.id.contactsListView1 );
        contactsListView1.setOnItemClickListener ( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                String message = textView.getText ().toString ();
                for (int i=0;i<contacts.length;i++){
                    if (message.equals ( contacts[i] )){
                        index = i;
                    }
                }

                addEmailText.setText ( contactsMails[index] );
            }
        } );

        //add emails to emailListView
        emailListView = (ListView)findViewById ( R.id.emailListView );
        String[] items = {};
        arrayList = new ArrayList<> ( Arrays.asList(items) );
        adapter = new ArrayAdapter<String> ( this,android.R.layout.simple_list_item_1 ,arrayList);////
        emailListView.setAdapter(adapter);

        // addEmailButton Button
        addEmailText = (EditText)findViewById ( R.id.addEmailText );
        Button addEmailButton = (Button)findViewById ( R.id.addEmailButton );
        addEmailButton.setOnClickListener ( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newItem = addEmailText.getText ().toString ();
                arrayList.add ( newItem );
                adapter.notifyDataSetChanged ();
                Toast.makeText (getBaseContext (),"Email Added",Toast.LENGTH_SHORT).show ();
                addEmailText.setText ( " " );
            }
        } );

        //Contacts Button
        Button contactsButton = (Button)findViewById ( R.id.contactsButton );
        contactsButton.setOnClickListener ( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactsListView1.setVisibility(View.VISIBLE);
            }
        } );

        //Hide ListView when click addEmailText
        TextView addEmailText = (TextView)findViewById ( R.id.addEmailText );
        addEmailText.setOnClickListener ( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactsListView1.setVisibility(View.GONE);
            }
        } );

*/
        createButton = (Button)findViewById(R.id.createButton);
        createButton.setOnClickListener ( this );


        //notification refernce
        mNotification = FirebaseDatabase.getInstance().getReference().child("Notifications");
        mAuth = FirebaseAuth.getInstance();
        reqstUser = FirebaseDatabase.getInstance().getReference().child("UserIndex");

    }

    public void onClick(View view) {

        //send email
        if (view.getId()==R.id.createButton){
            Intent intent = new Intent( Intent.ACTION_SEND);
           // intent.setData ( Uri.parse ("mailto:") );
            //String[] to = {"sammanianu123@gmail.com","sammanianu12@gmail.com"};
            //ListView lv = (ListView)findViewById ( R.id.emailListView );
            //String[] to = (String[]) listEmail.toArray ();

           // String[] to = arrayList.toArray(new String[0]);

           // intent.putExtra ( Intent.EXTRA_EMAIL,to );
            intent.putExtra ( Intent.EXTRA_SUBJECT,"Meeting Invitation" );
            intent.putExtra ( Intent.EXTRA_TEXT,"click this link" );
            intent.setType ( "message/rfc822" );
            startActivity (Intent.createChooser ( intent,"Send Email" ));


            //send notifications
            final HashMap<String,String> notificationData = new HashMap<>();
            String CurrntUserId = mAuth.getCurrentUser().getUid();
            //calculate unique number
            String roomId = Integer.toString((int)System.currentTimeMillis());
            notificationData.put("from",CurrntUserId);
            notificationData.put("roomID",roomId);
            notificationData.put("type","meeting creation");

            String[] sendUser = {"sam","mashi","","hiru007"};



            //store evey participants deatials

            try {

                for(int i=0; i< sendUser.length;i++ ){


                    DatabaseReference reqst_userDB = reqstUser.child(sendUser[i]);




                    reqst_userDB.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.

                            String reqstUid= dataSnapshot.getValue().toString();



                            mNotification.child(reqstUid).push().setValue(notificationData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            });

                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });

                    //set room


                }


            }catch (Exception e){
                Log.d("myTag", "error");

            }














        }
    }
}
