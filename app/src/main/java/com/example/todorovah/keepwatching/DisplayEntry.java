package com.example.todorovah.keepwatching;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.content.Intent;

public class DisplayEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_entry);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String entryTitle = intent.getStringExtra(LoadEntries.ENTRY_TITLE);
        String timeStamp = intent.getStringExtra(LoadEntries.ENTRY_TIMESTAMP);
        String notes = intent.getStringExtra(LoadEntries.ENTRY_NOTES);
        return;
    }



}
