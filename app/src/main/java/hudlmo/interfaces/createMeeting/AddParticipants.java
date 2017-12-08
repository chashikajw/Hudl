package hudlmo.interfaces.createMeeting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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


public class AddParticipants extends AppCompatActivity implements View.OnClickListener {

    Button createButton , contactsButton, selectButton, deleteButton ;
    int index;
    ListView emailListView , contactsListView1;
    private ArrayList<String> arrayList1;
    private ArrayList<String> arrayList2;
    private ArrayList<String> arrayList3;
    //private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;
    private EditText addEmailText;
    //private String[] lv_arr,checkedList = {};

    private DatabaseReference mNotification;
    private DatabaseReference reqstUser;
    private FirebaseAuth mAuth;
    private DataSnapshot dataSnapshot;

    String[] contacts;
    String[] stringArray;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    EditText inputSearch;

    private ArrayAdapter mAdapter;
    ArrayAdapter<String> itemsAdapter2;


    ///@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_add_participants );

        contactsListView1 = (ListView)findViewById ( R.id.contactsListView1 );
        inputSearch = (EditText)findViewById(R.id.inputSearch);
        //initList();
        setContacts();
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals(" ")){
                    //initList();
                    setContacts();
                }
                else {
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

        Button selectButton = (Button)findViewById(R.id.selectButton);
        selectButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setCheckItemsEmailArrray();
            }
        });

        Button deleteButton = (Button)findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setDelete();
            }
        });


        // addEmailButton Button
 /*       addEmailText = (EditText)findViewById ( R.id.addEmailText );
        Button addEmailButton = (Button)findViewById ( R.id.addEmailButton );
        addEmailButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String newMailList[] = new String[1];
                String newMail = addEmailText.getText().toString();
                newMailList[0]=newMail;
                emailListView = (ListView)findViewById(R.id.emailListView);
                arrayList3 = new ArrayList<> ( Arrays.asList(newMailList) );
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList3);
                emailListView.setAdapter(itemsAdapter);

            }
        });
*/
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

    public void searchItem(String textToSearch){
        for (String item:contacts){
            if (!item.contains(textToSearch)){
                arrayList2.remove(item);
            }
        }
        adapter.notifyDataSetChanged();
    }
    public void initList(){
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
        contactsListView1 = (ListView)findViewById(R.id.contactsListView1);
        //int j;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("contacts");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long size = dataSnapshot.getChildrenCount();
                int j= (int)size;
                String contact[] = new String[j];

                for(int i=0;i<j;i++){
                    String item = (String)(contactsListView1.getItemAtPosition(i));
                    System.out.println(item);
                    contact[i] = item;

                }
                contactsListView1 = (ListView)findViewById(R.id.contactsListView1);
                arrayList1 = new ArrayList<> ( Arrays.asList(contact) );

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
    public void setCheckItemsEmailArrray(){
        SparseBooleanArray checked = contactsListView1.getCheckedItemPositions();
        int k=0;
        int j=contactsListView1.getAdapter().getCount();
        String checkedList[] = new String[j];

        for(int i=0;i<j;i++){
            String item = (String)(contactsListView1.getItemAtPosition(i));
            //System.out.println(item);
            //checkedList[i] = item;
            if (checked.get(i)){
                checkedList[k]=item;
                k++;
            }

        }

        emailListView = (ListView)findViewById(R.id.emailListView);
        arrayList2 = new ArrayList<> ( Arrays.asList(checkedList) );
        itemsAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, arrayList2);
        emailListView.setAdapter(itemsAdapter2);
        emailListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

    }
    public void setDelete(){
        //get checked items
        SparseBooleanArray checked = emailListView.getCheckedItemPositions();
        int k=0;
        int size=contactsListView1.getAdapter().getCount();
        int indexList[] = new int[size];
        String notCheckedList[] = new String[size];
        String totalList[] = new String[size];
        String newList[] = new String[size];

        for(int i=0;i<size;i++){
            String item = (String)(emailListView.getItemAtPosition(i));
            totalList[i]=item;
            newList[i]=item;;
            if (!checked.get(i)){
                //indexList[k]=i;
                notCheckedList[k]=item;
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

        emailListView = (ListView)findViewById(R.id.emailListView);
        arrayList3 = new ArrayList<> ( Arrays.asList(notCheckedList));
        ArrayAdapter<String> itemsAdapter2 =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, arrayList3);
        emailListView.setAdapter(itemsAdapter2);
        emailListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

    }
    public void setContacts(){

        //get user list from firebase database
        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();

        contactsListView1 = (ListView)findViewById(R.id.contactsListView1);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("contacts");



        //create multiple choice list view
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


    }


    public void filter(){
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
                if (s.toString().equals(" ")){

                }
                else{

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public void onClick(View view) {

        //send email
        if (view.getId()==R.id.createButton){
            Intent intent = new Intent( Intent.ACTION_SEND);
            intent.setData ( Uri.parse ("mailto:") );
            //String[] to = {"sammanianu123@gmail.com","sammanianu12@gmail.com"};
            //ListView lv = (ListView)findViewById ( R.id.emailListView );
            //String[] to = (String[]) listEmail.toArray ();

            String[] to = arrayList2.toArray(new String[0]);

            intent.putExtra ( Intent.EXTRA_EMAIL,to );
            intent.putExtra ( Intent.EXTRA_SUBJECT,"Meeting Invitation" );
            intent.putExtra ( Intent.EXTRA_TEXT,"click this link" );
            intent.setType ( "message/rfc822" );
            startActivity (Intent.createChooser ( intent,"Send Email" ));


            //send notifications
            final HashMap<String,String> notificationData = new HashMap<>();
            String CurrntUserId = mAuth.getCurrentUser().getUid();
            notificationData.put("from",CurrntUserId);
            notificationData.put("type","meeting creation");

            final String[] sendUser = {"cjw007","boby","jay007"};

            //store evey participants deatials

            reqstUser = reqstUser.child("boby");



            reqstUser.addValueEventListener(new ValueEventListener() {
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







        }
    }
}
