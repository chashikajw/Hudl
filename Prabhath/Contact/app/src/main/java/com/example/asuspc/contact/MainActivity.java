package com.example.asuspc.contact;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
    }

    public void showNext(View view){
        String button_text;
        button_text=((Button) view).getText().toString();
        if (button_text.equals("Create new Group")){
            Intent intent= new Intent(this,SaveContact.class);
            startActivity(intent);
        }
        else if (button_text.equals("Add new contact")){
            Intent intent=new Intent(this,CreateGroup.class);
            startActivity(intent);
        }
    }
}
