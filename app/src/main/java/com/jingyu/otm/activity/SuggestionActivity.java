package com.jingyu.otm.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.jingyu.otm.R;
import com.jingyu.otm.databinding.ActivitySuggestionBinding;
import com.jingyu.otm.repository.SuggestionRepository;

public class SuggestionActivity extends AppCompatActivity {
    ActivitySuggestionBinding binding;
    SuggestionRepository repository;
    Integer steps;
    Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySuggestionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("userId");
            //The key argument here must match that used in the other activity
        }
        repository = new SuggestionRepository();
        steps = repository.getStepsForToday();
        binding.steps.setText(steps.toString());
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SuggestionActivity.this, RunActivity.class)
                        .putExtra("userId", userId)
                );
            }
        });

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        Integer temp = 0;

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.level1:
                if (checked)
                    temp = steps / 2 + 100;
                    binding.target.setText("Target: " + temp.toString());
                    break;
            case R.id.level2:
                if (checked)
                    temp = steps + 50;
                    binding.target.setText("Target: " + temp.toString());
                    break;
            case R.id.level3:
                if (checked)
                    temp = steps * 2;
                    binding.target.setText("Target: " + temp.toString());
                    break;
        }
    }
}