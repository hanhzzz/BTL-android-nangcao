package com.example.btl_adr_nangcao.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.btl_adr_nangcao.Domain.Cart;
import com.example.btl_adr_nangcao.Domain.Foods;
import com.example.btl_adr_nangcao.Helper.ChangeNumberItemsListener;
import com.example.btl_adr_nangcao.Helper.ManagmentCart;
import com.example.btl_adr_nangcao.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.viewholder> {
    FirebaseFirestore firestore;
    FirebaseAuth mAuth;
    ArrayList<Cart> listCart;

    public CartAdapter(ArrayList<Cart> listCart) {
        this.listCart = listCart;
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public CartAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.viewholder holder, int position) {
        //set giao dien
        holder.title.setText(listCart.get(position).getCartTitle());
        holder.feeEachItem.setText((listCart.get(position).getCartNumberInCart()*listCart.get(position).getCartPrice())+"d");
        holder.totalEachItem.setText((listCart.get(position).getCartPrice())+"");
        holder.num.setText(listCart.get(position).getCartNumberInCart()+"");

        Glide.with(holder.itemView.getContext())
                .load(listCart.get(position).getCartImagePath())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.pic);

        //set chuc nang xoa
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("AddToCart").document(mAuth.getCurrentUser().getUid())
                        .collection("CurrentUser")
                        .document(listCart.get(position).getCartId())
                        .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                listCart.remove(listCart.get(position));
                                notifyDataSetChanged();
                            }
                        });
            }
        });

        //set chuc nang nut +
        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listCart.get(position).setCartNumberInCart(listCart.get(position).getCartNumberInCart()+1);
                String productId = listCart.get(position).getCartId();
                int newQuantity = listCart.get(position).getCartNumberInCart();
                updateQuantityFirestore(productId, newQuantity);
                notifyDataSetChanged();
            }
        });

        //set chuc nang nut -
        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listCart.get(position).getCartNumberInCart() == 1){
                    listCart.remove(position);
                }
                else{
                    listCart.get(position).setCartNumberInCart(listCart.get(position).getCartNumberInCart()-1);
                    String productId = listCart.get(position).getCartId();
                    int newQuantity = listCart.get(position).getCartNumberInCart();
                    updateQuantityFirestore(productId, newQuantity);
                }
                notifyDataSetChanged();
            }
        });


    }

    //update so luong vao firestore
    private void updateQuantityFirestore(String productId, int newQuantity) {
        firestore.collection("AddToCart").document(mAuth.getCurrentUser().getUid())
                .collection("CurrentUser")
                .document(productId)
                .update("cartNumberInCart", newQuantity).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("Hanhzzz", "Document successfully updated!");
                    }
                });
    }

    @Override
    public int getItemCount() {
        return listCart.size();
    }


    public class viewholder extends RecyclerView.ViewHolder {
        TextView title, feeEachItem, plusItem, minusItem;
        ImageView pic, btnDelete;
        TextView totalEachItem, num;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.txtTitle);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            plusItem = itemView.findViewById(R.id.btnPlusCart);
            minusItem = itemView.findViewById(R.id.btnMinusCart);
            pic = itemView.findViewById(R.id.pic);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            num = itemView.findViewById(R.id.txtNumberItem);
        }
    }
}
