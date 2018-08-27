package com.example.bell;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class EditIpActivity extends AppCompatActivity {

    EditText edtIp;
    TextView currentip;

    Databasehelper4 myDb4;
    String IP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ip);

        myDb4 = new Databasehelper4(this);

        edtIp = (EditText) findViewById(R.id.edtip);
        Button submitIp = (Button) findViewById(R.id.submitip);
        currentip = (TextView) findViewById(R.id.currentip);

        //currentip.setText(IP);
        getIpFromDatabase();

        submitIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IP = edtIp.getText().toString().trim();
                AddToDatabase();
            }
        });
    }

    private void AddToDatabase(){
        Cursor res = myDb4.getAllData4();

        if (res.getCount() == 0){
            boolean isInserted = myDb4.insertData4(IP);

            if (isInserted == true) {
                finish();
                startActivity(new Intent(this,MainActivity.class));
            }
            else
                Toast.makeText(EditIpActivity.this, "Some eRror occurred while inserting Ip",Toast.LENGTH_LONG).show();

            return;
        }else {
            boolean isUpdate = myDb4.updateData4("1",IP);

            if (isUpdate == true) {
                finish();
                startActivity(new Intent(this,MainActivity.class));
            }
            else
                Toast.makeText(EditIpActivity.this, "Some eRroR occurred while inserting Ip",Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onBackPressed(){
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }


    private void getIpFromDatabase(){
        Cursor res4 = myDb4.getAllData4();
        if (res4.getCount() == 0){
            currentip.setText("Empty");
            return;
        }else {
            currentip.setText(res4.getString(1));
            //currentip = res4.getString(1);
        }
    }

}
