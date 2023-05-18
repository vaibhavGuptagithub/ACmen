package com.example.acmen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ShowBill extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bill);

        RecyclerView recyclerView = findViewById(R.id.recyclerDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ArrayList<DetailModel> arrDetails = new ArrayList<>();

        MyDBhelper dBhelper = new MyDBhelper(this);

        arrDetails = dBhelper.fetchDetail();
        if(arrDetails.size()==0){
            Toast.makeText(this, "Empty List! Please add Customer.", Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            RecyclerDetailAdapter adapter = new RecyclerDetailAdapter(this, arrDetails);
            recyclerView.setAdapter(adapter);
        }

    }
}