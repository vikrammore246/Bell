package com.example.bell;

import android.app.ProgressDialog;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class AnnouncementActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView annTimer;
    private Button btnStart,btnStop;
    private EditText edtAnn;
    private ProgressDialog progressDialog2;

    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);

        //CASTING
        annTimer = (TextView)findViewById(R.id.annTimer);
        btnStart = (Button)findViewById(R.id.btnAnnStart);
        btnStop = (Button)findViewById(R.id.btnAnnStop);
        edtAnn = (EditText)findViewById(R.id.edtAnn);

        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);

        progressDialog2 = new ProgressDialog(this);

        //AnnTimer();
    }


    public void Start(){
        //counter = counter + 1;

        String string = edtAnn.getText().toString() + "/";
        //String URL2 = "http://192.168.43.152/bellapp/v1/getString.php";
        String URL2 = "http://192.168.43.25/bellapp/v1/getString.php";
        progressDialog2.setMessage("sending...");
        progressDialog2.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog2.dismiss();
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
                        progressDialog2.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("string",string);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void Stop(){

        //counter = 0;
        String string = "";
        edtAnn.setText(string);
        //String URL2 = "http://192.168.43.152/bellapp/v1/getString.php";
        String URL2 = "http://192.168.43.25/bellapp/v1/getString.php";
        progressDialog2.setMessage("clearing..");
        progressDialog2.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog2.dismiss();
                        try {
                            JSONObject object = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), "Cleared", Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(MainActivity.this,MainActivity.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog2.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("string",string);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }




    private void AnnTimer(){
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
                                annTimer = (TextView)findViewById(R.id.annTimer);
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                                String timeString = sdf.format(date);
                                annTimer.setText(timeString);

                                if (counter == 1 || counter == 2){
                                    if (counter == 2){
                                        Stop();
                                    }else{
                                        counter = counter + 1;
                                    }
                                }

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
        if (view == btnStart){
            Start();
            edtAnn.setText("");
        }

        if (view == btnStop){
            Stop();
        }
    }
}
