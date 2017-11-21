package com.example.james.planificador.GUI;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Debug;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.james.planificador.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class VerInfoSistema extends AppCompatActivity {

    private final int CONVERT = 1024;
    private ActivityManager activity_man;
    TextView batterLevel, memoria, cpu, so, memoriaInterna, memoriaExterna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_info_sistema);

        batterLevel = (TextView) findViewById(R.id.textView6);

        memoria = (TextView)findViewById(R.id.textViewMemoria);
        activity_man = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);

        cpu = (TextView)findViewById(R.id.textViewCPU);
        so = (TextView)findViewById(R.id.textViewSO);
        memoriaInterna = (TextView)findViewById(R.id.textViewMemInt);
        memoriaExterna = (TextView)findViewById(R.id.textView9);
    }


    public void verInfo(View view){

        //CPU
        try
        {
            RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r");
            String load = reader.readLine();

            String[] toks = load.split(" +");  // Split on one or more spaces

            long idle1 = Long.parseLong(toks[4]);
            long cpu1 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[5])
                    + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

            try {
                Thread.sleep(360);
            } catch (Exception e) {}

            reader.seek(0);
            load = reader.readLine();
            reader.close();

            toks = load.split(" +");

            long idle2 = Long.parseLong(toks[4]);
            long cpu2 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[5])
                    + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

            float usabilidadCPU = (cpu2 - cpu1) / ((cpu2 + idle2) - (cpu1 + idle1));
            cpu.setText("Uso de CPU: " + usabilidadCPU +" %" );


            //return (float)(cpu2 - cpu1) / ((cpu2 + idle2) - (cpu1 + idle1));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //MEMORIA

        ActivityManager.MemoryInfo mem_info;
        double mem_size;

        mem_info = new ActivityManager.MemoryInfo();
        activity_man.getMemoryInfo(mem_info);
        mem_size = (mem_info.availMem / (CONVERT * CONVERT));

        memoria.setText(String.format("Uso de memoria:\t %.2f Mb", mem_size));




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
                batterLevel.setText("Nivel de batería: " + level + "%");
            }
        };
        IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryLevelReceiver, batteryLevelFilter);




        //VERSION S.0
        String PhoneModel = android.os.Build.MODEL;
        String AndroidVersion = android.os.Build.VERSION.RELEASE;
        so.setText("Version del sistema operativo: "+ PhoneModel + " (" + AndroidVersion +")");

        //ALMACENAMIENTO INTERNO
        memoriaInterna.setText("Almacenamiento interno usado : " + usadoInterno());

        //ALMACENAMIENTO EXTERNO
        memoriaExterna.setText("Almacenamiento externo disponible \n : "+megabytesAvailable()+ "MB");

    }


    public static float megabytesAvailable() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long bytesAvailable = (long)stat.getBlockSize() * (long)stat.getAvailableBlocks();
        return bytesAvailable / (1024.f * 1024.f);
    }

    public static String usadoInterno() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();

        long tot = totalBlocks * blockSize;
        long availableBlocks = stat.getAvailableBlocksLong();
        long ava = availableBlocks * blockSize;
        long resta = tot-ava;
        return formatSize(resta);
    }



    public static String formatSize(long size) {
        String suffix = null;

        if (size >= 1024) {
            suffix = "KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024;
            }
        }

        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }

        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }


}

