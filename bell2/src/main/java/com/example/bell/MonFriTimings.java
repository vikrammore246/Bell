package com.example.bell;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MonFriTimings extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener {
    DatabaseHelper myDb;
    Spinner mh1,mh2,mh3,mh4,mh5,mh6,mh7,mh8,mh9,mh10,mm1,mm2,mm3,mm4,mm5,mm6,mm7,mm8,mm9,mm10;
    Button btnSetMFTime,btnCancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_fri_timings);

        myDb = new DatabaseHelper(this);

        //CASTING
        mh1 = (Spinner)findViewById(R.id.mh1);
        mh2 = (Spinner)findViewById(R.id.mh2);
        mh3 = (Spinner)findViewById(R.id.mh3);
        mh4 = (Spinner)findViewById(R.id.mh4);
        mh5 = (Spinner)findViewById(R.id.mh5);
        mh6 = (Spinner)findViewById(R.id.mh6);
        mh7 = (Spinner)findViewById(R.id.mh7);
        mh8 = (Spinner)findViewById(R.id.mh8);
        mh9 = (Spinner)findViewById(R.id.mh9);
        mh10 = (Spinner)findViewById(R.id.mh10);

        mm1 = (Spinner)findViewById(R.id.mm1);
        mm2 = (Spinner)findViewById(R.id.mm2);
        mm3 = (Spinner)findViewById(R.id.mm3);
        mm4 = (Spinner)findViewById(R.id.mm4);
        mm5 = (Spinner)findViewById(R.id.mm5);
        mm6 = (Spinner)findViewById(R.id.mm6);
        mm7 = (Spinner)findViewById(R.id.mm7);
        mm8 = (Spinner)findViewById(R.id.mm8);
        mm9 = (Spinner)findViewById(R.id.mm9);
        mm10 = (Spinner)findViewById(R.id.mm10);

        btnSetMFTime = (Button)findViewById(R.id.btnDoneMFEditing);
        btnCancle = (Button)findViewById(R.id.btnMFCancle);
        btnCancle.setOnClickListener(this);
        AddData();
    }

    public void AddData(){

        btnSetMFTime.setOnClickListener((View v) -> {
        Cursor res = myDb.getAllData();

        if (res.getCount() == 0){
            boolean isInserted = myDb.insertData(mh1.getSelectedItem().toString()+":"+mm1.getSelectedItem().toString()+":00",
                    mh2.getSelectedItem().toString()+":"+mm2.getSelectedItem().toString()+":00",
                    mh3.getSelectedItem().toString()+":"+mm3.getSelectedItem().toString()+":00",
                    mh4.getSelectedItem().toString()+":"+mm4.getSelectedItem().toString()+":00",
                    mh5.getSelectedItem().toString()+":"+mm5.getSelectedItem().toString()+":00",
                    mh6.getSelectedItem().toString()+":"+mm6.getSelectedItem().toString()+":00",
                    mh7.getSelectedItem().toString()+":"+mm7.getSelectedItem().toString()+":00",
                    mh8.getSelectedItem().toString()+":"+mm8.getSelectedItem().toString()+":00",
                    mh9.getSelectedItem().toString()+":"+mm9.getSelectedItem().toString()+":00",
                    mh10.getSelectedItem().toString()+":"+mm10.getSelectedItem().toString()+":00");

            if (isInserted == true) {
                Toast.makeText(MonFriTimings.this, "Timings Inserted successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, MainActivity.class));
            }
            else
                Toast.makeText(MonFriTimings.this, "Some eRror occurred",Toast.LENGTH_LONG).show();

            return;
        }else {
            boolean isUpdate = myDb.updateData("1",mh1.getSelectedItem().toString()+":"+mm1.getSelectedItem().toString()+":00",
                    mh2.getSelectedItem().toString()+":"+mm2.getSelectedItem().toString()+":00",
                    mh3.getSelectedItem().toString()+":"+mm3.getSelectedItem().toString()+":00",
                    mh4.getSelectedItem().toString()+":"+mm4.getSelectedItem().toString()+":00",
                    mh5.getSelectedItem().toString()+":"+mm5.getSelectedItem().toString()+":00",
                    mh6.getSelectedItem().toString()+":"+mm6.getSelectedItem().toString()+":00",
                    mh7.getSelectedItem().toString()+":"+mm7.getSelectedItem().toString()+":00",
                    mh8.getSelectedItem().toString()+":"+mm8.getSelectedItem().toString()+":00",
                    mh9.getSelectedItem().toString()+":"+mm9.getSelectedItem().toString()+":00",
                    mh10.getSelectedItem().toString()+":"+mm10.getSelectedItem().toString()+":00");

            if (isUpdate == true) {
                Toast.makeText(MonFriTimings.this, "Timings Saved successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, MainActivity.class));
            }
            else
                Toast.makeText(MonFriTimings.this, "Some eRroR occurred",Toast.LENGTH_LONG).show();
        }

        });




    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        if (view == btnCancle){
            startActivity(new Intent(this,MainActivity.class));
        }
    }


}
