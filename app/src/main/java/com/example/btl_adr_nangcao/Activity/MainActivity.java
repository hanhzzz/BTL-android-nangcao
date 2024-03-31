package com.example.btl_adr_nangcao.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.btl_adr_nangcao.Adapter.BestFoodAdapter;
import com.example.btl_adr_nangcao.Adapter.CategoryAdapter;
import com.example.btl_adr_nangcao.Domain.Category;
import com.example.btl_adr_nangcao.Domain.Foods;
import com.example.btl_adr_nangcao.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends BaseFirebaseClass {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();

        InitBestFood();
        InitCategory();
        setVariable();
    }

    private void setVariable() {
        //set ten nguoi dung
        DatabaseReference dataRef = database.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("name");
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nameCurrentUser = snapshot.getValue(String.class);
                binding.nameCurrentUser.setText(nameCurrentUser+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = binding.edtSearch.getText().toString();
                if(!text.isEmpty()){
                    Intent intent = new Intent(MainActivity.this, ListFoodActivity.class);
                    intent.putExtra("text", text);
                    intent.putExtra("isSearch",true);
                    startActivity(intent);
                }
            }
        });

        binding.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });

        binding.btnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BillActivity.class);
                startActivity(intent);
            }
        });
    }

    private void InitBestFood() {
        binding.progressBarBestFood.setVisibility(View.VISIBLE);
        ArrayList<Foods> listBestFood= new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference bestFoodCollection = FirebaseFirestore.getInstance().collection("Foods");
        Query query = bestFoodCollection.whereEqualTo("BestFood", true);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        Foods foods = document.toObject(Foods.class);
                        listBestFood.add(foods);
                    }
                    if(listBestFood.size()>0){
                        Log.d(TAG, "da tao list best food");
                        binding.recyclerBestFood.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        RecyclerView.Adapter adapter = new BestFoodAdapter(listBestFood);
                        binding.recyclerBestFood.setAdapter(adapter);
                    }
                    binding.progressBarBestFood.setVisibility(View.GONE);
                }
            }
        });
    }

    private void InitCategory() {
        binding.progressBarBestFood.setVisibility(View.VISIBLE);
        ArrayList<Category> listCategory= new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Category").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        Category category = document.toObject(Category.class);
                        listCategory.add(category);
                    }
                    if(listCategory.size()>0){
                        Log.d(TAG, "da tao list best food");
                        binding.recyclerCategory.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
                        RecyclerView.Adapter adapter = new CategoryAdapter(listCategory);
                        binding.recyclerCategory.setAdapter(adapter);
                    }
                    binding.progressBarCategory.setVisibility(View.GONE);
                }
            }
        });
    }

    //
}