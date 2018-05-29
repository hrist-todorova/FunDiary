package com.example.todorovah.keepwatching;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.RadioGroup;

public class AddNewRecord extends AppCompatActivity implements View.OnClickListener {

    Button saveButton;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_record);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);

        //let movie be default choice
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.check(R.id.movieButton);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        if(radioButtonId == R.id.movieButton){
            Toast.makeText(this, "Movie saved!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "TV show saved!", Toast.LENGTH_SHORT).show();

        }
    }


}
