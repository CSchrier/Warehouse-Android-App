package com.example.warehouseapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Locale;
import java.util.Scanner;

public class InboundActivity extends AppCompatActivity {
    Socket s;
    PrintWriter pw;
    String ip = "192.168.29.49";
    String job = "";
    String bay = "";
    EditText jobText;
    EditText scanSpot;
    TextView output;
    TextView CounterText;
    String newText;
    int counter = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbound);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        jobText = (EditText) findViewById(R.id.jobText);
        scanSpot = (EditText) findViewById(R.id.scanSpot);
        output = (TextView)  findViewById(R.id.Output);
        CounterText = (TextView) findViewById(R.id.counterText);
    }


    public void submitBin(View view) {
        job = jobText.getText().toString();
        bay = scanSpot.getText().toString().toUpperCase(Locale.ROOT);
        if(bay.length()<5){
            newText = "Invalid scan\n";
            output.setText(newText+output.getText());

        }else{
            System.out.println("sending data to server");
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {
                        s = new Socket(ip, 4999);
                        pw = new PrintWriter(s.getOutputStream());
                        pw.println("0 0 "+ job+"-"+counter + " " + bay);
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

            System.out.println("Successfully sent data");
            newText = job+"-"+counter+" has been added to bay "+bay+"\n";
            //output.append("\n"+job+"-"+counter+" has been added to bay "+bay);
            output.setText(newText+output.getText());
            counter++;
            CounterText.setText("Bin #"+counter);
            scanSpot.setText("");
            scanSpot.requestFocus();
        }





    }

    public void clear(View view) {
        jobText.setText("");
        jobText.requestFocus();
        counter = 1;
        CounterText.setText("Bin #"+counter);
    }
}