package com.example.acmen;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerDetailAdapter extends RecyclerView.Adapter<RecyclerDetailAdapter.ViewHolder> {

    Context context;
    ArrayList<DetailModel> arrDetails ;
    MyDBhelper dBhelper;

    public RecyclerDetailAdapter(Context context,ArrayList<DetailModel> arrDetails){
        this.context = context;
        this.arrDetails = arrDetails;
        dBhelper = new MyDBhelper(context);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ArrayList<billDetail> arrbill =  dBhelper.getBillBook();
        View v = LayoutInflater.from(context).inflate(R.layout.detail_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(arrDetails.get(position).name);
        holder.gst.setText(arrDetails.get(position).gst_no);
        holder.address.setText(arrDetails.get(position).address);
        holder.number.setText(arrDetails.get(position).phoneNo);

        holder.llRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.update_lay);
                Button btnEdit = dialog.findViewById(R.id.btnEdit);
                Button btnDelete = dialog.findViewById(R.id.btnDelete);

                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                     Dialog dialog1 = new Dialog(context);
                     dialog1.setContentView(R.layout.edit_lay);

                        EditText nameText = dialog1.findViewById(R.id.nameText);
                        EditText gstText = dialog1.findViewById(R.id.gstText);
                        EditText addressText = dialog1.findViewById(R.id.addressText);
                        Button done = dialog1.findViewById(R.id.done);

                        nameText.setText(arrDetails.get(position).name);
                        gstText.setText(arrDetails.get(position).gst_no);
                        addressText.setText(arrDetails.get(position).address);
                        dialog.dismiss();
                        done.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String newName = nameText.getText().toString();
                                String newgst = gstText.getText().toString();
                                String newaddress = addressText.getText().toString();
                                int id = arrDetails.get(position).id;
                                dBhelper.editDetail(id,newName,newgst,newaddress);
                                Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                dialog1.dismiss();
                                ((Activity)context).finish();
                            }
                        });
                        dialog1.show();

                    }
                });

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       dBhelper.deleteDetail(arrDetails.get(position).id);
                        arrDetails.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }

        });


    }

    @Override
    public int getItemCount() {

        return arrDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,gst,address,number;
        LinearLayout llRow;
        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textName);
            gst = itemView.findViewById(R.id.textGst);
            address = itemView.findViewById(R.id.textAddress);
            llRow = itemView.findViewById(R.id.llRow);
            number = itemView.findViewById(R.id.number);
        }
    }
}
