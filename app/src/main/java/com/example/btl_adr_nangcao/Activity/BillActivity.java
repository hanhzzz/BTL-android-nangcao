package com.example.btl_adr_nangcao.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.btl_adr_nangcao.Adapter.BillAdapter;
import com.example.btl_adr_nangcao.Domain.Bill;
import com.example.btl_adr_nangcao.databinding.ActivityBillBinding;
//import com.example.btl_adr_nangcao.databinding.ActivityViewHolderBillBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BillActivity extends BaseFirebaseClass {
    ActivityBillBinding binding;
    ArrayList<Bill> listBill = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBillBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //tao firestore
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        initBill();
        initVariable();
    }

    //tao hoa don
    private void initBill() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recycleBill.setLayoutManager(linearLayoutManager);

        //hien thi danh sach san pham trong hoa don
        firestore.collection("AddtoBill").document(mAuth.getCurrentUser().getUid())
                .collection("CurrentUser").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                                //lay id cua document chua bill
                                String idDocument = documentSnapshot.getId();
                                //truyen id cua document chua bill de lay du lieu ra
                                //initRecycleBill(idDocument);
                                Bill bill = documentSnapshot.toObject(Bill.class);
                                //truyen du lieu vao bill
                                listBill.add(bill);
                                RecyclerView.Adapter adapter = new BillAdapter(listBill);
                                binding.recycleBill.setAdapter(adapter);
                            }
                        }
                    }
                });
    }

    private void setBillId(String billId){
        firestore.collection("AddtoBill").document(mAuth.getCurrentUser().getUid())
                .collection("CurrentUser").document(billId)
                .update("billId", billId).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                        }
                    }
                });
    }

    //ham lay id cua document chua bill de lay du lieu ra
    private void initRecycleBill(String idDocument){
        firestore.collection("AddtoBill").document(mAuth.getCurrentUser().getUid())
                .collection("CurrentUser").document(idDocument)
                .collection("ListBill").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                                //tao 1 bien de hung du lieu tu document
                                Bill bill = documentSnapshot.toObject(Bill.class);
                                //tao list hang trong gio
                                listBill.add(bill);
                                RecyclerView.Adapter adapter = new BillAdapter(listBill);
                                binding.recycleBill.setAdapter(adapter);
                            }
                        }
                    }
                });
    }

    private void initVariable() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}