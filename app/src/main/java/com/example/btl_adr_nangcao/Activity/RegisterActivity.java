package com.example.btl_adr_nangcao.Activity;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.btl_adr_nangcao.Domain.UserModel;
import com.example.btl_adr_nangcao.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;


public class RegisterActivity extends BaseFirebaseClass {
    private ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //btn_register
        BtnRegisterAction();
        LoginNow();
    }

    private void BtnRegisterAction(){
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.emailRegister.getText().toString();
                String name = binding.name.getText().toString();
                String password = binding.passwordRegister.getText().toString();
                String repassword = binding.rePasswordRegister.getText().toString();

                if(password.equals(repassword) == false){
                    Toast.makeText(RegisterActivity.this, "Nhập lại mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                    binding.rePasswordRegister.setText("");
                    return;
                }
                if(password.length() < 8){
                    Toast.makeText(RegisterActivity.this, "Mật khẩu phải nhiều hơn 8 ký tự", Toast.LENGTH_SHORT).show();
                    binding.passwordRegister.setText("");
                    binding.rePasswordRegister.setText("");
                    return;
                }
                //tao tk
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isComplete()){
                            Log.i(TAG, "loi tai complete create user");

                            UserModel userModel = new UserModel(name, email, password);
                            String id = task.getResult().getUser().getUid();
                            database.getReference("Users").child(id).setValue(userModel);
                            Toast.makeText(RegisterActivity.this, "Dang ky thanh cong", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        }
                        else{
                            Log.i(TAG, "loi tai "+task.getException());
                            Toast.makeText(RegisterActivity.this, "Đăng ký thật bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                createUser();
            }
        });
    }

    private void createUser() {
        String email = binding.emailRegister.getText().toString();
        String name = binding.name.getText().toString();
        String password = binding.passwordRegister.getText().toString();
        String repassword = binding.rePasswordRegister.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Chua nhap email", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    //action da co tai khoan? dang nhap
    private void LoginNow(){
        binding.loginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

            }
        });
    }
}