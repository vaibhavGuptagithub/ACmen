package com.example.acmen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class billIntent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_intent);
        EditText rate = findViewById(R.id.rate);
        EditText AutoGst = findViewById(R.id.AutoGst);
        EditText tax = findViewById(R.id.Tax);
        Spinner unit = findViewById(R.id.unit);
        Button plusButton = findViewById(R.id.addMaterial);
        AutoCompleteTextView materialSpinner = findViewById(R.id.materialSpinner);
        MyDBhelper dBhelper = new MyDBhelper(this);
        ArrayList<DetailModel> arrDetails = dBhelper.fetchDetail();
        EditText quantity = findViewById(R.id.quantity);
        ArrayList<String> arrName = new ArrayList<>();
        ArrayList<String> arrUnit = new ArrayList<>();
        arrUnit.add("Kg");
        arrUnit.add("pcs.");
        arrUnit.add("L");
        for(DetailModel model : arrDetails){
            arrName.add(model.name);
        }
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.AutoName);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,arrName);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setThreshold(1);
        ArrayAdapter<String> adapterUnit = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,arrUnit);
        unit.setAdapter(adapterUnit);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedItem = (String) adapterView.getItemAtPosition(i);
                for (DetailModel model : arrDetails) {
                    if (model.name.equals(selectedItem)) {
                        AutoGst.setText(model.gst_no);
                        break;
                    }
                }
            }
        });
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog add = new Dialog(billIntent.this);
                add.setContentView(R.layout.material_add_lay);
                EditText enteredMaterial = add.findViewById(R.id.EnteredMaterial);
                EditText enteredHsn = add.findViewById(R.id.EnteredHsn);
                EditText enteredTax = add.findViewById(R.id.EnteredTax);
                Button addBtn = add.findViewById(R.id.addBtn);
                addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String EnteredMaterial = enteredMaterial.getText().toString();
                        String EnteredHsn = enteredHsn.getText().toString();
                        try {
                            int EnteredTax = Integer.parseInt(enteredTax.getText().toString());
                            dBhelper.addMaterial(EnteredMaterial,EnteredHsn,EnteredTax);
                            add.dismiss();
                            finish();
                        } catch(Exception e) {
                            Toast.makeText(billIntent.this, "Only number is allowed(in Tax) ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                add.show();
            }
        });
        ArrayList<material> arrMaterial = dBhelper.fetchMaterial();
        ArrayList<String> arrMaterialName = new ArrayList<>();
        for (material m : arrMaterial){
            arrMaterialName.add(m.material);
        }
        ArrayAdapter<String> materialadapter = new ArrayAdapter<>(billIntent.this, android.R.layout.simple_dropdown_item_1line,arrMaterialName);
        materialSpinner.setThreshold(1);
        materialSpinner.setAdapter(materialadapter);
        materialSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String matter = (String) adapterView.getItemAtPosition(i);
                for (material matters : arrMaterial){
                    if (matters.material.equals(matter)){
                        tax.setText(String.valueOf(matters.tax)+"%");
                        break;
                    }
                }
            }
        });

        Button create = findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dBhelper.fetchMaterial().size()==0){
                    Toast.makeText(billIntent.this, "Please add Material Details.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH) + 1;  // Month starts from 0, so add 1
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    String currentDate = day + "/" + month + "/" + year;

                    String customerName = autoCompleteTextView.getText().toString();
                    int customerId = 0;
                    for (DetailModel d : arrDetails) {
                        if (d.name.equals(customerName)) {
                            customerId = d.id;
                            break;
                        }
                    }
//                Toast.makeText(getApplicationContext(), "me", Toast.LENGTH_SHORT).show();
                    String materialName = materialSpinner.getText().toString();
                    int materialId = 0;
//                Toast.makeText(getApplicationContext(), ""+materialName, Toast.LENGTH_SHORT).show();
                    for (material m : arrMaterial) {
                        if (m.material.equals(materialName)) {
                            materialId = m.id;
                            break;
                        }
                    }

                    int BillQuantity = Integer.parseInt(quantity.getText().toString());
                    int BillRate = Integer.parseInt(rate.getText().toString());

                    dBhelper.createBill(customerId, materialId, currentDate, BillQuantity, BillRate);

                    finish();
                }
            }
        });
    }

}