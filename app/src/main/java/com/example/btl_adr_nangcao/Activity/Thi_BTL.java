package com.example.btl_adr_nangcao.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.btl_adr_nangcao.Adapter.CategoryAdapter;
import com.example.btl_adr_nangcao.Adapter.SinhvienAdapter;
import com.example.btl_adr_nangcao.Domain.Category;
import com.example.btl_adr_nangcao.Domain.Foods;
import com.example.btl_adr_nangcao.Domain.Sinhvien;
import com.example.btl_adr_nangcao.R;
import com.example.btl_adr_nangcao.databinding.ActivityListFood2Binding;
import com.example.btl_adr_nangcao.databinding.ActivityThiBtlBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Thi_BTL extends BaseFirebaseClass {
    ActivityThiBtlBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThiBtlBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.Activethem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Thi_BTL.this, NhapSinhvien.class);
                startActivity(intent);
            }
        });

        initList();
    }

    private void initList() {
        ArrayList<Sinhvien> listSinhvien = new ArrayList<>();

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        CollectionReference ref = FirebaseFirestore.getInstance().collection("Sinhvien");
        Query query = ref.whereEqualTo("BestFood", true);

        firestore.collection("Sinhvien").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        Sinhvien sinhvien = document.toObject(Sinhvien.class);
                        listSinhvien.add(sinhvien);
                    }
                    if(listSinhvien.size()>0){
                        Log.d(TAG, "da tao list sinhvien");
                        binding.recycleSinhvien.setLayoutManager(new LinearLayoutManager(Thi_BTL.this, LinearLayoutManager.VERTICAL, false));
                        RecyclerView.Adapter adapter = new SinhvienAdapter(listSinhvien);
                        binding.recycleSinhvien.setAdapter(adapter);
                    }
                }
            }
        });
    }
}