package com.example.bell;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;



import java.text.SimpleDateFormat;


import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    DatabaseHelper myDb;
    DatabaseHelper2 myDb2;
    DatabaseHelper3 myDb3;
    Databasehelper4 myDb4;
    DatabaseHelper5 myDb5;

    int clickcount=0, errcount=0;
    TextView timeview,txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9,txt10,mainlogo,viewIp;
    Button btnMF,btnSat,btnEdit,btnAnn;
    String s1,s2,s3,s4,s5,s6,s7,s8,s9,s10;
    String IpAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);
        myDb2 = new DatabaseHelper2(this);
        myDb3 = new DatabaseHelper3(this);
        myDb4 = new Databasehelper4(this);
        myDb5 = new DatabaseHelper5(this);

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
        viewIp = (TextView) findViewById(R.id.viewip);


        btnMF = (Button)findViewById(R.id.btnSetMFTimings);
        btnSat = (Button)findViewById(R.id.btnSetSatTimings);
        btnEdit = (Button)findViewById(R.id.btnEdtTimings);
        btnAnn = (Button)findViewById(R.id.btnAnnouncement);

        btnAnn.setOnClickListener(this);

        SetThirdDatabaseToView();
        getIpFromDatabase();
        Timer();
        EditTimingsClick();
        ShowPhoneDataMF();
        ShowPhoneDataSat();

        mainlogo.setOnClickListener((View v) -> {
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
        });


    }

    private void SetThirdDatabaseToView(){
        Cursor res2 = myDb3.getAllData3();
        if (res2.getCount() == 0){
            //showMessage("Error", "Nothing Found in Database to Show!\nSet Some Timings First.");
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
        txt1.setText(s1);
        txt2.setText(s2);
        txt3.setText(s3);
        txt4.setText(s4);
        txt5.setText(s5);
        txt6.setText(s6);
        txt7.setText(s7);
        txt8.setText(s8);
        txt9.setText(s9);
        txt10.setText(s10);
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
                                //SetTimesToView();
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
            SendLiteValueToNode();
            AddToThirdDatabase1();
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
            SendLiteValueToNode();
            AddToThirdDatabase1();
        });
    }


    private void SendLiteValueToNode() {

        //String URL = "http://192.168.43.152/bellapp/v1/getValues.php";
        //String URL = "http://192.168.43.25/bellapp/v1/getValues.php";
        getIpFromDatabase();
        String URL = "http://" + IpAddress + "/" +s1+s2+s3+s4+s5+s6+s7+s8+s9+s10;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(getApplicationContext(), "Time set",Toast.LENGTH_LONG).show();
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
            if (errcount == 0) {
                showMessage("Hello", "First insert Ip Address of NodeMCU in menu options,\nto start using the app.");
                errcount++;
                return;
            }
        }else {
            IpAddress = res4.getString(1);
        }
        viewIp.setText(IpAddress);
    }


    private void AddToThirdDatabase1(){
        Cursor res = myDb3.getAllData3();

        if (res.getCount() == 0){
            boolean isInserted = myDb3.insertData3(s1,s2,s3,s4,s5,s6,s7,s8,s9,s10);

            if (isInserted == true) {
                //Toast.makeText(MainActivity.this, "Timings Inserted successfully", Toast.LENGTH_LONG).show();
                //startActivity(new Intent(this, MainActivity.class));

//                finish();
//                startActivity(getIntent());

                SetThirdDatabaseToView();
            }
            else
                Toast.makeText(MainActivity.this, "Some eRror occurred while getting timings",Toast.LENGTH_LONG).show();

            return;
        }else {
            boolean isUpdate = myDb3.updateData3("1",s1,s2,s3,s4,s5,s6,s7,s8,s9,s10);

            if (isUpdate == true) {
                //Toast.makeText(MainActivity.this, "Timings Saved successfully", Toast.LENGTH_LONG).show();
                //startActivity(new Intent(this, MainActivity.class));

//                finish();
//                startActivity(getIntent());

                SetThirdDatabaseToView();
            }
            else
                Toast.makeText(MainActivity.this, "Some eRroR occurred while getting timings",Toast.LENGTH_LONG).show();
        }


    }


    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    private void EditTimingsClick() {
        btnEdit.setOnClickListener((View a)->{
            AlertDialog.Builder mbuilder = new AlertDialog.Builder(MainActivity.this);
            View mview = getLayoutInflater().inflate(R.layout.edit_options_dialog,null);
            TextView monfri = (TextView) mview.findViewById(R.id.edtMFView);
            TextView sat = (TextView) mview.findViewById(R.id.edtSatView);
            TextView newt = (TextView) mview.findViewById(R.id.edtNewView);

            monfri.setOnClickListener((View v) -> {

                finish();
                startActivity(new Intent(this,MonFriTimings.class));
            });

            sat.setOnClickListener((View v) ->{

                finish();
                startActivity(new Intent(this,SatTimings.class));
            });

            newt.setOnClickListener((View v) ->{

                finish();
                startActivity(new Intent(this,NewTimings.class));
            });
            mbuilder.setView(mview);
            AlertDialog dialog = mbuilder.create();
            dialog.show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.nodeipmenu){
            Cursor res = myDb5.getAllData5();
            if (res.getCount() == 0){

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View view = getLayoutInflater().inflate(R.layout.ippassword,null);
                EditText pass = (EditText) view.findViewById(R.id.edtpass);
                EditText repass = (EditText) view.findViewById(R.id.edtrepass);
                Button btnsave = (Button) view.findViewById(R.id.btnsave);

                btnsave.setOnClickListener((View v) -> {

                    String passowrd;
                    passowrd = pass.getText().toString().trim();
                    Cursor dat = myDb5.getAllData5();

                    if (dat.getCount() == 0){
                        boolean isInserted = myDb5.insertData5(passowrd);
                        if (isInserted == true) {
                            Toast.makeText(getApplicationContext(), "Password Saved!", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(getIntent());
                        }else {
                            Toast.makeText(getApplicationContext(), "Some eRror occurred while saving password",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        boolean isUpdate = myDb5.updateData5("1",passowrd);
                        if (isUpdate == true) {
                            Toast.makeText(getApplicationContext(), "Password Saved!", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(getIntent());
                        }else{
                            Toast.makeText(getApplicationContext(), "Some eRror occurred while saving password",Toast.LENGTH_LONG).show();
                        }
                    }
                });

                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.show();

            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View view = getLayoutInflater().inflate(R.layout.enterpassword,null);
                EditText mainpass = (EditText) view.findViewById(R.id.mainpass);
                Button btngo = (Button) view.findViewById(R.id.btngo);

                btngo.setOnClickListener((View v) -> {
                    String mainpassword = mainpass.getText().toString().trim();
                    Cursor d = myDb5.getAllData5();
                    String savedpass = d.getString(1);
                    if (mainpassword.equals(savedpass)){
                        finish();
                        startActivity(new Intent(this, EditIpActivity.class));
                    }else {
                        Toast.makeText(getApplicationContext(), "Password is incorrect", Toast.LENGTH_LONG).show();
                    }
                });

//                Intent i = new Intent(MainActivity.this, EditIpActivity.class);
//                startActivity(i);

                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            return true;
        }


        if (id == R.id.changepassmenu){
            Cursor res = myDb5.getAllData5();
            if (res.getCount() == 0){

                Toast.makeText(getApplicationContext(),"Password not found to Change!",Toast.LENGTH_SHORT).show();
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View view = getLayoutInflater().inflate(R.layout.change_password_layout,null);
                EditText oldpass = (EditText) view.findViewById(R.id.edtoldpass);
                EditText newpass = (EditText) view.findViewById(R.id.edtnewpass);
                EditText renewpass = (EditText) view.findViewById(R.id.edtrenewpass);
                Button btnchange = (Button) view.findViewById(R.id.btnchangepass);

                btnchange.setOnClickListener((View v) -> {
                    Cursor d = myDb5.getAllData5();
                    String savedpass = d.getString(1);
                    if (oldpass.getText().toString().trim().equals(savedpass)){
                        if (newpass.getText().toString().trim().equals(renewpass.getText().toString().trim())){

                            boolean isUpdate = myDb5.updateData5("1",newpass.getText().toString().trim());
                            if (isUpdate == true) {
                                Toast.makeText(getApplicationContext(), "Password Updated!", Toast.LENGTH_LONG).show();
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "Some eRror occurred while updating password",Toast.LENGTH_LONG).show();
                            }


                        }else {
                            Toast.makeText(getApplicationContext(),"Password does not Matching",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Old Password is Incorrect!", Toast.LENGTH_LONG).show();
                    }
                });

//                Intent i = new Intent(MainActivity.this, EditIpActivity.class);
//                startActivity(i);

                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            return true;

        }

        return true;
    }



    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }



    @Override
    public void onClick(View view) {
        if (view == btnAnn){
            startActivity(new Intent(this,AnnouncementActivity.class));
        }
    }

//    private void SetTimesToView() {
//        //String URL = "http://192.168.43.152/bellapp/includes/fetchTimesArray.php";
//        String URL = "http://192.168.43.25/bellapp/includes/fetchTimesArray.php";
//
//        StringRequest stringRequest = new StringRequest(URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        showDetails(response);
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                });
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//    }
//
//    private void showDetails(String responce){
//        String t1 = "";
//        String t2 = "";
//        String t3 = "";
//        String t4 = "";
//        String t5 = "";
//        String t6 = "";
//        String t7 = "";
//        String t8 = "";
//        String t9 = "";
//        String t10 = "";
//
//        try {
//            JSONObject jsonObject = new JSONObject(responce);
//            JSONArray details = jsonObject.getJSONArray("times");
//            JSONObject driveDetails = details.getJSONObject(0);
//            t1 = driveDetails.getString("t1");
//            t2 = driveDetails.getString("t2");
//            t3 = driveDetails.getString("t3");
//            t4 = driveDetails.getString("t4");
//            t5 = driveDetails.getString("t5");
//            t6 = driveDetails.getString("t6");
//            t7 = driveDetails.getString("t7");
//            t8 = driveDetails.getString("t8");
//            t9 = driveDetails.getString("t9");
//            t10 = driveDetails.getString("t10");
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        txt1.setText(t1);
//        txt2.setText(t2);
//        txt3.setText(t3);
//        txt4.setText(t4);
//        txt5.setText(t5);
//        txt6.setText(t6);
//        txt7.setText(t7);
//        txt8.setText(t8);
//        txt9.setText(t9);
//        txt10.setText(t10);
//    }
}
