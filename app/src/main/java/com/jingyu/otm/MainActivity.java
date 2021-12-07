package com.jingyu.otm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
//        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
//            @Override
//            public void onChanged(@Nullable List<Note> notes) {
//                //update RecyclerView
//                Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
//            }
//        });

    }
}