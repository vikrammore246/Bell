package com.example.bell;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.net.URL;

import cz.msebera.android.httpclient.Header;

public class AnnouncementActivity extends AppCompatActivity implements View.OnClickListener {

    //String IPADDRESS = "192.168.43.132/";

    private Button btnStart, btnStop;
    private EditText edtAnn;
    String IpAddress;

    Databasehelper4 myDb4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);

        btnStart = (Button) findViewById(R.id.btnAnnStart);
        btnStop = (Button) findViewById(R.id.btnAnnStop);
        edtAnn = (EditText) findViewById(R.id.edtAnn);

        myDb4 = new Databasehelper4(this);

        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }

    private void SendNoticeToNode() {
        String notice = edtAnn.getText().toString();
        notice = notice.replaceAll(" ","_");
        getIpFromDatabase();
        String URL = "http://" + IpAddress + "/" + notice;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(getApplicationContext(),"Notice Sent",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), "onFailuer",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ClearNodeNotice() {
        getIpFromDatabase();
        String URL = "http://" + IpAddress + "/*-";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(getApplicationContext(),"Display Cleared",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), "onFailuer",Toast.LENGTH_LONG).show();
            }
        });
    }


    private void getIpFromDatabase(){
        Cursor res4 = myDb4.getAllData4();
        if (res4.getCount() == 0){
            showMessage("Error", "Ip Address not found!\nFirst insert Ip Address of NodeMCU in menu options,\nthen start using app.");
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
    }


    @Override
    public void onClick(View view) {
        if (view == btnStart) {
            SendNoticeToNode();
        }
        if (view == btnStop){
            ClearNodeNotice();
            edtAnn.setText("");
        }
    }

}
