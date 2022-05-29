package com.bugsnag.monitorexceptionsmartbear;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bugsnag.android.Bugsnag;
import com.bugsnag.android.Configuration;
import com.bugsnag.android.internal.BugsnagMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    private Button bt_fileNotFoundException;
    private Button bt_airthmeticException;
    private Button bt_runtimeException;
    private TextView displayException;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         Configuration config  = Configuration.load(this);
         config.setEnabledReleaseStages(new HashSet<String>() {{
            add("production");
        }});
         Bugsnag.start(this, config);

        bt_fileNotFoundException = (Button)findViewById(R.id.bt_fileNotFoundException) ;
        bt_airthmeticException = (Button)findViewById(R.id.bt_airthmaticException) ;
        bt_runtimeException = (Button)findViewById(R.id.bt_runtimeExcetion) ;
        displayException = (TextView)findViewById(R.id.tv_DisplayCurrentException);
        Bugsnag.leaveBreadcrumb("Application Started ...");
        bt_fileNotFoundException.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileInputStream input1 = null;
                try {
                    Bugsnag.leaveBreadcrumb("Reading DEMO.API integration configuration file for list of product from Cloud S3 bucket.");
                    input1 = new FileInputStream("D:/file.txt");
               }catch(Exception e){
                    displayException.setText("Exception  : " + e);
                    Bugsnag.notify(e);
            }
            }
        });

        bt_airthmeticException.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             try {
                 Bugsnag.leaveBreadcrumb("Calculating total checkout price.");
                 int t = 10/0;
             }catch (Exception e){
                 displayException.setText("Exception  : " + e);
                 Bugsnag.notify(e);
             }
            }
        });

        bt_runtimeException.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bugsnag.leaveBreadcrumb("Fetching list of Electronic items available external api.");
                    throw new RuntimeException();
                }catch (Exception e){
                    displayException.setText("Exception  : " + e);
                    Bugsnag.notify(e);
                }
            }
        });

    }
}