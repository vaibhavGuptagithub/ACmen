package com.example.acmen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

public class bill_template extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_template);
        RecyclerView billrecyclerView = findViewById(R.id.billRecycler);
        billrecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        RecyclerBillAdapter billAdapter = new RecyclerBillAdapter(this);
        billrecyclerView.setAdapter(billAdapter);
    }
}