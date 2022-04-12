package com.example.warehouseapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
    }

    public void manualSubmit(View view) {
        binText = (EditText) findViewById(R.id.binEdit);
        bayText = (EditText) findViewById(R.id.bayEdit);
        jobText = (EditText) findViewById(R.id.jobEdit);
        bin = binText.getText().toString();
        bay = bayText.getText().toString();
        job = jobText.getText().toString();
        TextView textView = findViewById(R.id.TextViewEdit);
        if(job.length()>4&&bin.length()>0&&bay.length()>4){

            System.out.println("sending data to server");
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {
                        s = new Socket(ip, 4999);
                        pw = new PrintWriter(s.getOutputStream());
                        pw.println("0 0 "+ job+"-"+bin + " " + bay);
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
            textView.setText(job+"-"+bin  + " was added to bay " + bay);

        }else{
            textView.setText("Something went wrong, scan again");

        }
        binText.setText("");
        bayText.setText("");
        jobText.setText("");
        bayText.requestFocus();
    }

    public void editToRoom(View view) {
        binText = (EditText) findViewById(R.id.binEdit);
        jobText = (EditText) findViewById(R.id.jobEdit);
        bin = binText.getText().toString();
        job = jobText.getText().toString();
        TextView textView = findViewById(R.id.lastAdded);
        if(job.length()>6){
            textView.setText(job+"-"+bin+ " was added to audit room");
            System.out.println("sending data to server");
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {
                        s = new Socket(ip, 4999);
                        pw = new PrintWriter(s.getOutputStream());
                        pw.println("0 0 "+ job+"-"+bin + " room");
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