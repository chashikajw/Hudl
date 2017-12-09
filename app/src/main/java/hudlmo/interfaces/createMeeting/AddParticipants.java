package hudlmo.interfaces.createMeeting;

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
import com.google.firebase.auth.api.model.StringList;
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
<<<<<<< HEAD
import hudlmo.interfaces.loginpage.login;
=======

import hudlmo.interfaces.loginpage.login;

>>>>>>> 5894b1362806a9b92c51606fb4d0870a816c6569
import hudlmo.models.User;


public class AddParticipants extends AppCompatActivity implements View.OnClickListener {

<<<<<<< HEAD
    Button createButton , contactsButton, selectButton, deleteButton, addEmailButton ;
    String group_name,description_,date_text,time_text;
=======
    Button createButton, contactsButton, selectButton, deleteButton;
>>>>>>> 5894b1362806a9b92c51606fb4d0870a816c6569
    int index;
    ListView emailListView, contactsListView1;
    private ArrayList<String> arrayList1;
    private ArrayList<String> arrayList2;
    private ArrayList<String> arrayList3;
    private ArrayList<String> arrayList4;
    //private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;
    private EditText addEmailText;
    //private String[] lv_arr,checkedList = {};

    private DatabaseReference mNotification;
    private DatabaseReference reqstUser;
    private FirebaseAuth mAuth;
    private DataSnapshot dataSnapshot;
<<<<<<< HEAD
    int count;
    String[] check = new String[8];
    String[] emailList = new String[8];
    int contactLength;
=======
    private DatabaseReference usersref;

    String[] check = new String[6];
>>>>>>> 5894b1362806a9b92c51606fb4d0870a816c6569

    String[] contacts;
    String[] stringArray;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    EditText inputSearch;

    private ArrayAdapter mAdapter;
    ArrayAdapter<String> itemsAdapter2;
    ArrayAdapter<String> itemsAdapter4;


    ///@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participants);

<<<<<<< HEAD
        contactsListView1 = (ListView)findViewById ( R.id.contactsListView1 );
        emailListView = (ListView)findViewById(R.id.emailListView);
        inputSearch = (EditText)findViewById(R.id.inputSearch);
        addEmailText = (EditText) findViewById(R.id.addEmailText) ;

        setContacts();

        //Select Button
        selectButton = (Button)findViewById(R.id.selectButton);
=======
        contactsListView1 = (ListView) findViewById(R.id.contactsListView1);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        //initList();
        setContacts();

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals(" ")) {
                    //initList();
                    setContacts();
                } else {
                    searchItem(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //setContacts();
        //combineMethod();
        //setCheckItemsEmailArrray();
        //filter();


        Button selectButton = (Button) findViewById(R.id.selectButton);
>>>>>>> 5894b1362806a9b92c51606fb4d0870a816c6569
        selectButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setCheckItemsEmailArrray();
            }
        });

<<<<<<< HEAD
        //Add email button
        addEmailButton = (Button)findViewById(R.id.addEmailButton);
=======
        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setDelete();
            }
        });


        // addEmailButton Button
 /*       addEmailText = (EditText)findViewById ( R.id.addEmailText );
        Button addEmailButton = (Button)findViewById ( R.id.addEmailButton );
>>>>>>> 5894b1362806a9b92c51606fb4d0870a816c6569
        addEmailButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //addEmailText.setText(count);
                //Toast.makeText(AddParticipants.this,count,Toast.LENGTH_LONG).show();
            }
        });


<<<<<<< HEAD
        //create Button
        createButton = (Button)findViewById(R.id.createButton);
        createButton.setOnClickListener ( this );

    }

    public void setCheckItemsEmailArrray(){
        SparseBooleanArray checked = contactsListView1.getCheckedItemPositions();
        int k=0;
        contactLength = check.length;
        //int j=contactsListView1.getAdapter().getCount();
        //Toast.makeText(AddParticipants.this,contactLength,Toast.LENGTH_LONG).show();
        String checkedList[] = new String[contactLength];

        for(int i=0;i<contactLength;i++){
            String item = emailList[i];
            if (checked.get(i)){
                checkedList[k]=item;
=======
        //Hide ListView when click addEmailText
        TextView addEmailText = (TextView)findViewById ( R.id.addEmailText );
        addEmailText.setOnClickListener ( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactsListView1.setVisibility(View.GONE);
            }
        } );

*/
        createButton = (Button) findViewById(R.id.createButton);
        createButton.setOnClickListener(this);



        //notification refernce
        mNotification = FirebaseDatabase.getInstance().getReference().child("Notifications");
        mAuth = FirebaseAuth.getInstance();
        reqstUser = FirebaseDatabase.getInstance().getReference().child("UserIndex");
        usersref = FirebaseDatabase.getInstance().getReference().child("Users");

    }

    public void searchItem(String textToSearch) {
        for (String item : contacts) {
            if (!item.contains(textToSearch)) {
                arrayList2.remove(item);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void initList() {
/*
        contactsListView1 = (ListView)findViewById ( R.id.contactsListView1 );
        contacts = new String[] {"Sammani","Chashika","Piyumi","Aravind","Shalini","Prabhath"};
        arrayList2 = new ArrayList<> ( Arrays.asList(contacts) );
        adapter = new ArrayAdapter<String> ( this,android.R.layout.simple_list_item_multiple_choice ,arrayList2);
        contactsListView1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        contactsListView1.setAdapter(adapter); //same adapter?
*/

        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        contactsListView1 = (ListView) findViewById(R.id.contactsListView1);
        //int j;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("contacts");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long size = dataSnapshot.getChildrenCount();
                int j = (int) size;
                String contact[] = new String[j];

                for (int i = 0; i < j; i++) {
                    String item = (String) (contactsListView1.getItemAtPosition(i));
                    System.out.println(item);
                    contact[i] = item;

                }
                contactsListView1 = (ListView) findViewById(R.id.contactsListView1);
                arrayList1 = new ArrayList<>(Arrays.asList(contact));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //int j=databaseReference.getAdapter().getCount();


        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, arrayList1);
        contactsListView1.setAdapter(itemsAdapter);

    }

    public void setCheckItemsEmailArrray() {
        SparseBooleanArray checked = contactsListView1.getCheckedItemPositions();
        int k = 0;
        int j = contactsListView1.getAdapter().getCount();
        String checkedList[] = new String[j];

        for (int i = 0; i < j; i++) {
            String item = (String) (contactsListView1.getItemAtPosition(i));
            //System.out.println(item);
            //checkedList[i] = item;
            if (checked.get(i)) {
                checkedList[k] = item;
>>>>>>> 5894b1362806a9b92c51606fb4d0870a816c6569
                k++;
            }
        }

        emailListView = (ListView) findViewById(R.id.emailListView);
        arrayList2 = new ArrayList<>(Arrays.asList(checkedList));
        itemsAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, arrayList2);
        emailListView.setAdapter(itemsAdapter2);
        emailListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

    }
<<<<<<< HEAD

    public void setContacts(){
=======

    public void setDelete() {
        //get checked items
        SparseBooleanArray checked = emailListView.getCheckedItemPositions();
        int k = 0;
        int size = contactsListView1.getAdapter().getCount();
        int indexList[] = new int[size];
        String notCheckedList[] = new String[size];
        String totalList[] = new String[size];
        String newList[] = new String[size];

        for (int i = 0; i < size; i++) {
            String item = (String) (emailListView.getItemAtPosition(i));
            totalList[i] = item;
            newList[i] = item;
            ;
            if (!checked.get(i)) {
                //indexList[k]=i;
                notCheckedList[k] = item;
                k++;
            }
        }

/*
        for (int i=0;i<indexList.length;i++){
            arrayList2.remove(indexList[i]);
        }
*/

        //itemsAdapter2.notifyDataSetChanged();

/*
        for (int i=0;i<((checkedList.length)-1);i++){
            System.out.println(checkedList[i]);
            for (int j=i+1;j<totalList.length;j++){
                System.out.println(totalList[j]);
                if (checkedList[i]==totalList[j]){
                    totalList[j]=null;
                }
            }
        }
*/

        emailListView = (ListView) findViewById(R.id.emailListView);
        arrayList3 = new ArrayList<>(Arrays.asList(notCheckedList));
        ArrayAdapter<String> itemsAdapter2 =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, arrayList3);
        emailListView.setAdapter(itemsAdapter2);
        emailListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

    }

    public void setContacts() {
>>>>>>> 5894b1362806a9b92c51606fb4d0870a816c6569

        //get user list from firebase database
        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();

        contactsListView1 = (ListView) findViewById(R.id.contactsListView1);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("contacts");

<<<<<<< HEAD
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseListAdapter<User> firebaseListAdapter = new FirebaseListAdapter<User>(
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
=======

        //create multiple choice list view

        FirebaseListAdapter<User> userFirebaseListAdapter = new FirebaseListAdapter<User>(
                this,
                User.class,android.R.layout.simple_list_item_multiple_choice,
                databaseReference) {
            @Override
            protected void populateView(View view, User user, int i) {
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setText((CharSequence) user);
                //textView.setText(i);
                System.out.println("dewgeEAGRHTDAEJEMRXMYUMSXMYSFX");
                System.out.println(textView);

>>>>>>> 5894b1362806a9b92c51606fb4d0870a816c6569
            }


        };
<<<<<<< HEAD
        contactsListView1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        contactsListView1.setAdapter(firebaseListAdapter);
    }


=======

        //contactsListView1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        //contactsListView1.setAdapter(userFirebaseListAdapter);
    }

    public void check(View v) {
        Toast.makeText(AddParticipants.this, check[0], Toast.LENGTH_LONG).show();
        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        for (int i = 0; i < 3; i++) {
            databaseReference.child("yes").child(Integer.toString(i)).setValue(check[i]);
        }

    }


    public void filter() {
/*
        List<String> td = (ArrayList<String>) dataSnapshot.getValue();
        String[] stringArray = td.toArray(new String[0]);
*/
        //setContacts();
        //lv = (ListView) findViewById(R.id.list_view);
        inputSearch = (EditText) findViewById(R.id.inputSearch);

        adapter = new ArrayAdapter<String>(this, R.layout.activity_add_participants);
        contactsListView1.setAdapter(adapter);
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                AddParticipants.this.adapter.getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals(" ")) {

                } else {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

>>>>>>> 5894b1362806a9b92c51606fb4d0870a816c6569

    public void onClick(View view) {

        //send email
<<<<<<< HEAD
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



 /*           //send notifications
            final HashMap<String,String> notificationData = new HashMap<>();
=======
        if (view.getId() == R.id.createButton) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("mailto:"));
            //String[] to = {"sammanianu123@gmail.com","sammanianu12@gmail.com"};
            //ListView lv = (ListView)findViewById ( R.id.emailListView );
            //String[] to = (String[]) listEmail.toArray ();

            String[] to = arrayList2.toArray(new String[0]);

            intent.putExtra(Intent.EXTRA_EMAIL, to);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Meeting Invitation");
            intent.putExtra(Intent.EXTRA_TEXT, "click this link");
            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent, "Send Email"));


            //send notifications
            final HashMap<String, String> notificationData = new HashMap<>();
>>>>>>> 5894b1362806a9b92c51606fb4d0870a816c6569
            String CurrntUserId = mAuth.getCurrentUser().getUid();
            //calculate unique number
            final String roomId = Integer.toString((int) System.currentTimeMillis());
            notificationData.put("from", CurrntUserId);
            notificationData.put("roomId", roomId);
            //notificationData.put("roomID",roomId);
            notificationData.put("type", "meeting creation");

            String[] sendUser = {"sha", "piyumi", "prabhath", "jay007", "cjw007"};


            //store evey participants deatials

            try {

                for (int i = 0; i < sendUser.length; i++) {


                    DatabaseReference reqst_userDB = reqstUser.child(sendUser[i]);


                    reqst_userDB.addValueEventListener(new ValueEventListener() {
                        @Override
<<<<<<< HEAD
                        public void onSuccess(Void aVoid) {
                        }
                    });
                }
                @Override
                public void onCancelled(DatabaseError error) {

                }
            });*/
=======
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
>>>>>>> 5894b1362806a9b92c51606fb4d0870a816c6569
        }
    }
}