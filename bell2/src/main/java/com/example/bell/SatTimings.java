package com.example.bell;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class SatTimings extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener {
    DatabaseHelper2 myDb2;
    Spinner sh1,sh2,sh3,sh4,sh5,sh6,sh7,sh8,sh9,sh10,sm1,sm2,sm3,sm4,sm5,sm6,sm7,sm8,sm9,sm10;
    Button btnSetSatTime,btnSatCancle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sat_timings);

        myDb2 = new DatabaseHelper2(this);

        //CASTING
        sh1 = (Spinner)findViewById(R.id.sh1);
        sh2 = (Spinner)findViewById(R.id.sh2);
        sh3 = (Spinner)findViewById(R.id.sh3);
        sh4 = (Spinner)findViewById(R.id.sh4);
        sh5 = (Spinner)findViewById(R.id.sh5);
        sh6 = (Spinner)findViewById(R.id.sh6);
        sh7 = (Spinner)findViewById(R.id.sh7);
        sh8 = (Spinner)findViewById(R.id.sh8);
        sh9 = (Spinner)findViewById(R.id.sh9);
        sh10 = (Spinner)findViewById(R.id.sh10);

        sm1 = (Spinner)findViewById(R.id.sm1);
        sm2 = (Spinner)findViewById(R.id.sm2);
        sm3 = (Spinner)findViewById(R.id.sm3);
        sm4 = (Spinner)findViewById(R.id.sm4);
        sm5 = (Spinner)findViewById(R.id.sm5);
        sm6 = (Spinner)findViewById(R.id.sm6);
        sm7 = (Spinner)findViewById(R.id.sm7);
        sm8 = (Spinner)findViewById(R.id.sm8);
        sm9 = (Spinner)findViewById(R.id.sm9);
        sm10 = (Spinner)findViewById(R.id.sm10);


        btnSetSatTime = (Button)findViewById(R.id.btnDoneSatEditing);
        btnSatCancle = (Button)findViewById(R.id.btnSatCancle);
        btnSatCancle.setOnClickListener(this);
        AddData2();
    }

    public void AddData2(){

        btnSetSatTime.setOnClickListener((View v) -> {
            Cursor res = myDb2.getAllData2();

            if (res.getCount() == 0){
                boolean isInserted2 = myDb2.insertData2(sh1.getSelectedItem().toString()+":"+sm1.getSelectedItem().toString()+":00",
                        sh2.getSelectedItem().toString()+":"+sm2.getSelectedItem().toString()+":00",
                        sh3.getSelectedItem().toString()+":"+sm3.getSelectedItem().toString()+":00",
                        sh4.getSelectedItem().toString()+":"+sm4.getSelectedItem().toString()+":00",
                        sh5.getSelectedItem().toString()+":"+sm5.getSelectedItem().toString()+":00",
                        sh6.getSelectedItem().toString()+":"+sm6.getSelectedItem().toString()+":00",
                        sh7.getSelectedItem().toString()+":"+sm7.getSelectedItem().toString()+":00",
                        sh8.getSelectedItem().toString()+":"+sm8.getSelectedItem().toString()+":00",
                        sh9.getSelectedItem().toString()+":"+sm9.getSelectedItem().toString()+":00",
                        sh10.getSelectedItem().toString()+":"+sm10.getSelectedItem().toString()+":00");

                if (isInserted2 == true) {
                    Toast.makeText(SatTimings.this, "Timings Inserted successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this, MainActivity.class));
                }
                else
                    Toast.makeText(SatTimings.this, "Some eRror occurred",Toast.LENGTH_LONG).show();

                return;
            }else {
                boolean isUpdate2 = myDb2.updateData2("1",sh1.getSelectedItem().toString()+":"+sm1.getSelectedItem().toString()+":00",
                        sh2.getSelectedItem().toString()+":"+sm2.getSelectedItem().toString()+":00",
                        sh3.getSelectedItem().toString()+":"+sm3.getSelectedItem().toString()+":00",
                        sh4.getSelectedItem().toString()+":"+sm4.getSelectedItem().toString()+":00",
                        sh5.getSelectedItem().toString()+":"+sm5.getSelectedItem().toString()+":00",
                        sh6.getSelectedItem().toString()+":"+sm6.getSelectedItem().toString()+":00",
                        sh7.getSelectedItem().toString()+":"+sm7.getSelectedItem().toString()+":00",
                        sh8.getSelectedItem().toString()+":"+sm8.getSelectedItem().toString()+":00",
                        sh9.getSelectedItem().toString()+":"+sm9.getSelectedItem().toString()+":00",
                        sh10.getSelectedItem().toString()+":"+sm10.getSelectedItem().toString()+":00");

                if (isUpdate2 == true) {
                    Toast.makeText(SatTimings.this, "Timings Saved successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this, MainActivity.class));
                }
                else
                    Toast.makeText(SatTimings.this, "Some eRroR occurred",Toast.LENGTH_LONG).show();
            }

        });




    }


    @Override
    public void onClick(View view) {
        if (view == btnSatCancle){
            startActivity(new Intent(this,MainActivity.class));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
