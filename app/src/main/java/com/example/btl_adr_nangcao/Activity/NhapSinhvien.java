package com.example.btl_adr_nangcao.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.btl_adr_nangcao.Domain.Sinhvien;
import com.example.btl_adr_nangcao.R;
import com.example.btl_adr_nangcao.databinding.ActivityNhapSinhvienBinding;
import com.example.btl_adr_nangcao.databinding.ActivityThiBtlBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class NhapSinhvien extends BaseFirebaseClass {
    ActivityNhapSinhvienBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNhapSinhvienBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        binding.btnthemsinhvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sinhvien sinhvien;

                final HashMap<String,String> cartMap = new HashMap<>();
                //luu item vao theo hashmap
                cartMap.put("MaSV", binding.masinhvien.getText().toString());
                cartMap.put("HotenSv", binding.tensinhvien.getText().toString());
                cartMap.put("Diachi", binding.diachi.getText().toString());
                cartMap.put("Ngaysinh", binding.ngaysinh.getText().toString());
                cartMap.put("Gioitinh", binding.gioitinh.getText().toString());
                cartMap.put("Email", binding.email.getText().toString());
                cartMap.put("Diemtrungbinh", binding.diemtrungbinh.getText().toString());

                //luu hashmap chua item vao firestore
                firestore.collection("Sinhvien").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(NhapSinhvien.this, "Added to firestore", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}