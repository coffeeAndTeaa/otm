package com.jingyu.otm.activity;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jingyu.otm.databinding.ActivityLoginBinding;
import com.jingyu.otm.db.User;
import com.jingyu.otm.repository.LoginRepository;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.userName.getText().toString();
                String password = binding.password.getText().toString();
                LoginRepository repo = new LoginRepository();
                if (name.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "please input your name and password", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        User user = repo.login(name, password);
                        if (user == null) {
                            Toast.makeText(getApplicationContext(),"user not exist please verify your input or register",Toast.LENGTH_SHORT).show();
                        } else {
                            Long userId = user.id_user;
                            Log.d("LoginActivity", userId.toString());
                            Log.d("LoginActivity", userId.toString());
                            Log.d(TAG, "onClick: ");
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class)
                                .putExtra("userId", userId)
                            );
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
    }
}