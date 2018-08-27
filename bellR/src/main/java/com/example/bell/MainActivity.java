package com.example.bell;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.felhr.usbserial.UsbSerialDevice;
import com.felhr.usbserial.UsbSerialInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    public final String ACTION_USB_PERMISSION = "com.example.bell.USB_PERMISSION";
    UsbManager usbManager;
    UsbDevice device;
    UsbSerialDevice serialPort;
    UsbDeviceConnection connection;

    int clickcount = 0;
    //int counter = 0, counter2 = 0;

    TextView timeview,timeview2,txtAnn,txtDisAnn,txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9,txt10,mainlogo;
    TextView textView,txtHead,txtMessage;
    Button startButton, stopButton;
    String currentDate;


    UsbSerialInterface.UsbReadCallback mCallback = new UsbSerialInterface.UsbReadCallback() { //Defining a Callback which triggers whenever data is read.
        @Override
        public void onReceivedData(byte[] arg0) {
            String data = null;
            try {
                data = new String(arg0, "UTF-8");
                data.concat("/n");
                //tvAppend(textView, data);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


        }
    };

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { //Broadcast Receiver to automatically start and stop the Serial connection.
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_USB_PERMISSION)) {
                boolean granted = intent.getExtras().getBoolean(UsbManager.EXTRA_PERMISSION_GRANTED);
                if (granted) {
                    connection = usbManager.openDevice(device);
                    serialPort = UsbSerialDevice.createUsbSerialDevice(device, connection);
                    if (serialPort != null) {
                        if (serialPort.open()) { //Set Serial Connection Parameters.
                            setUiEnabled(true);
                            serialPort.setBaudRate(9600);
                            serialPort.setDataBits(UsbSerialInterface.DATA_BITS_8);
                            serialPort.setStopBits(UsbSerialInterface.STOP_BITS_1);
                            serialPort.setParity(UsbSerialInterface.PARITY_NONE);
                            serialPort.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF);
                            serialPort.read(mCallback);
                            //tvAppend(textView,"Serial Connection Opened!\n");
                            //textView.setText("USB Connection Opened!");

                        } else {
                            Log.d("SERIAL", "PORT NOT OPEN");
                        }
                    } else {
                        Log.d("SERIAL", "PORT IS NULL");
                    }
                } else {
                    Log.d("SERIAL", "PERM NOT GRANTED");
                }
            } else if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_ATTACHED)) {
                onClickStart(startButton);
            } else if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_DETACHED)) {
                onClickStop(stopButton);

            }
        }

        ;
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        myDb = new DatabaseHelper(this);


        //FOR USB CONN START

        usbManager = (UsbManager) getSystemService(this.USB_SERVICE);
        startButton = (Button) findViewById(R.id.buttonStart);
        //sendButton = (Button) findViewById(R.id.buttonSend);
        stopButton = (Button) findViewById(R.id.buttonStop);
        //textView = (TextView) findViewById(R.id.connStatusView);
        setUiEnabled(false);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_USB_PERMISSION);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        registerReceiver(broadcastReceiver, filter);

        //FOR USB CONN END


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
        //txtHead = (TextView)findViewById(R.id.noticeSub);
        txtMessage = (TextView)findViewById(R.id.noticeMess);

        Timer();

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

    public void AddNoticeToSqLite(){

        boolean isInserted = myDb.insertData(txtDisAnn.getText().toString(),timeview.getText().toString(),currentDate);

        if (isInserted == true){
            //Toast.makeText(MainActivity.this, "Added to SqLite", Toast.LENGTH_LONG).show();
        }else {
            //Toast.makeText(MainActivity.this, "Error occured while adding to SqLite",Toast.LENGTH_LONG).show();
        }

    }


    public void SendNoticesToDatabaseRecord(){

        String string = txtDisAnn.getText().toString();
        String time = timeview.getText().toString();
        String date = currentDate;
        //String URL2 = "http://192.168.43.152/bellapp/v1/getString.php";
        String URL2 = "http://192.168.43.25/bellapp/v1/getStringListRecordBell.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

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

                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("string",string);
                params.put("time",time);
                params.put("date",date);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.belltimingsmenu){
            Intent i = new Intent(MainActivity.this, OtherTimingsActivity.class);
            startActivity(i);
            return true;
        }

        if (id == R.id.notimenu){
            Intent i2 = new Intent(MainActivity.this, Recent_Notifications.class);
            startActivity(i2);
            return true;
        }
        return true;
    }


    public void setUiEnabled(boolean bool) {
        startButton.setEnabled(!bool);
        //sendButton.setEnabled(bool);
        stopButton.setEnabled(bool);
        //textView.setEnabled(bool);

    }

    public void onClickStart(View view) {

        HashMap<String, UsbDevice> usbDevices = usbManager.getDeviceList();
        if (!usbDevices.isEmpty()) {
            boolean keep = true;
            for (Map.Entry<String, UsbDevice> entry : usbDevices.entrySet()) {
                device = entry.getValue();
                int deviceVID = device.getVendorId();
                if (deviceVID == 0x2341)//Arduino Vendor ID
                {
                    PendingIntent pi = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
                    usbManager.requestPermission(device, pi);
                    keep = false;
                } else {
                    connection = null;
                    device = null;
                }

                if (!keep)
                    break;
            }
        }


    }

//    public void onClickSend(View view) {
//        String string = editText.getText().toString();
//        serialPort.write(string.getBytes());
//        tvAppend(textView, "\nData Sent : " + string + "\n");
//
//    }

    public void onClickStop(View view) {
        setUiEnabled(false);
        serialPort.close();
        //tvAppend(textView,"\nSerial Connection Closed! \n");
        //textView.setText("USB Connection Closed!");

    }




    private void RingBell() {

        String string = "ringit/";
        String currentTime = timeview.getText().toString();

        if (currentTime.equals(txt1.getText().toString().trim()))
        {
            Toast.makeText(this,"1st",Toast.LENGTH_LONG).show();
            serialPort.write(string.getBytes());
            //MediaPlayer ring = MediaPlayer.create(MainActivity.this,R.raw.tone);
            //ring.start();
            }

        if (currentTime.equals(txt2.getText().toString()))
        {Toast.makeText(this,"2nd",Toast.LENGTH_LONG).show();
            serialPort.write(string.getBytes());
            }


        if (currentTime.equals(txt3.getText().toString()))
        {Toast.makeText(this,"3rd",Toast.LENGTH_LONG).show();
            serialPort.write(string.getBytes());
        }


        if (currentTime.equals(txt4.getText().toString()))
        {Toast.makeText(this,"4th",Toast.LENGTH_LONG).show();
            serialPort.write(string.getBytes());
        }

        if (currentTime.equals(txt5.getText().toString()))
        {Toast.makeText(this,"5th",Toast.LENGTH_LONG).show();
            serialPort.write(string.getBytes());
        }

        if (currentTime.equals(txt6.getText().toString()))
        {Toast.makeText(this,"6th",Toast.LENGTH_LONG).show();
            serialPort.write(string.getBytes());
        }

        if (currentTime.equals(txt7.getText().toString()))
        {Toast.makeText(this,"7th",Toast.LENGTH_LONG).show();
            serialPort.write(string.getBytes());
        }

        if (currentTime.equals(txt8.getText().toString()))
        {Toast.makeText(this,"8th",Toast.LENGTH_LONG).show();
            serialPort.write(string.getBytes());
        }

        if (currentTime.equals(txt9.getText().toString()))
        {Toast.makeText(this,"9th",Toast.LENGTH_LONG).show();
            serialPort.write(string.getBytes());
        }

        if (currentTime.equals(txt10.getText().toString()))
        {Toast.makeText(this,"10th",Toast.LENGTH_LONG).show();
            serialPort.write(string.getBytes());
        }


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
                                GetAnnString();


                                if (!txtAnn.getText().toString().trim().equals(txtDisAnn.getText().toString().trim())){
                                    //counter = counter + 1;
                                    txtDisAnn.setText(txtAnn.getText().toString());

                                    if (!txtMessage.getText().toString().equals("")){
                                        SendNoticesToDatabaseRecord();
                                        AddNoticeToSqLite();
                                        //txtHead.setText("Message Recieved");
                                    }else{
                                        //txtHead.setText("");
                                    }
                                    //txtMessage.setText(txtDisAnn.getText().toString());


                                    // ******DO  ACTIVATE  THIS******

                                    serialPort.write(txtDisAnn.getText().toString().getBytes());
                                }

//                                if (counter == 1){
//                                    counter2 = counter2 + 1;
//                                }
//                                if (counter2 == 2){
//                                    counter = 0;
//                                    counter2 = 0;
//                                    txtAnn.setText("");
//                                }
                                timeview = (TextView)findViewById(R.id.timeview);
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                                String timeString = sdf.format(date);
                                timeview.setText(timeString);

                                RingBell();

                            }
                        });
                    }
                }catch (InterruptedException e){}
            }
        };
        t.start();


    }


//    private void Timer2(){
//        //TIMER
//
//        Thread t = new Thread(){
//            @Override
//            public void run(){
//                try {
//                    while (!isInterrupted()){
//                        Thread.sleep(1000);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                SetTimesToView();
//
//                                timeview2 = (TextView)findViewById(R.id.timeview2);
//                                long date = System.currentTimeMillis();
//                                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
//                                String timeString = sdf.format(date);
//                                timeview2.setText(timeString);
//
//                            }
//                        });
//                    }
//                }catch (InterruptedException e){}
//            }
//        };
//        t.start();
//
//
//    }


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


    private void GetAnnString(){
        //String URL = "http://192.168.43.152/bellapp/includes/SendString.php";
        String URL = "http://192.168.43.25/bellapp/includes/SendString.php";

        StringRequest stringRequest = new StringRequest(URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        setString(response);

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

    private void setString(String responce){
        String s1 = "";

        try {
            JSONObject jsonObject = new JSONObject(responce);
            JSONArray details = jsonObject.getJSONArray("string");
            JSONObject driveDetails = details.getJSONObject(0);
            s1 = driveDetails.getString("string");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        txtAnn.setText(s1);
        txtMessage.setText(s1);
    }


//    private void tvAppend(TextView tv, CharSequence text) {
//        final TextView ftv = tv;
//        final CharSequence ftext = text;
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                ftv.append(ftext);
//            }
//        });
//    }


}
