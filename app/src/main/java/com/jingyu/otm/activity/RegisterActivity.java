package com.jingyu.otm.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jingyu.otm.databinding.ActivityRegisterBinding;
import com.jingyu.otm.db.User;
import com.jingyu.otm.repository.LoginRepository;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    LoginRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginRepository repo = new LoginRepository();
                if (repo.checkNameExist(binding.userName.getText().toString()) != null) {
                    Log.d("register", "invalid username ");
                    Toast.makeText(getApplicationContext(), "please input another username", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = new User(
                        binding.userName.getText().toString(),
                        Double.parseDouble(binding.userHeight.getText().toString()),
                        Integer.parseInt(binding.userAge.getText().toString()),
                        Double.parseDouble(binding.userWeight.getText().toString()),
                        binding.password.getText().toString(),
                        binding.gender.getText().toString()
                );

                if (validateInput(user)) {
                    repo.insert(user);
                    Toast.makeText(getApplicationContext(), "User registered!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class)
                    );
                } else {
                    Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private Boolean validateInput(User user){
        if (user.password.isEmpty() || user.name.isEmpty() || user.age == null || user.weight == null || user.height == null) {
            return false;
        }
        return true;
    }
}