package com.example.btl_adr_nangcao.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.btl_adr_nangcao.Domain.Foods;
import com.example.btl_adr_nangcao.Helper.ManagmentCart;
import com.example.btl_adr_nangcao.R;
import com.example.btl_adr_nangcao.databinding.ActivityDetailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class DetailActivity extends BaseFirebaseClass {
    ActivityDetailBinding binding;
    private Foods object;
    private int num = 1;
    private ManagmentCart managmentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //set firestore
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        
        getIntentExtra();
        setVariable();
    }

    private void setVariable() {
        managmentCart = new ManagmentCart(this);

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Glide.with(DetailActivity.this)
                .load(object.getImagePath())
                .into(binding.pic);

        binding.txtPrice.setText(object.getPrice()+"d");
        binding.txtTitle.setText(object.getTitle());
        binding.txtDecription.setText(object.getDescription());
        binding.txtRate.setText(object.getStar() + " rating");
        binding.ratingBar.setRating((float) object.getStar());
        binding.txtTotal.setText(num*object.getPrice()+"d");

        //them san pham
        binding.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num = num + 1;
                binding.txtNum.setText(num + "");
                binding.txtTotal.setText(num*object.getPrice()+"d");
            }
        });

        //tru san pham
        binding.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num>1){
                    num = num - 1;
                    binding.txtNum.setText(num+"");
                    binding.txtTotal.setText(num*object.getPrice()+"d");
                }
            }
        });

        //them vao gio hang
        binding.btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                object.setNumberInCart(num);
                //managmentCart.insertFood(object);

                //tao hashmap
                final HashMap<String,Object> cartMap = new HashMap<>();
                //luu item vao theo hashmap
                cartMap.put("cartTitle", object.getTitle());
                cartMap.put("cartPrice", object.getPrice());
                cartMap.put("cartImagePath", object.getImagePath());
                cartMap.put("cartStar", object.getStar());
                cartMap.put("cartDescription", object.getDescription());
                cartMap.put("cartNumberInCart", object.getNumberInCart());

                //luu hashmap chua item vao firestore
                firestore.collection("AddToCart").document(mAuth.getCurrentUser().getUid())
                        .collection("CurrentUser").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(DetailActivity.this, "Added to firestore", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void getIntentExtra() {
        object = (Foods) getIntent().getSerializableExtra("object");
    }
}