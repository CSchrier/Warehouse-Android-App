package com.example.warehouseapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Locale;

public class EditActivity extends AppCompatActivity {
    Socket s;
    PrintWriter pw;
    String ip = "192.168.29.49";
    String bin;
    String bay;
    String job;
    EditText binText;
    EditText bayText;
    EditText jobText;
    Spinner aisleSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    public void manualSubmitNoClear(View view) {
        aisleSpinner = (Spinner)findViewById(R.id.aisleID);
        binText = findViewById(R.id.binEdit);
        bayText =  findViewById(R.id.bayEdit);
        jobText =  findViewById(R.id.jobEdit);

        bin = binText.getText().toString();
        bay = bayText.getText().toString();
        String aisle = aisleSpinner.getSelectedItem().toString();


        job = jobText.getText().toString();
        TextView textView = findViewById(R.id.outputText);
        /*
        if((bay.charAt(1))!=('-')){
            char addADash = '-';
            fixedBay = bay.substring(0,1).toUpperCase() + addADash + bay.substring(1);
        }else{
            fixedBay = bay;
        }
        */


        if(job.length()>4&&bin.length()>0&&bay.length()>2){

            System.out.println("sending data to server");

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {
                        s = new Socket(ip, 4999);
                        pw = new PrintWriter(s.getOutputStream());
                        pw.println("0 0 " + job +"-"+bin+ " "+ aisle+"-"+bay);
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
            textView.setText(job+"-"+bin  + " was added to "+aisle+"-"+bay);

        }else{
            textView.setText("Something went wrong, scan again");

        }



        binText.setText("");
        bayText.setText("");
        bayText.requestFocus();
    }





    public void manualSubmit(View view) {
        aisleSpinner = (Spinner)findViewById(R.id.aisleID);
        binText = findViewById(R.id.binEdit);
        bayText =  findViewById(R.id.bayEdit);
        jobText =  findViewById(R.id.jobEdit);
        String fixedBay;
        bin = binText.getText().toString();
        bay = bayText.getText().toString();
        String aisle = aisleSpinner.getSelectedItem().toString();


        job = jobText.getText().toString();
        TextView textView = findViewById(R.id.outputText);
        /*
        if((bay.charAt(1))!=('-')){
            char addADash = '-';
            fixedBay = bay.substring(0,1).toUpperCase() + addADash + bay.substring(1);
        }else{
            fixedBay = bay;
        }
        */


        if(job.length()>4&&bin.length()>0&&bay.length()>2){

            System.out.println("sending data to server");

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {
                        s = new Socket(ip, 4999);
                        pw = new PrintWriter(s.getOutputStream());
                        pw.println("0 0 " + job +"-"+bin+ " "+ aisle+"-"+bay);
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
            textView.setText(job+"-"+bin  + " was added to "+aisle+"-"+bay);

        }else{
            textView.setText("Something went wrong, scan again");

        }



        binText.setText("");
        bayText.setText("");
        jobText.setText("");
        bayText.requestFocus();
    }

    public void editToClear(View view) {

        aisleSpinner = (Spinner)findViewById(R.id.aisleID);
        bayText =  findViewById(R.id.bayEdit);

        bay = bayText.getText().toString();
        String aisle = aisleSpinner.getSelectedItem().toString();

        TextView textView = findViewById(R.id.outputText);
        if(bay.length()>2){
            textView.setText(aisle+"-"+bay+ " was cleared");
            System.out.println("sending data to server");
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {
                        s = new Socket(ip, 4999);
                        pw = new PrintWriter(s.getOutputStream());
                        pw.println("0 0 0-0 " + aisle+"-"+bay);
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

        }else{
            textView.setText("Something went wrong, scan again");

        }
        binText.setText("");
        bayText.setText("");
        jobText.setText("");
        binText.requestFocus();
    }
}