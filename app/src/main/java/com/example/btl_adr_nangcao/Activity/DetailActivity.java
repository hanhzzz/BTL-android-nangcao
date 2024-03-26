package com.example.btl_adr_nangcao.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.btl_adr_nangcao.Domain.Foods;
import com.example.btl_adr_nangcao.Helper.ManagmentCart;
import com.example.btl_adr_nangcao.R;
import com.example.btl_adr_nangcao.databinding.ActivityDetailBinding;

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
        binding.txtRate.setText(object.getStar() + "rating");
        binding.ratingBar.setRating((float) object.getStar());
        binding.txtTotal.setText(num*object.getPrice()+"d");

        binding.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num = num + 1;
                binding.txtNum.setText(num + "");
                binding.txtTotal.setText(num*object.getPrice()+"d");
            }
        });

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

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                object.setNumberInCart(num);
                managmentCart.insertFood(object);
            }
        });
    }

    private void getIntentExtra() {
        object = (Foods) getIntent().getSerializableExtra("object");
    }
}