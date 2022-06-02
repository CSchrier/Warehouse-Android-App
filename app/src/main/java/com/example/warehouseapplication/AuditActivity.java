package com.example.warehouseapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class AuditActivity extends AppCompatActivity {
    Socket s;
    PrintWriter pw;
    String ip = "192.168.29.49";
    String bin;
    String bay;
    EditText binText;
    EditText bayText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    public void submitAudit(View view) throws IOException, InterruptedException {
        binText =  findViewById(R.id.Bin);
        bayText =  findViewById(R.id.bay);
        bin = binText.getText().toString();
        bay = bayText.getText().toString();
        TextView lastAddedTextView = findViewById(R.id.lastAdded);
        if(bin.length()>6&&bay.length()>4){
            lastAddedTextView.setText(bin + " was added to bay " + bay);
            System.out.println("sending data to server");
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {
                        s = new Socket(ip, 4999);
                        pw = new PrintWriter(s.getOutputStream());
                        pw.println("0 0 "+ bin + " " + bay);
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
            lastAddedTextView.setText("Something went wrong, scan again");

        }
        binText.setText("");
        bayText.setText("");
        binText.requestFocus();
    }


    public void submitAuditToRoom(View view) throws IOException, InterruptedException {
        bayText =  findViewById(R.id.bay);
        binText = findViewById(R.id.Bin);
        bin = binText.getText().toString();
        TextView lastAddedTextView = findViewById(R.id.lastAdded);
        if(bin.length()>6){
            lastAddedTextView.setText(bin + " was cleared");
            System.out.println("sending data to server");
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {
                        s = new Socket(ip, 4999);
                        pw = new PrintWriter(s.getOutputStream());
                        pw.println("0 0 "+ bin + " room");
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
            lastAddedTextView.setText("Something went wrong, scan again");

        }
        binText.setText("");
        bayText.setText("");
        binText.requestFocus();
    }


    public void goToManualEntry(View view) {
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());    }
}