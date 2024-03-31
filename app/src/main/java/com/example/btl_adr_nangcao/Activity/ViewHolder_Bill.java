package com.example.btl_adr_nangcao.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.btl_adr_nangcao.Adapter.CartAdapter;
import com.example.btl_adr_nangcao.Adapter.RecycleinBillAdapter;
import com.example.btl_adr_nangcao.Domain.Cart;
import com.example.btl_adr_nangcao.Domain.IteminRecycleBill;
import com.example.btl_adr_nangcao.R;
import com.example.btl_adr_nangcao.databinding.ActivityCartBinding;
import com.example.btl_adr_nangcao.databinding.ActivityViewHolderBillBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewHolder_Bill extends BaseFirebaseClass {
    ActivityViewHolderBillBinding binding;
    ArrayList<Cart> listCart = new ArrayList<>();
    ArrayList<IteminRecycleBill> listIteminRecycleBill = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewHolderBillBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //tao firestore
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        initBill();
    }

    private void initBill() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerViewBill.setLayoutManager(linearLayoutManager);

        //hien thi danh sach san pham trong hoa don
        firestore.collection("AddtoBill").document(mAuth.getCurrentUser().getUid())
                .collection("CurrentUser").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                                String idDocument = documentSnapshot.getId();
                                initRecycleBill(idDocument);
                            }
                        }
                    }
                });

        //hien thi noi nhan va tong tien cua bill
        firestore.collection("AddtoBill").document(mAuth.getCurrentUser().getUid())
                .collection("CurrentUser").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                                binding.txtLocation.setText(documentSnapshot.getString("location"));
                                binding.txtTotalPrice.setText(documentSnapshot.getString("total"));
                            }
                        }
                    }
                });
    }

    private void initRecycleBill(String idDocument){
        firestore.collection("AddtoBill").document(mAuth.getCurrentUser().getUid())
                .collection("CurrentUser").document(idDocument)
                .collection("ListBill").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                                //tao 1 bien de hung du lieu tu document
                                IteminRecycleBill iteminRecycleBill = documentSnapshot.toObject(IteminRecycleBill.class);
                                //tao list hang trong gio
                                listIteminRecycleBill.add(iteminRecycleBill);
                                RecyclerView.Adapter adapter = new RecycleinBillAdapter(listIteminRecycleBill);
                                binding.recyclerViewBill.setAdapter(adapter);
                            }
                        }
                    }
                });
    }

}