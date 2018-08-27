package com.example.vikram.bell;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

/**
 * Created by vikram on 25/8/17.
 */

public class BackgroundServices extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public BackgroundServices() {
        super();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
