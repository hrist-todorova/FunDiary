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
    public static final String ENTRY_NAME = "ENTRY_NAME";
    public static final String ENTRY_TIMESTAMP = "ENTRY_TIMESTAMP";
    public static final String ENTRY_NOTES = "ENTRY_NOTES";

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
        createMoviesTable();
        createTvShowsTable();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), AddNewRecord.class));
            }
        });

        getAllEntries();
    }

    private void createMoviesTable() {
        String sql = "CREATE TABLE IF NOT EXISTS movies (\n" +
                "    id INTEGER NOT NULL CONSTRAINT movies_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    title varchar(255) NOT NULL,\n" +
                "    timestamp datetime NOT NULL,\n" +
                "    notes varchar(255) NULL\n" +
                ");";

        myDatabase.execSQL(sql);
    }

    private void createTvShowsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS tv_shows (\n" +
                "    id INTEGER NOT NULL CONSTRAINT tv_shows_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    title varchar(255) NOT NULL,\n" +
                "    timestamp datetime NOT NULL,\n" +
                "    notes varchar(255) NULL\n" +
                ");";

        myDatabase.execSQL(sql);
    }

    private void getAllEntries() {
        myListView = findViewById(R.id.contentsListView);

        final ArrayList<String> contentList = new ArrayList<>();
        String sql = "SELECT title FROM " +
                "(SELECT title FROM tv_shows UNION " +
                "SELECT title FROM movies) AS entries ORDER BY title ASC";
        Cursor cursor = myDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                contentList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        listAdapter = new ArrayAdapter<>(this, R.layout.database_entry_view, contentList);
        myListView.setAdapter(listAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // query the database
                // send all fields as parameters

                Intent intent = new Intent(view.getContext(), DisplayEntry.class);
                intent.putExtra(ENTRY_NAME, contentList.get(i));
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
