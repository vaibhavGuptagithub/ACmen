package com.example.acmen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerBillAdapter extends RecyclerView.Adapter<RecyclerBillAdapter.ViewHolder> {

    Context context;
    MyDBhelper dBhelper;

    ArrayList<billDetail> arrBills;
    ArrayList<userDetail> arrUserDetail;
    ArrayList<DetailModel> arrCustomerDetails;
    ArrayList<material> arrMaterial;


    public RecyclerBillAdapter(Context context){
        this.context = context;
        dBhelper = new MyDBhelper(context);
        arrBills = dBhelper.getBillBook();
        arrUserDetail = dBhelper.getUserDetail();
        arrCustomerDetails = dBhelper.fetchDetail();
        arrMaterial = dBhelper.fetchMaterial();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.bill_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.templateBillNo.setText("Bill no. "+arrBills.get(position).bill_no);
        holder.templateBillName.setText(""+arrUserDetail.get(arrUserDetail.size()-1).name);
        holder.templateBillDate.setText(""+arrBills.get(position).date);

        int CustomerId = arrBills.get(position).customer_id;
        holder.templateBillCustomerName.setText(""+arrCustomerDetails.get(CustomerId-1).name);
        holder.templateCustomerGst.setText(""+arrCustomerDetails.get(CustomerId-1).gst_no);
        holder.templateCustomerAddress.setText(""+arrCustomerDetails.get(CustomerId-1).address);
        holder.templateCustomerNumber.setText(""+arrCustomerDetails.get(CustomerId-1).phoneNo);

        holder.templateUserName.setText(arrUserDetail.get(arrUserDetail.size()-1).name);
        holder.templateUserGst.setText(arrUserDetail.get(arrUserDetail.size()-1).gst);
        holder.templateUserAddress.setText(arrUserDetail.get(arrUserDetail.size()-1).address);
        holder.templateUserNumber.setText(arrUserDetail.get(arrUserDetail.size()-1).number);

        int MaterialId = arrBills.get(position).material_id;
        holder.templateMaterialName.setText(""+arrMaterial.get(MaterialId-1).material);
        holder.templateHsn.setText(""+arrMaterial.get(MaterialId-1).hsn);
        holder.templateTax.setText(""+arrMaterial.get(MaterialId-1).tax+"%  =  ");
        holder.templateQuantity.setText(""+arrBills.get(position).quantity);
        holder.templateRate.setText(""+arrBills.get(position).rate);

        int amount = arrBills.get(position).quantity * arrBills.get(position).rate;
        holder.templateAmount.setText(String.valueOf(amount));

        holder.taxable.setText(""+String.valueOf((arrMaterial.get(MaterialId-1).tax * amount)/100));

        int totalAmount = ((arrMaterial.get(MaterialId-1).tax * amount)/100)+amount;
        holder.totalAmount.setText(" = "+String.valueOf(totalAmount));

    }

    @Override
    public int getItemCount() {
        return arrBills.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView templateBillNo,templateBillName,templateBillDate,templateBillCustomerName,templateCustomerGst,
        templateCustomerAddress,templateCustomerNumber,templateUserName,templateUserGst,templateUserAddress,templateUserNumber,
                templateMaterialName,templateHsn,templateQuantity,templateRate, taxable,templateAmount, templateTax, totalAmount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            templateBillNo = itemView.findViewById(R.id.template_bill_no);
            templateBillName= itemView.findViewById(R.id.template_bill_name);
            templateBillDate= itemView.findViewById(R.id.template_bill_date);
            templateBillCustomerName= itemView.findViewById(R.id.template_bill_customerName);
            templateCustomerGst= itemView.findViewById(R.id.template_bill_customerGst);

            templateCustomerAddress= itemView.findViewById(R.id.template_bill_customerAddress);
            templateCustomerNumber= itemView.findViewById(R.id.template_bill_customerNumber);
            templateUserName= itemView.findViewById(R.id.template_bill_userName);
            templateUserGst= itemView.findViewById(R.id.template_bill_userGst);
            templateUserAddress= itemView.findViewById(R.id.template_bill_userAddress);
            templateUserNumber= itemView.findViewById(R.id.template_bill_userNumber);

            templateMaterialName= itemView.findViewById(R.id.template_materialName);
            templateHsn= itemView.findViewById(R.id.template_materialHsn);
            templateQuantity= itemView.findViewById(R.id.template_materialQuantity);
            templateRate= itemView.findViewById(R.id.template_materialRate);
            taxable = itemView.findViewById(R.id.taxable);
            templateAmount= itemView.findViewById(R.id.template_materialAmount);
            templateTax= itemView.findViewById(R.id.template_materialTax);
            totalAmount= itemView.findViewById(R.id.template_bill_totalAmount);
        }
    }
}
