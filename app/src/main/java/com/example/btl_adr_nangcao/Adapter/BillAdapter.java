package com.example.btl_adr_nangcao.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_adr_nangcao.Domain.Bill;
import com.example.btl_adr_nangcao.R;

import java.util.ArrayList;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.viewholder>{
    private LayoutInflater inflater;
    private ArrayList<Bill> listBill;

    public BillAdapter(ArrayList<Bill> listBill) {
        this.listBill = listBill;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_bill, parent, false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.txtBillId.setText(listBill.get(position).getBillId()+"");
        holder.txtLocation.setText(listBill.get(position).getLocation()+"");
        holder.txtTotalPrice.setText(listBill.get(position).getTotal()+"");
    }

    @Override
    public int getItemCount() {
        return listBill.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView txtLocation, txtTotalPrice, txtBillId;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            txtTotalPrice = itemView.findViewById(R.id.txtTotalPrice);
            txtBillId = itemView.findViewById(R.id.txtBillId);
        }
    }
}
