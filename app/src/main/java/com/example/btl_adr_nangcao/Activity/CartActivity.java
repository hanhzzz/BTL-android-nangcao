package com.example.btl_adr_nangcao.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.btl_adr_nangcao.Adapter.CartAdapter;
import com.example.btl_adr_nangcao.Domain.Cart;
import com.example.btl_adr_nangcao.Helper.ChangeNumberItemsListener;
import com.example.btl_adr_nangcao.Helper.ManagmentCart;
import com.example.btl_adr_nangcao.R;
import com.example.btl_adr_nangcao.databinding.ActivityCartBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CartActivity extends BaseFirebaseClass {
    private ActivityCartBinding binding;
    private ManagmentCart managmentCart;
    ArrayList<Cart> listCart = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart = new ManagmentCart(this);

        //tao firestore
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        
        setVariable();
        //caculatorCart();
        initList();
    }

    private void initList() {
        if(managmentCart.getListCart().isEmpty()){
            binding.txtEmpty.setVisibility(View.VISIBLE);
            binding.scrollViewCart.setVisibility(View.GONE);
        }
        else{
            binding.txtEmpty.setVisibility(View.GONE);
            binding.scrollViewCart.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.cardView.setLayoutManager(linearLayoutManager);

        firestore.collection("AddToCart").document(mAuth.getCurrentUser().getUid())
                        .collection("CurrentUser").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            double total = 0;
                            for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                                //lay du lieu id tu document
                                String documentId = documentSnapshot.getId();
                                //tao 1 bien de hung du lieu tu document
                                Cart cart = documentSnapshot.toObject(Cart.class);
                                //set id cho moi product trong cart
                                cart.setCartId(documentId);

                                listCart.add(cart);
                                RecyclerView.Adapter adapter = new CartAdapter(listCart);
                                binding.cardView.setAdapter(adapter);

                                //tinh tong tien
                                double price = documentSnapshot.getDouble("cartPrice");
                                int quantity = documentSnapshot.getLong("cartNumberInCart").intValue();
                                total = total + price*quantity;
                            }
                            //set tong tien
                            binding.txtTotal.setText(total+"d");

                        }
                    }
                });
    }


    private void setVariable() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}