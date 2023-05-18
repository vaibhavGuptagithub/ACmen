package com.example.acmen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class addIntent extends AppCompatActivity {
//    ArrayList<help> detail = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_intent);
        EditText partyName = findViewById(R.id.partyName);
        EditText gstNo = findViewById(R.id.gstNo);
        EditText address =  findViewById(R.id.adress);
        Button add = findViewById(R.id.add);
        EditText phone = findViewById(R.id.mobileNo);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = partyName.getText().toString();
                String gst = gstNo.getText().toString();
                String thisAddress = address.getText().toString();
                String mobileNo = phone.getText().toString();

                if((gst.equals("")) && (name.equals(""))){
                    Toast.makeText(addIntent.this, "Please enter details.", Toast.LENGTH_SHORT).show();
                }
                else {
                    MyDBhelper db = new MyDBhelper(getApplicationContext());
                    db.addDetail(name, gst, thisAddress,mobileNo);
                    Toast.makeText(addIntent.this, "Added Successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });


    }


}


