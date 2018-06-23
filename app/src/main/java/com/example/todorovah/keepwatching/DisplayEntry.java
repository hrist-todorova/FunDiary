package com.example.todorovah.keepwatching;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import android.util.Log;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


public class DisplayEntry extends AppCompatActivity implements View.OnClickListener{

    TextView printTitle, printTimestamp, printNotes, genre, plot;
    Button deleteButton;
    SQLiteDatabase myDatabase;
    Integer currentId;
    ImageView imageView;

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

        genre = findViewById(R.id.genreID);
        imageView = findViewById(R.id.posterID);
        plot = findViewById(R.id.plotID);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String title = entryTitle.replace(" ", "+");
        String URL = "http://www.omdbapi.com/?apikey=ea938712&r=json&t=" + title;

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        Entry entry =  gson.fromJson(response.toString(), Entry.class);
                        genre.setText(entry.Genre);
                        plot.setText(entry.Plot);
                        loadImageFromURL(entry.Poster);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("REST response", error.toString());
                    }
                }
        );

        requestQueue.add(objectRequest);


        return;
    }

    private void loadImageFromURL(String url) {
        Picasso.with(this).load(url).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imageView, new com.squareup.picasso.Callback(){
            @Override
            public void onSuccess() {

            }
            @Override
            public void onError() {

            }
        });
    }


    @Override
    public void onClick(View view) {
        String sql = "DELETE FROM entry WHERE id = ?";

        myDatabase.execSQL(sql, new String[]{currentId.toString()});
        Toast.makeText(this, "Deleted!", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

}

class Entry {
    String Genre;
    String Poster;
    String Plot;
}