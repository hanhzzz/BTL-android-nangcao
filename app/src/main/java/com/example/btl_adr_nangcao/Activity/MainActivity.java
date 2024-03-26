package com.example.btl_adr_nangcao.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.btl_adr_nangcao.Adapter.BestFoodAdapter;
import com.example.btl_adr_nangcao.Adapter.CategoryAdapter;
import com.example.btl_adr_nangcao.Domain.Category;
import com.example.btl_adr_nangcao.Domain.Foods;
import com.example.btl_adr_nangcao.Domain.Location;
import com.example.btl_adr_nangcao.Domain.Price;
import com.example.btl_adr_nangcao.Domain.Time;
import com.example.btl_adr_nangcao.R;
import com.example.btl_adr_nangcao.databinding.ActivityMainBinding;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends BaseFirebaseClass {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        InitLocation();
        InitTime();
        InitPrice();
        InitBestFood();
        InitCategory();
        setVariable();
    }

    private void setVariable() {
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
    }

    private void InitBestFood() {
        DatabaseReference myRef = database.getReference("Foods");
        binding.progressBarBestFood.setVisibility(View.VISIBLE);
        ArrayList<Foods> listBestFood= new ArrayList<>();
        Query query = myRef.orderByChild("BestFood").equalTo(true);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue : snapshot.getChildren()){
                        listBestFood.add(issue.getValue(Foods.class));
                    }
                    if(listBestFood.size()>0){
                        binding.recyclerBestFood.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        RecyclerView.Adapter adapter = new BestFoodAdapter(listBestFood);
                        binding.recyclerBestFood.setAdapter(adapter);
                    }
                    binding.progressBarBestFood.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void InitCategory() {
        DatabaseReference myRef = database.getReference("Category");
        binding.progressBarBestFood.setVisibility(View.VISIBLE);
        ArrayList<Category> listCategory= new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue : snapshot.getChildren()){
                        listCategory.add(issue.getValue(Category.class));
                    }
                    if(listCategory.size()>0){
                        binding.recyclerCategory.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
                        RecyclerView.Adapter adapter = new CategoryAdapter(listCategory);
                        binding.recyclerCategory.setAdapter(adapter);
                    }
                    binding.progressBarCategory.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void InitLocation() {
        DatabaseReference myRef = database.getReference("Location");
        ArrayList<Location> listLocation = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue: snapshot.getChildren()){
                        listLocation.add(issue.getValue(Location.class));
                    }
                    ArrayAdapter<Location> adapterLocation = new ArrayAdapter<>(MainActivity.this, R.layout.spin_items, listLocation);
                    adapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.spnLocation.setAdapter(adapterLocation);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void InitTime() {
        DatabaseReference myRef = database.getReference("Time");
        ArrayList<Time> listTime = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue: snapshot.getChildren()){
                        listTime.add(issue.getValue(Time.class));
                    }
                    ArrayAdapter<Time> adapterLocation = new ArrayAdapter<>(MainActivity.this, R.layout.spin_items, listTime);
                    adapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.spnTime.setAdapter(adapterLocation);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void InitPrice() {
        DatabaseReference myRef = database.getReference("Price");
        ArrayList<Price> listPrice = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue: snapshot.getChildren()){
                        listPrice.add(issue.getValue(Price.class));
                    }
                    ArrayAdapter<Price> adapterLocation = new ArrayAdapter<>(MainActivity.this, R.layout.spin_items, listPrice);
                    adapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.spnPrice.setAdapter(adapterLocation);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}