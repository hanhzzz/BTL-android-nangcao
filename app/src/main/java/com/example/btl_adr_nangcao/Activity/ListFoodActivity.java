package com.example.btl_adr_nangcao.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.btl_adr_nangcao.Adapter.FoodListAdapter;
import com.example.btl_adr_nangcao.Domain.Foods;
import com.example.btl_adr_nangcao.R;
import com.example.btl_adr_nangcao.databinding.ActivityListFood2Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ListFoodActivity extends BaseFirebaseClass {
    ActivityListFood2Binding binding;
    private RecyclerView.Adapter adapterListFood;
    private int categoryId;
    private String categoryName;
    private String searchText;
    private boolean isSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListFood2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentExtra();
        initList();
        setVariable();
    }

    private void setVariable() {
    }

    private void initList() {

        ArrayList<Foods> list = new ArrayList<>();

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        Query query;

        CollectionReference itemsRef = firestore.collection("Foods");
        if(isSearch){
            query = itemsRef.whereEqualTo("Title", searchText);
        }
        else{
            query = itemsRef.whereEqualTo("CategoryId", categoryId);
        }

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        Foods foods = document.toObject(Foods.class);
                        list.add(foods);

                    }
                    if(list.size()>0){
                        Log.d(TAG, "da tao list");
                        binding.FoodListView.setLayoutManager(new GridLayoutManager(ListFoodActivity.this, 2));
                        RecyclerView.Adapter adapterListFood = new FoodListAdapter(list);
                        binding.FoodListView.setAdapter(adapterListFood);
                    }
                    binding.progressBar.setVisibility(View.GONE);
                }
            }
        });

    }

    private void getIntentExtra() {
        categoryId = getIntent().getIntExtra("CategoryId", 0);
        categoryName = getIntent().getStringExtra("CategoryName");
        searchText = getIntent().getStringExtra("text");
        isSearch = getIntent().getBooleanExtra("isSearch", false);

        binding.txtTitle.setText(categoryName);
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}