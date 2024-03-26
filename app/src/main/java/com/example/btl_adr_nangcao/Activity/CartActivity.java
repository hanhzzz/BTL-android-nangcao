package com.example.btl_adr_nangcao.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.btl_adr_nangcao.Adapter.CartAdapter;
import com.example.btl_adr_nangcao.Helper.ChangeNumberItemsListener;
import com.example.btl_adr_nangcao.Helper.ManagmentCart;
import com.example.btl_adr_nangcao.R;
import com.example.btl_adr_nangcao.databinding.ActivityCartBinding;

public class CartActivity extends BaseFirebaseClass {
    private ActivityCartBinding binding;
    private RecyclerView.Adapter adapter;
    private ManagmentCart managmentCart;
    private  double tax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart = new ManagmentCart(this);
        
        setVariable();
        caculatorCart();
        initList();
    }

    private void initList() {
        if(managmentCart.getListCart().isEmpty()){
            binding.txtEmpty.setVisibility(View.VISIBLE);
            binding.scrollViewCart.setVisibility(View.GONE);
        }
        else{
            binding.txtEmpty.setVisibility(View.GONE);
            binding.scrollViewCart.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.cardView.setLayoutManager(linearLayoutManager);
        adapter = new CartAdapter(managmentCart.getListCart(), this, new ChangeNumberItemsListener() {
            @Override
            public void change() {
                caculatorCart();
            }
        });
        binding.cardView.setAdapter(adapter);
    }

    private void caculatorCart() {
        double percentTax = 0.02;
        double delivery = 10000;

        tax = Math.round(managmentCart.getTotalFee() * percentTax * 100) / 100;

        double total = Math.round((managmentCart.getTotalFee() + tax + delivery) * 100) / 100;
        double itemTotal = Math.round(managmentCart.getTotalFee() * 100) / 100;

        binding.txtTotalFee.setText(itemTotal+"d");
        binding.txtTax.setText(tax+"d");
        binding.txtDelivery.setText(delivery+"d");
        binding.txtTotal.setText(total+"d");
    }

    private void setVariable() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}