package com.example.btl_adr_nangcao.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.btl_adr_nangcao.R;
import com.example.btl_adr_nangcao.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseFirebaseClass {
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BtnLoginAction();
        RegisterNow();
    }

    private void BtnLoginAction(){
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }

    private void RegisterNow(){
        binding.registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"loi an dang ky ngay");
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}