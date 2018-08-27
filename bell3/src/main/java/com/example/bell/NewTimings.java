package com.example.bell;

import android.content.Intent;
import android.database.Cursor;
import android.preference.PreferenceActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class NewTimings extends AppCompatActivity implements View.OnClickListener{

    //String IPADDRESS = "192.168.43.132/";

    Spinner nh1,nh2,nh3,nh4,nh5,nh6,nh7,nh8,nh9,nh10,nm1,nm2,nm3,nm4,nm5,nm6,nm7,nm8,nm9,nm10;
    Button btnSetNewTime,btnCancle;
    String s1,s2,s3,s4,s5,s6,s7,s8,s9,s10;
    String IpAddress;

    DatabaseHelper3 myDb3;
    Databasehelper4 myDb4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_timings);

        myDb3 = new DatabaseHelper3(this);
        myDb4 = new Databasehelper4(this);

        //CASTING
        nh1 = (Spinner)findViewById(R.id.nh1);
        nh2 = (Spinner)findViewById(R.id.nh2);
        nh3 = (Spinner)findViewById(R.id.nh3);
        nh4 = (Spinner)findViewById(R.id.nh4);
        nh5 = (Spinner)findViewById(R.id.nh5);
        nh6 = (Spinner)findViewById(R.id.nh6);
        nh7 = (Spinner)findViewById(R.id.nh7);
        nh8 = (Spinner)findViewById(R.id.nh8);
        nh9 = (Spinner)findViewById(R.id.nh9);
        nh10 = (Spinner)findViewById(R.id.nh10);

        nm1 = (Spinner)findViewById(R.id.nm1);
        nm2 = (Spinner)findViewById(R.id.nm2);
        nm3 = (Spinner)findViewById(R.id.nm3);
        nm4 = (Spinner)findViewById(R.id.nm4);
        nm5 = (Spinner)findViewById(R.id.nm5);
        nm6 = (Spinner)findViewById(R.id.nm6);
        nm7 = (Spinner)findViewById(R.id.nm7);
        nm8 = (Spinner)findViewById(R.id.nm8);
        nm9 = (Spinner)findViewById(R.id.nm9);
        nm10 = (Spinner)findViewById(R.id.nm10);

        btnCancle = (Button)findViewById(R.id.btnCancle);
        btnCancle.setOnClickListener(this);
        btnSetNewTime = (Button)findViewById(R.id.btnDoneNewEditing);
        btnSetNewTime.setOnClickListener(this);
    }


    private void SendValue() {
        s1 = nh1.getSelectedItem().toString()+":"+nm1.getSelectedItem().toString()+":00";
        s2 = nh2.getSelectedItem().toString()+":"+nm2.getSelectedItem().toString()+":00";
        s3 = nh3.getSelectedItem().toString()+":"+nm3.getSelectedItem().toString()+":00";
        s4 = nh4.getSelectedItem().toString()+":"+nm4.getSelectedItem().toString()+":00";
        s5 = nh5.getSelectedItem().toString()+":"+nm5.getSelectedItem().toString()+":00";
        s6 = nh6.getSelectedItem().toString()+":"+nm6.getSelectedItem().toString()+":00";
        s7 = nh7.getSelectedItem().toString()+":"+nm7.getSelectedItem().toString()+":00";
        s8 = nh8.getSelectedItem().toString()+":"+nm8.getSelectedItem().toString()+":00";
        s9 = nh9.getSelectedItem().toString()+":"+nm9.getSelectedItem().toString()+":00";
        s10 = nh10.getSelectedItem().toString()+":"+nm10.getSelectedItem().toString()+":00";

        //String URL = "http://192.168.43.152/bellapp/v1/getValues.php";
        //String URL = "http://192.168.43.25/bellapp/v1/getValues.php";
        //getIpFromDatabase();

        Cursor res4 = myDb4.getAllData4();
        if (res4.getCount() == 0){
            showMessage("Error", "Ip Address not found!\nFirst insert Ip Address of NodeMCU in Menu options\nfrom HomeScreen");
            return;
        }else {
            IpAddress = res4.getString(1);


            String URL = "http://" + IpAddress + "/" + s1 + s2 + s3 + s4 + s5 + s6 + s7 + s8 + s9 + s10;

            AsyncHttpClient client = new AsyncHttpClient();
            client.get(URL, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Toast.makeText(getApplicationContext(), "Time sent", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(getApplicationContext(), "onFailuer", Toast.LENGTH_LONG).show();
                }
            });


            AddToThirdDatabase1();

            finish();
            startActivity(new Intent(this, MainActivity.class));

        }

    }


    private void AddToThirdDatabase1(){
        Cursor res = myDb3.getAllData3();

        if (res.getCount() == 0){
            boolean isInserted = myDb3.insertData3(s1,s2,s3,s4,s5,s6,s7,s8,s9,s10);

            if (isInserted == true) {
                //Toast.makeText(MainActivity.this, "Timings Inserted successfully", Toast.LENGTH_LONG).show();
                //startActivity(new Intent(this, MainActivity.class));

            }
            else
                Toast.makeText(NewTimings.this, "Some eRror occurred while getting timings",Toast.LENGTH_LONG).show();

            return;
        }else {
            boolean isUpdate = myDb3.updateData3("1",s1,s2,s3,s4,s5,s6,s7,s8,s9,s10);

            if (isUpdate == true) {
                //Toast.makeText(MainActivity.this, "Timings Saved successfully", Toast.LENGTH_LONG).show();
                //startActivity(new Intent(this, MainActivity.class));

            }
            else
                Toast.makeText(NewTimings.this, "Some eRroR occurred while getting timings",Toast.LENGTH_LONG).show();
        }


    }

    private void getIpFromDatabase(){
        Cursor res4 = myDb4.getAllData4();
        if (res4.getCount() == 0){
            showMessage("Error", "Ip Address not found!\nFirst insert Ip Address of NodeMCU in Menu options\nfrom HomeScreen");
            return;
        }else {
            IpAddress = res4.getString(1);
        }
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


    @Override
    public void onBackPressed(){
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }


    @Override
    public void onClick(View view) {
        if (view == btnSetNewTime){
            SendValue();
        }

        if (view == btnCancle){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
    }
}
