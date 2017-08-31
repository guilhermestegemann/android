package com.example.gui.md09;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MainActivity extends Service {

    @Override
    public IBinder onBind(Intent arg0){
        return null;
    }
}
