package com.example.warehouseapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;

public class WarehouseAuditActivity extends AppCompatActivity {

    Socket s;
    PrintWriter pw;
    String ip = "192.168.29.49";
    String bin;
    String[] bay;

    String[] bays;
    int bayIncrementer;
    Button auditStartBtn;
    Button correctBtn;
    Button incorrectBtn;
    Button isEmptyButton;
    TextView locationText;
    TextView containsText;
    String aisle;
    String singleBay;


    /*

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

#TODO - when audit starts, lock audit button and set increment counter to 0.
  Fill array with the bay info and use a counter to work through each spot,
  when array has been fully traversed, unlock audit button.
*/





    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_warehouse_audit);
        locationText = findViewById(R.id.locationText);
        containsText = findViewById(R.id.containsText);
        correctBtn = findViewById(R.id.correctBtn);
        incorrectBtn = findViewById(R.id.incorrectBtn);
        isEmptyButton = findViewById(R.id.isEmptyBtn);
        auditStartBtn = findViewById(R.id.auditStartBtn);

        correctBtn.setEnabled(false);
        incorrectBtn.setEnabled(false);
        isEmptyButton.setEnabled(false);


    }



    public void getData(String aisle){


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    s = new Socket(ip, 4999);
                    pw = new PrintWriter(s.getOutputStream());
                    pw.println("0 3 " + aisle);
                    pw.flush();
                    pw.close();

                    InputStreamReader in;
                    in = new InputStreamReader(s.getInputStream());
                    BufferedReader br = new BufferedReader(in);
                    String reply = br.readLine();

                    bays = reply.split("\\s");


                } catch (IOException e) {
                    Thread.currentThread().interrupt();
                    System.out.println(
                            "Thread was interrupted, Failed to complete operation");
                }
            }
        });








    }


    public void StartAudit(View view) {
        bayIncrementer = 0;
        auditStartBtn.setEnabled(false);
        correctBtn.setEnabled(true);
        incorrectBtn.setEnabled(true);
        isEmptyButton.setEnabled(true);
        Spinner spinner = findViewById(R.id.aisleSpinner);
        String aisle = spinner.toString();
        getData(aisle);

        updateInfo();



    }

    private void updateInfo(){
            if(bayIncrementer<bays.length ){
                bay = bays[bayIncrementer].split("/");

                locationText.setText(bay[0]+"-"+bay[1]);
                containsText.setText(bay[2]+"-"+bay[3]);



                bayIncrementer++;
            }else{
                //end
                //re-enable start audit button for another aisle
                auditStartBtn.setEnabled(true);
                correctBtn.setEnabled(false);
                incorrectBtn.setEnabled(false);
                isEmptyButton.setEnabled(false);
            }
    }


    public void response(View v) {
        if (v.getId() == R.id.correctBtn) {
            updateInfo();
        } else if (v.getId() == R.id.incorrectBtn) {
            //popup where user inputs job & bin
            Intent popup = new Intent(WarehouseAuditActivity.this,pop.class);
            popup.putExtra("currentBay",bay[0]+"-"+bay[1]);
            startActivity(popup);
            updateInfo();
        } else if (v.getId() == R.id.isEmptyBtn) {
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {
                        s = new Socket(ip, 4999);
                        pw = new PrintWriter(s.getOutputStream());
                        pw.println("0 0 0-0" + bay[0]+"-"+bay[1]);
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
            updateInfo();
        }



    }




}