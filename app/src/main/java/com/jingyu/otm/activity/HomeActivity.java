package com.jingyu.otm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.jingyu.otm.R;
import com.jingyu.otm.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
//    private ActivityHomeBinding binding;
    private NavController navController;
    private Long userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController);

        // get the userId passed in from the login activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("userId");
            //The key argument here must match that used in the other activity
        }
    }

    public Long giveMeUserId() {
        return userId;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }
}