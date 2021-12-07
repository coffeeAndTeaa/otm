package com.jingyu.otm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jingyu.otm.databinding.ActivityRegisterBinding;
import com.jingyu.otm.db.RunDao;
import com.jingyu.otm.db.RunDataBase;
import com.jingyu.otm.db.User;
import com.jingyu.otm.db.UserDao;
import com.jingyu.otm.repository.LoginRepository;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(
                        binding.userName.getText().toString(),
                        Double.parseDouble(binding.userHeight.getText().toString()),
                        Integer.parseInt(binding.userAge.getText().toString()),
                        Double.parseDouble(binding.userHeight.getText().toString()),
                        binding.password.getText().toString()
                );
                
                if (validateInput(user)) {
//                    RunDataBase db = OtmApplication.getDataBase();
//                    UserDao userDao = db.userDao();
//                    userDao.insertUser(user);
                    LoginRepository repo = new LoginRepository();
                    repo.insert(user);
                    Toast.makeText(getApplicationContext(), "User registered!", Toast.LENGTH_SHORT).show();

                    // Do insert operation
//                    RunDataBase db = RunDataBase.getInstance(getApplicationContext());
//                    UserDao userDao = db.userDao();
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            // register the user
//                            userDao.insertUser(user);
//                            runOnUiThread(
//                                    new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            Toast.makeText(getApplicationContext(), "User registered!", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                            );
//                        }
//                    }).start();
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