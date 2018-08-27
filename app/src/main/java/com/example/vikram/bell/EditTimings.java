package com.example.vikram.bell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class EditTimings extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    Spinner h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,m1,m2,m3,m4,m5,m6,m7,m8,m9,m10;
    Button btnSetTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_timings);


        //CASTING
        h1 = (Spinner)findViewById(R.id.h1);
        h2 = (Spinner)findViewById(R.id.h2);
        h3 = (Spinner)findViewById(R.id.h3);
        h4 = (Spinner)findViewById(R.id.h4);
        h5 = (Spinner)findViewById(R.id.h5);
        h6 = (Spinner)findViewById(R.id.h6);
        h7 = (Spinner)findViewById(R.id.h7);
        h8 = (Spinner)findViewById(R.id.h8);
        h9 = (Spinner)findViewById(R.id.h9);
        h10 = (Spinner)findViewById(R.id.h10);

        m1 = (Spinner)findViewById(R.id.m1);
        m2 = (Spinner)findViewById(R.id.m2);
        m3 = (Spinner)findViewById(R.id.m3);
        m4 = (Spinner)findViewById(R.id.m4);
        m5 = (Spinner)findViewById(R.id.m5);
        m6 = (Spinner)findViewById(R.id.m6);
        m7 = (Spinner)findViewById(R.id.m7);
        m8 = (Spinner)findViewById(R.id.m8);
        m9 = (Spinner)findViewById(R.id.m9);
        m10 = (Spinner)findViewById(R.id.m10);

        btnSetTime = (Button)findViewById(R.id.btnDoneEditing);
        btnSetTime.setOnClickListener(this);
       // AddData();
    }


//    public void AddData(){
//        btnSetTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               boolean isInserted = myDb.insertData(h1.getSelectedItem().toString()+":"+m1.getSelectedItem().toString()+":00",
//                        h2.getSelectedItem().toString()+":"+m2.getSelectedItem().toString()+":00",
//                        h3.getSelectedItem().toString()+":"+m3.getSelectedItem().toString()+":00",
//                        h4.getSelectedItem().toString()+":"+m4.getSelectedItem().toString()+":00",
//                        h5.getSelectedItem().toString()+":"+m5.getSelectedItem().toString()+":00",
//                        h6.getSelectedItem().toString()+":"+m6.getSelectedItem().toString()+":00",
//                        h7.getSelectedItem().toString()+":"+m7.getSelectedItem().toString()+":00",
//                        h8.getSelectedItem().toString()+":"+m8.getSelectedItem().toString()+":00",
//                        h9.getSelectedItem().toString()+":"+m9.getSelectedItem().toString()+":00",
//                        h10.getSelectedItem().toString()+":"+m10.getSelectedItem().toString()+":00");
//
//                if (isInserted = true)
//                    Toast.makeText(EditTimings.this, "Timings are set successfully",Toast.LENGTH_LONG).show();
//                else
//                    Toast.makeText(EditTimings.this, "Some eRror occurred",Toast.LENGTH_LONG).show();
//            }
//        });
//    }




    public void SendValue() {
        final String t1 = h1.getSelectedItem().toString()+":"+m1.getSelectedItem().toString()+":00";
        final String t2 = h2.getSelectedItem().toString()+":"+m2.getSelectedItem().toString()+":00";
        final String t3 = h3.getSelectedItem().toString()+":"+m3.getSelectedItem().toString()+":00";
        final String t4 = h4.getSelectedItem().toString()+":"+m4.getSelectedItem().toString()+":00";
        final String t5 = h5.getSelectedItem().toString()+":"+m5.getSelectedItem().toString()+":00";
        final String t6 = h6.getSelectedItem().toString()+":"+m6.getSelectedItem().toString()+":00";
        final String t7 = h7.getSelectedItem().toString()+":"+m7.getSelectedItem().toString()+":00";
        final String t8 = h8.getSelectedItem().toString()+":"+m8.getSelectedItem().toString()+":00";
        final String t9 = h9.getSelectedItem().toString()+":"+m9.getSelectedItem().toString()+":00";
        final String t10 = h10.getSelectedItem().toString()+":"+m10.getSelectedItem().toString()+":00";

        String URL = "http://192.168.43.152/bellapp/v1/getValues.php";
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
                            startActivity(new Intent(EditTimings.this,MainActivity.class));
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
                params.put("t1",t1);
                params.put("t2",t2);
                params.put("t3",t3);
                params.put("t4",t4);
                params.put("t5",t5);
                params.put("t6",t6);
                params.put("t7",t7);
                params.put("t8",t8);
                params.put("t9",t9);
                params.put("t10",t10);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        if (view == btnSetTime){
            SendValue();
        }
    }
}
