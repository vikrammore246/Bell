package com.example.bell;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OtherTimingsActivity extends AppCompatActivity {

    TextView txtAnn,txtDisAnn,txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9,txt10,mainlogo;
    int clickcount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_timings);


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

        txtAnn = (TextView)findViewById(R.id.txtAnnouncement);
        txtDisAnn = (TextView)findViewById(R.id.txtDisabledAnnouncement);

        SetTimesToView();


        mainlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickcount=clickcount+1;
                if (clickcount==10){
                    Toast.makeText(getApplicationContext(),"clicked "+clickcount+" times",Toast.LENGTH_LONG).show();

                    AlertDialog.Builder mbuilder = new AlertDialog.Builder(OtherTimingsActivity.this);
                    View mview = getLayoutInflater().inflate(R.layout.infodilog,null);

                    mbuilder.setView(mview);
                    AlertDialog dialog = mbuilder.create();
                    dialog.show();

                    clickcount = 0;
                }
            }
        });


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


}
