package com.example.btl_adr_nangcao;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;


public class RegisterActivity extends BaseFirebaseClass {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //btn_register
        BtnRegisterAction();
        RegisterNow();
    }

    private void BtnRegisterAction(){
        //anh xa
        EditText emailRegister = findViewById(R.id.emailLogin);
        EditText passwordRegister = findViewById(R.id.passwordRegister);
        EditText rePasswordRegister = findViewById(R.id.rePasswordRegister);
        Button btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailRegister.getText().toString();
                String password = passwordRegister.getText().toString();
                String repassword = rePasswordRegister.getText().toString();

                if(password.equals(repassword) == false){
                    Toast.makeText(RegisterActivity.this, "Nhập lại mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                    rePasswordRegister.setText("");
                    return;
                }
                if(password.length() < 8){
                    Toast.makeText(RegisterActivity.this, "Mật khẩu phải nhiều hơn 8 ký tự", Toast.LENGTH_SHORT).show();
                    passwordRegister.setText("");
                    rePasswordRegister.setText("");
                    return;
                }
                //tao tk
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isComplete()){
                            Log.i(TAG, "loi tai complete create user");
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        }
                        else{
                            Log.i(TAG, "loi tai "+task.getException());
                            Toast.makeText(RegisterActivity.this, "Đăng ký thật bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    //action da co tai khoan? dang nhap
    private void RegisterNow(){
        TextView registerNow = findViewById(R.id.loginNow);

        registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

            }
        });
    }
}