package com.example.bell;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper myDb;
    DatabaseHelper2 myDb2;

    TextView timeview,txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9,txt10,mainlogo;
    Button btnMF,btnSat,btnEdit,btnAnn;
    String s1,s2,s3,s4,s5,s6,s7,s8,s9,s10;

    int clickcount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);
        myDb2 = new DatabaseHelper2(this);

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
        mainlogo = (TextView)findViewById(R.id.mainlogo);


        btnMF = (Button)findViewById(R.id.btnSetMFTimings);
        btnSat = (Button)findViewById(R.id.btnSetSatTimings);
        btnEdit = (Button)findViewById(R.id.btnEdtTimings);
        btnAnn = (Button)findViewById(R.id.btnAnnouncement);
        btnAnn.setOnClickListener(this);


        Timer();
        EditTimingsClick();
        ShowPhoneDataMF();
        ShowPhoneDataSat();

        mainlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickcount=clickcount+1;
                if (clickcount==10){
                    Toast.makeText(getApplicationContext(),"clicked "+clickcount+" times",Toast.LENGTH_LONG).show();

                    AlertDialog.Builder mbuilder = new AlertDialog.Builder(MainActivity.this);
                    View mview = getLayoutInflater().inflate(R.layout.infodilog,null);

                    mbuilder.setView(mview);
                    AlertDialog dialog = mbuilder.create();
                    dialog.show();

                    clickcount = 0;
                }
            }
        });

    }

    private void ShowPhoneDataMF() {
        btnMF.setOnClickListener((View v) -> {
            Cursor res = myDb.getAllData();
            if (res.getCount() == 0){
                showMessage("Error", "Nothing Found!\nFirst click on 'Edit New Timings' and set Timings");
                return;
            }else {
                        s1 = res.getString(1);
                        s2 = res.getString(2);
                        s3 = res.getString(3);
                        s4 = res.getString(4);
                        s5 = res.getString(5);
                        s6 = res.getString(6);
                        s7 = res.getString(7);
                        s8 = res.getString(8);
                        s9 = res.getString(9);
                        s10 = res.getString(10);
            }
            SendLiteValueToNet();
        });
    }

    private void ShowPhoneDataSat() {
        btnSat.setOnClickListener((View v2) -> {
            Cursor res2 = myDb2.getAllData2();
            if (res2.getCount() == 0){
                showMessage("Error", "Nothing Found!\nFirst click on 'Edit New Timings' and set Timings");
                return;
            }else {
                s1 = res2.getString(1);
                s2 = res2.getString(2);
                s3 = res2.getString(3);
                s4 = res2.getString(4);
                s5 = res2.getString(5);
                s6 = res2.getString(6);
                s7 = res2.getString(7);
                s8 = res2.getString(8);
                s9 = res2.getString(9);
                s10 = res2.getString(10);
            }
            SendLiteValueToNet();
        });
    }


    private void SendLiteValueToNet() {

        //String URL = "http://192.168.43.152/bellapp/v1/getValues.php";
        String URL = "http://192.168.43.25/bellapp/v1/getValues.php";
        // progressDialog.setMessage("sending..");
        // progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressDialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(MainActivity.this,MainActivity.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("t1",s1);
                params.put("t2",s2);
                params.put("t3",s3);
                params.put("t4",s4);
                params.put("t5",s5);
                params.put("t6",s6);
                params.put("t7",s7);
                params.put("t8",s8);
                params.put("t9",s9);
                params.put("t10",s10);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
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
                                SetTimesToView();
                                timeview = (TextView)findViewById(R.id.timeview);
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                                String timeString = sdf.format(date);
                                timeview.setText(timeString);

                                //RingBell();

                            }
                        });
                    }
                }catch (InterruptedException e){}
            }
        };
        t.start();


    }



    private void RingBell() {

        //String string = 1;
        String currentTime = timeview.getText().toString();

        if (currentTime.equals(txt1.getText().toString().trim()))
        {Toast.makeText(this,"1st",Toast.LENGTH_LONG).show();
            //serialPort.write(String.getBytes());
            }

        if (currentTime.equals(txt2.getText().toString()))
        {Toast.makeText(this,"2nd",Toast.LENGTH_LONG).show();
            }


        if (currentTime.equals(txt3.getText().toString()))
        {Toast.makeText(this,"3rd",Toast.LENGTH_LONG).show();}


        if (currentTime.equals(txt4.getText().toString()))
        {Toast.makeText(this,"4th",Toast.LENGTH_LONG).show();}

        if (currentTime.equals(txt5.getText().toString()))
        {Toast.makeText(this,"5th",Toast.LENGTH_LONG).show();}

        if (currentTime.equals(txt6.getText().toString()))
        {Toast.makeText(this,"6th",Toast.LENGTH_LONG).show();}

        if (currentTime.equals(txt7.getText().toString()))
        {Toast.makeText(this,"7th",Toast.LENGTH_LONG).show();}

        if (currentTime.equals(txt8.getText().toString()))
        {Toast.makeText(this,"8th",Toast.LENGTH_LONG).show();}

        if (currentTime.equals(txt9.getText().toString()))
        {Toast.makeText(this,"6th",Toast.LENGTH_LONG).show();}

        if (currentTime.equals(txt10.getText().toString()))
        {Toast.makeText(this,"10th",Toast.LENGTH_LONG).show();}


    }


    private void SetTimesToView() {
        //String URL = "http://192.168.43.152/bellapp/includes/fetchTimesArray.php";
        String URL = "http://192.168.43.25/bellapp/includes/fetchTimesArray.php";

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


    private void EditTimingsClick() {
        btnEdit.setOnClickListener((View a)->{
            AlertDialog.Builder mbuilder = new AlertDialog.Builder(MainActivity.this);
            View mview = getLayoutInflater().inflate(R.layout.edit_options_dialog,null);
            TextView monfri = (TextView) mview.findViewById(R.id.edtMFView);
            TextView sat = (TextView) mview.findViewById(R.id.edtSatView);
            TextView newt = (TextView) mview.findViewById(R.id.edtNewView);

            monfri.setOnClickListener((View v) -> {

                Intent i = new Intent(this,MonFriTimings.class);
                startActivity(i);
            });

            sat.setOnClickListener((View v) ->{

                Intent i = new Intent(this,SatTimings.class);
                startActivity(i);
            });

            newt.setOnClickListener((View v) ->{

                Intent i = new Intent(this,NewTimings.class);
                startActivity(i);
            });
            mbuilder.setView(mview);
            AlertDialog dialog = mbuilder.create();
            dialog.show();
        });
    }

    @Override
    public void onClick(View view) {
        if (view == btnAnn)
            startActivity(new Intent(this,AnnouncementActivity.class));
    }
}
