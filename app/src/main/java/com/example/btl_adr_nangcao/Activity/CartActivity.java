package com.example.btl_adr_nangcao.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.btl_adr_nangcao.Adapter.CartAdapter;
import com.example.btl_adr_nangcao.Domain.Cart;
import com.example.btl_adr_nangcao.Domain.Foods;
import com.example.btl_adr_nangcao.Helper.ChangeNumberItemsListener;
import com.example.btl_adr_nangcao.Helper.ManagmentCart;
import com.example.btl_adr_nangcao.R;
import com.example.btl_adr_nangcao.databinding.ActivityCartBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class CartActivity extends BaseFirebaseClass {
    private ActivityCartBinding binding;
    ArrayList<Cart> listCart = new ArrayList<>();
    // lang nghe su kien thay doi du lieu cua gio hang
    ListenerRegistration listenerRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //tao firestore
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        
        setVariable();
        initList();
    }

    private void initList() {
//        if(managmentCart.getListCart().isEmpty()){
//            binding.txtEmpty.setVisibility(View.VISIBLE);
//            binding.scrollViewCart.setVisibility(View.GONE);
//        }
//        else{
            binding.txtEmpty.setVisibility(View.GONE);
            binding.scrollViewCart.setVisibility(View.VISIBLE);
//        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.cardView.setLayoutManager(linearLayoutManager);

        firestore.collection("AddToCart").document(mAuth.getCurrentUser().getUid())
                        .collection("CurrentUser").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                                //lay du lieu id tu document
                                String documentId = documentSnapshot.getId();
                                //tao 1 bien de hung du lieu tu document
                                Cart cart = documentSnapshot.toObject(Cart.class);
                                //set id cho moi product trong cart
                                cart.setCartId(documentId);
                                //tao list hang trong gio
                                listCart.add(cart);
                                RecyclerView.Adapter adapter = new CartAdapter(listCart);
                                binding.cardView.setAdapter(adapter);

                                //tinh tong tien
                                caculatorCart(documentSnapshot, listCart);
                            }
                        }
                    }
                });
    }

    private void caculatorCart(DocumentSnapshot documentSnapshot, ArrayList<Cart> listCart) {
        CollectionReference coRef  = firestore.collection("AddToCart").document(mAuth.getCurrentUser().getUid()).collection("CurrentUser");
        listenerRegistration = coRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Log.w(TAG, "Listen failed.", error);
                    return;
                }
                if(value != null){
                    double total = 0;
                    for (int i = 0; i < listCart.size(); i++){
                        total = total + (listCart.get(i).getCartPrice()*listCart.get(i).getCartNumberInCart());
                    }
                    binding.txtTotal.setText(total+" đ");
                }
            }
        });
    }


    private void setVariable() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                //tat lang nghe du lieu gio hang
                if(listenerRegistration != null){
                    listenerRegistration.remove();
                }
            }
        });

        binding.btnGiaohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //luu hashmap chua item vao firestore
                final HashMap<String,Object> cartMap = new HashMap<>();
                //luu item vao theo hashmap
                cartMap.put("billId", "");
                cartMap.put("location", binding.txtDiadiemnhan.getText().toString());
                cartMap.put("total", binding.txtTotal.getText().toString());
                firestore.collection("AddtoBill").document(mAuth.getCurrentUser().getUid())
                        .collection("CurrentUser").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                //lay id cua gio hang
                                String billIDgetFirestore = task.getResult().getId();
                                //set bill id
                                setBillId(billIDgetFirestore);
                                //set cac san pham co trong hoa don
                                addDatatoBill(billIDgetFirestore);

                                Toast.makeText(CartActivity.this, "Added BILL list cart to firestore", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            //set bill id
            private void setBillId(String billId){
                firestore.collection("AddtoBill").document(mAuth.getCurrentUser().getUid())
                        .collection("CurrentUser").document(billId)
                        .update("billId", billId).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
            }

            //set du lieu vao bill
            private void addDatatoBill(String billIDgetFirestore){
                for(int i = 0; i<listCart.size(); i++){
                    //tao hashmap
                    final HashMap<String,Object> cartMap = new HashMap<>();
                    //luu item vao theo hashmap
                    cartMap.put("billId", billIDgetFirestore);
                    cartMap.put("cartTitle", listCart.get(i).getCartTitle());
                    cartMap.put("cartPrice", listCart.get(i).getCartPrice());
                    cartMap.put("cartNumberInCart", listCart.get(i).getCartNumberInCart());

                    //luu hashmap chua item vao firestore
                    firestore.collection("AddtoBill").document(mAuth.getCurrentUser().getUid())
                            .collection("CurrentUser").document(billIDgetFirestore)
                            .collection("ListBill").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {

                                }
                            });
                }
            }
            //
        });
    }
}