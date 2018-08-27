package com.example.bell;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Recent_Notifications extends AppCompatActivity {

    DatabaseHelper myDb;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ListItem> listItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent__notifications);

        myDb = new DatabaseHelper(this);
        listItems = new ArrayList<>();
        listItems = myDb.getDataInList();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyAdapter(listItems, this);
        recyclerView.setAdapter(adapter);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.cleardatabasemenu){
            myDb.deleteAll();
            Toast.makeText(getApplicationContext(), "All Recent Notifications Cleared!", Toast.LENGTH_LONG).show();
            finish();
            startActivity(getIntent());
            return true;
        }

        return true;
    }




}
