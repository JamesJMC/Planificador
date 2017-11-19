package com.example.james.planificador.GUI;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Debug;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.james.planificador.R;

public class VerInfoSistema extends AppCompatActivity {

    private final int CONVERT = 1024;
    private ActivityManager activity_man;
    TextView batterLevel, memoria, cpu, so;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_info_sistema);

        batterLevel = (TextView) findViewById(R.id.textView6);

        memoria = (TextView)findViewById(R.id.textViewMemoria);
        activity_man = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);

        cpu = (TextView)findViewById(R.id.textViewCPU);
        so = (TextView)findViewById(R.id.textViewSO);


    }


    public void verInfo(View view){

        //BATERIA

        BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                context.unregisterReceiver(this);
                int rawlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int level = -1;
                if (rawlevel >= 0 && scale > 0) {
                    level = (rawlevel * 100) / scale;
                }
                batterLevel.setText("Battery Level Remaining: " + level + "%");
            }
        };
        IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryLevelReceiver, batteryLevelFilter);

        //MEMORIA

        ActivityManager.MemoryInfo mem_info;
        double mem_size;

        mem_info = new ActivityManager.MemoryInfo();
        activity_man.getMemoryInfo(mem_info);
        mem_size = (mem_info.availMem / (CONVERT * CONVERT));

        memoria.setText(String.format("Available memory:\t %.2f Mb", mem_size));


        //CPU
        String PhoneModel = android.os.Build.MODEL;
        String AndroidVersion = android.os.Build.VERSION.RELEASE + Build.BOARD;
        so.setText("Version del Sitema: "+ PhoneModel + " (" + AndroidVersion +")");

        //ALMACENAMIENTO INTERNO


    }


}

