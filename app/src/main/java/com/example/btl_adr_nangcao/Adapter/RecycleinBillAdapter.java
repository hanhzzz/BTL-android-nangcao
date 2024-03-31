package com.example.btl_adr_nangcao.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_adr_nangcao.Domain.Cart;
import com.example.btl_adr_nangcao.Domain.IteminRecycleBill;
import com.example.btl_adr_nangcao.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecycleinBillAdapter extends RecyclerView.Adapter<RecycleinBillAdapter.viewholder> {
    FirebaseFirestore firestore;
    FirebaseAuth mAuth;
    ArrayList<IteminRecycleBill> listItemInRecycleBills;

    public RecycleinBillAdapter(ArrayList<IteminRecycleBill> iteminRecycleBills) {
        this.listItemInRecycleBills = iteminRecycleBills;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_recycle_in_bill, parent, false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        //set giao dien
        holder.txtTitleinBill.setText(listItemInRecycleBills.get(position).getCartTitle()+"");
        holder.txtQuantityinBill.setText(listItemInRecycleBills.get(position).getCartNumberInCart()+"");
        holder.txtPriceinBill.setText(listItemInRecycleBills.get(position).getCartPrice()+"d");
    }

    @Override
    public int getItemCount() {
        return listItemInRecycleBills.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView txtTitleinBill, txtQuantityinBill, txtPriceinBill;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            txtTitleinBill = itemView.findViewById(R.id.txtTitleinBill);
            txtQuantityinBill = itemView.findViewById(R.id.txtQuantityinBill);
            txtPriceinBill = itemView.findViewById(R.id.txtPriceinBill);
        }
    }
}
