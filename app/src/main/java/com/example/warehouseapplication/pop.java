package com.example.warehouseapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class pop extends Activity {

    Socket s;
    PrintWriter pw;
    String ip = "192.168.29.49";
    String bay;
    EditText binText;
    String bin;
    String job;
    EditText jobText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int) (height*.8));
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            bay = extras.getString("currentBay");
        }

    }


    public void manualSubmit(View view) {

        binText = findViewById(R.id.binEdit2);

        jobText =  findViewById(R.id.jobEdit2);

        bin = binText.getText().toString();

        job = jobText.getText().toString();



        if(job.length()>4&&bin.length()>0){

            System.out.println("sending data to server");

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {
                        s = new Socket(ip, 4999);
                        pw = new PrintWriter(s.getOutputStream());
                        pw.println("0 0 " + job +"-"+bin+ " "+ bay);
                        pw.flush();
                        pw.close();
                        s.close();

                    }  catch (IOException e){
                        Thread.currentThread().interrupt();
                        System.out.println(
                                "Thread was interrupted, Failed to complete operation");
                    }
                }
            });
            thread.start();
    }
        finish();
    }
}
