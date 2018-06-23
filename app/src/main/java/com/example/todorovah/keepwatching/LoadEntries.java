package com.example.todorovah.keepwatching;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import android.widget.ListView;

public class LoadEntries extends AppCompatActivity {

    public static final String DATABASE_NAME = "notes";
    public static final String ENTRY_TITLE = "ENTRY_TITLE";
    public static final String ENTRY_TIMESTAMP = "ENTRY_TIMESTAMP";
    public static final String ENTRY_NOTES = "ENTRY_NOTES";
    public static final String ENTRY_ID = "ENTRY_ID";

    SQLiteDatabase myDatabase;
    ListView myListView;
    ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_entries);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        createEntryTable();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), AddNewRecord.class));
            }
        });

        getAllEntries();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllEntries();
    }

    private void createEntryTable() {
        String sql = "CREATE TABLE IF NOT EXISTS entry (\n" +
                "    id INTEGER NOT NULL CONSTRAINT movies_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    title varchar(255) NOT NULL,\n" +
                "    is_movie BOOL NOT NULL,\n" +
                "    timestamp datetime NOT NULL,\n" +
                "    notes varchar(255) NULL\n" +
                ");";

        myDatabase.execSQL(sql);
    }

    private void getAllEntries() {
        myListView = findViewById(R.id.contentsListView);

        final ArrayList<Integer> idsList = new ArrayList<>();
        final ArrayList<String> titlesList = new ArrayList<>();
        final ArrayList<String> timestampList = new ArrayList<>();
        final ArrayList<String> notesList = new ArrayList<>();
        String sql = "SELECT id, title, timestamp, notes FROM entry ORDER BY id DESC";
        Cursor cursor = myDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                idsList.add(cursor.getInt(0));
                titlesList.add(cursor.getString(1));
                timestampList.add(cursor.getString(2));
                notesList.add(cursor.getString(3));
            } while (cursor.moveToNext());
        }

        listAdapter = new ArrayAdapter<>(this, R.layout.database_entry_view, titlesList);
        myListView.setAdapter(listAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), DisplayEntry.class);
                intent.putExtra(ENTRY_TITLE, titlesList.get(i));
                intent.putExtra(ENTRY_TIMESTAMP, timestampList.get(i));
                intent.putExtra(ENTRY_NOTES, notesList.get(i));
                intent.putExtra(ENTRY_ID, idsList.get(i));
                startActivity(intent);
                return;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_load_entries, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
