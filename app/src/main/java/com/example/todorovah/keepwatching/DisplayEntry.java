package com.example.todorovah.keepwatching;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayEntry extends AppCompatActivity implements View.OnClickListener{

    TextView printTitle, printTimestamp, printNotes;
    Button deleteButton;
    SQLiteDatabase myDatabase;
    Integer currentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_entry);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myDatabase = openOrCreateDatabase(LoadEntries.DATABASE_NAME, MODE_PRIVATE, null);

        Intent intent = getIntent();
        String entryTitle = intent.getStringExtra(LoadEntries.ENTRY_TITLE);
        String timeStamp = intent.getStringExtra(LoadEntries.ENTRY_TIMESTAMP);
        String notes = intent.getStringExtra(LoadEntries.ENTRY_NOTES);
        currentId = intent.getIntExtra(LoadEntries.ENTRY_ID, 0);

        printTitle = findViewById(R.id.printTitile);
        printTitle.setText(entryTitle);

        printTimestamp = findViewById(R.id.printTimestamp);
        printTimestamp.setText("Added on " + timeStamp);

        printNotes = findViewById(R.id.printNotes);
        printNotes.setText(notes);

        deleteButton = findViewById(R.id.buttonDeleteEntry);
        deleteButton.setOnClickListener(this);

        return;
    }

    @Override
    public void onClick(View view) {
        String sql = "DELETE FROM entry WHERE id = ?";

        myDatabase.execSQL(sql, new String[]{currentId.toString()});
        Toast.makeText(this, "Deleted!", Toast.LENGTH_SHORT).show();
    }



}
