package com.example.bell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
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

import java.util.HashMap;
import java.util.Map;

public class NewTimings extends AppCompatActivity implements View.OnClickListener {
    Spinner nh1,nh2,nh3,nh4,nh5,nh6,nh7,nh8,nh9,nh10,nm1,nm2,nm3,nm4,nm5,nm6,nm7,nm8,nm9,nm10;
    Button btnSetNewTime,btnCancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_timings);

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
        final String nt1 = nh1.getSelectedItem().toString()+":"+nm1.getSelectedItem().toString()+":00";
        final String nt2 = nh2.getSelectedItem().toString()+":"+nm2.getSelectedItem().toString()+":00";
        final String nt3 = nh3.getSelectedItem().toString()+":"+nm3.getSelectedItem().toString()+":00";
        final String nt4 = nh4.getSelectedItem().toString()+":"+nm4.getSelectedItem().toString()+":00";
        final String nt5 = nh5.getSelectedItem().toString()+":"+nm5.getSelectedItem().toString()+":00";
        final String nt6 = nh6.getSelectedItem().toString()+":"+nm6.getSelectedItem().toString()+":00";
        final String nt7 = nh7.getSelectedItem().toString()+":"+nm7.getSelectedItem().toString()+":00";
        final String nt8 = nh8.getSelectedItem().toString()+":"+nm8.getSelectedItem().toString()+":00";
        final String nt9 = nh9.getSelectedItem().toString()+":"+nm9.getSelectedItem().toString()+":00";
        final String nt10 = nh10.getSelectedItem().toString()+":"+nm10.getSelectedItem().toString()+":00";

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
                            startActivity(new Intent(NewTimings.this,MainActivity.class));
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
                params.put("t1",nt1);
                params.put("t2",nt2);
                params.put("t3",nt3);
                params.put("t4",nt4);
                params.put("t5",nt5);
                params.put("t6",nt6);
                params.put("t7",nt7);
                params.put("t8",nt8);
                params.put("t9",nt9);
                params.put("t10",nt10);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onClick(View view) {
        if (view == btnSetNewTime){
            SendValue();
        }

        if (view == btnCancle){
            startActivity(new Intent(this,MainActivity.class));
        }

    }
}
