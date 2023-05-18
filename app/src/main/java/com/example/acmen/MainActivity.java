package com.example.acmen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int x=0;
        setContentView(R.layout.activity_main);
        Button bill = findViewById(R.id.bill);
        Button add = findViewById(R.id.add);
        Button show = findViewById(R.id.show);
        Button billBook = findViewById(R.id.BillBook);
        MyDBhelper dBhelper = new MyDBhelper(MainActivity.this);
        TextView newUser = findViewById(R.id.newUser);
        Intent billIntent = new Intent(getApplicationContext(), com.example.acmen.billIntent.class);
        Intent addIntent = new Intent(getApplicationContext(), com.example.acmen.addIntent.class);
        Intent showIntent = new Intent(getApplicationContext(),com.example.acmen.ShowBill.class);
        Intent billBookIntent = new Intent(getApplicationContext(),com.example.acmen.bill_template.class);
        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dBhelper.getUserDetail().size()>0 && dBhelper.fetchDetail().size()>0) {
                    startActivity(billIntent);
                } else{
                    Toast.makeText(MainActivity.this, "Please add Customer or User Detail", Toast.LENGTH_SHORT).show();
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(addIntent);
            }
        });
       show.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(showIntent);
           }
       });
       billBook.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(dBhelper.getBillBook().size()>0) {
                   startActivity(billBookIntent);
               } else{
                   Toast.makeText(MainActivity.this, "You had not created any bill", Toast.LENGTH_SHORT).show();
               }
           }
       });

       newUser.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Dialog userDialog = new Dialog(MainActivity.this);
               userDialog.setContentView(R.layout.first_ser);
               EditText userCompany = userDialog.findViewById(R.id.UserCompany);
               EditText userGst = userDialog.findViewById(R.id.UserGst);
               EditText userAddress = userDialog.findViewById(R.id.UserAddress);
               EditText userDescription = userDialog.findViewById(R.id.UserDescription);
               EditText userNumber = userDialog.findViewById(R.id.UserNumber);
               Button userDone = userDialog.findViewById(R.id.UserDone);

               userDone.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       String name = userCompany.getText().toString();
                       String gst = userGst.getText().toString();
                       String address = userAddress.getText().toString();
                       String description = userDescription.getText().toString();
                       String number = userNumber.getText().toString();
                       dBhelper.addUserDetail(name,gst,address,number,description);
                       Toast.makeText(MainActivity.this, "Your Details Successfully added!", Toast.LENGTH_SHORT).show();
                       userDialog.dismiss();
                   }
               });

               userDialog.show();
           }
       });

    }
}