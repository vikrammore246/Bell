package com.example.vikram.bell;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    TextView timeview,txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9,txt10,show1,show2;
    Button btnShow,btnstart,btnstop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnShow = (Button)findViewById(R.id.btnSetTimings);
        btnShow.setOnClickListener(this);

        txt1 = (TextView)findViewById(R.id.timet1);
        txt2 = (TextView)findViewById(R.id.timet2);
        txt3 = (TextView)findViewById(R.id.timet3);
        txt4 = (TextView)findViewById(R.id.timet4);
        txt5 = (TextView)findViewById(R.id.timet5);
        txt6 = (TextView)findViewById(R.id.timet6);
        txt7 = (TextView)findViewById(R.id.timet7);
        txt8 = (TextView)findViewById(R.id.timet8);
        txt9 = (TextView)findViewById(R.id.timet9);
        txt10 = (TextView)findViewById(R.id.timet10);
        show1 = (TextView)findViewById(R.id.show1);
        show2 = (TextView)findViewById(R.id.show2);

//        btnstart = (Button)findViewById(R.id.btnstart);
//        btnstop = (Button)findViewById(R.id.btnstop);
//
//        btnstart.setOnClickListener(this);
//        btnstop.setOnClickListener(this);


        Timer();

        SetTimesToView();
    }



    private void RingBell() {

        //String string = 1;

        if (timeview.getText().toString().equals(txt1.getText().toString()))
        {Toast.makeText(this,"1st",Toast.LENGTH_LONG).show();
            //serialPort.write(String.getBytes());
        show1.setText("done1");}

        if (timeview.getText().toString().equals(txt2.getText().toString()))
        {Toast.makeText(this,"2nd",Toast.LENGTH_LONG).show();
        show2.setText("done2");}

        if (timeview.getText().toString().equals(txt3.getText().toString()))
        {Toast.makeText(this,"3rd",Toast.LENGTH_LONG).show();}

        if (timeview.getText().toString().equals(txt4.getText().toString()))
        {Toast.makeText(this,"4th",Toast.LENGTH_LONG).show();}

    }

    private void SetTimesToView() {
        String URL = "http://192.168.43.152/bellapp/includes/fetchTimesArray.php";

        StringRequest stringRequest = new StringRequest(URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showDetails(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showDetails(String responce){
        String t1 = "";
        String t2 = "";
        String t3 = "";
        String t4 = "";
        String t5 = "";
        String t6 = "";
        String t7 = "";
        String t8 = "";
        String t9 = "";
        String t10 = "";

        try {
            JSONObject jsonObject = new JSONObject(responce);
            JSONArray details = jsonObject.getJSONArray("times");
            JSONObject driveDetails = details.getJSONObject(0);
            t1 = driveDetails.getString("t1");
            t2 = driveDetails.getString("t2");
            t3 = driveDetails.getString("t3");
            t4 = driveDetails.getString("t4");
            t5 = driveDetails.getString("t5");
            t6 = driveDetails.getString("t6");
            t7 = driveDetails.getString("t7");
            t8 = driveDetails.getString("t8");
            t9 = driveDetails.getString("t9");
            t10 = driveDetails.getString("t10");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        txt1.setText(t1);
        txt2.setText(t2);
        txt3.setText(t3);
        txt4.setText(t4);
        txt5.setText(t5);
        txt6.setText(t6);
        txt7.setText(t7);
        txt8.setText(t8);
        txt9.setText(t9);
        txt10.setText(t10);
    }

    private void Timer(){
        //TIMER

        Thread t = new Thread(){
            @Override
            public void run(){
                try {
                    while (!isInterrupted()){
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                timeview = (TextView)findViewById(R.id.timeview);
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                                String timeString = sdf.format(date);
                                timeview.setText(timeString);
//
//                                Intent timer = new Intent(MainActivity.this,BackgroundServices.class);
//                                timer.putExtra("clock",timeview.getText().toString());
//                                startActivity(timer);

                                RingBell();

                            }
                        });
                    }
                }catch (InterruptedException e){}
            }
        };
        t.start();


    }






    @Override
    public void onClick(View view) {
        if (view == btnShow){
            startActivity(new Intent(MainActivity.this,EditTimings.class));
            //finish();
        }




//        if (view == btnstart){
//
//            Intent i1 = new Intent(view.getContext(),BackgroundServices.class);
//            i1.putExtra("txt1",txt1.getText().toString());
//            view.getContext().startActivity(i1);
//
//            Intent i2 = new Intent(view.getContext(),BackgroundServices.class);
//            i1.putExtra("txt1",txt2.getText().toString());
//            view.getContext().startActivity(i2);
//
//            Intent i3 = new Intent(view.getContext(),BackgroundServices.class);
//            i1.putExtra("txt1",txt3.getText().toString());
//            view.getContext().startActivity(i3);
//
//            Intent i4 = new Intent(view.getContext(),BackgroundServices.class);
//            i1.putExtra("txt1",txt4.getText().toString());
//            view.getContext().startActivity(i4);
//
//            startService(new Intent(this,BackgroundServices.class));
//        }
//        if (view == btnstop){
//            stopService(new Intent(this,BackgroundServices.class));
//        }




    }
}
