package com.example.warehouseapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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
    String bay;
    TextView locationText;
    TextView containsText;


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


*/





    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_warehouse_audit);


    }

    public void getData() throws IOException {

    }

    public void filter(String aisle){



        Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try  {
                s = new Socket(ip, 4999);
                pw = new PrintWriter(s.getOutputStream());
                pw.println("0 3 " + aisle);
                pw.flush();
                pw.close();

                InputStreamReader in;
                in = new InputStreamReader(s.getInputStream());
                BufferedReader br = new BufferedReader(in);
                String reply = br.readLine();

                String[] bays = reply.split("\\s");

                for(int i = 0; i<bays.length;i++){
                    String[] bay = bays[i].split("/");
                    locationText.setText(bay[0]+"-"+bay[1]);
                    containsText.setText(bay[2]+"-"+bay[3]);
                }





            }  catch (IOException e){
                Thread.currentThread().interrupt();
                System.out.println(
                        "Thread was interrupted, Failed to complete operation");
            }
        }
    });









    }


    public void StartAudit(View view) {
        Spinner spinner = findViewById(R.id.aisleSpinner);
        String aisle = spinner.toString();
        switch(aisle){
            case "A":
                filter("A");
                break;
            case "B":
                filter("B");
                break;
            case "C":
                filter("C");
                break;
            case "D":
                filter("D");
                break;
            case "E":
                filter("E");
                break;
            case "F":
                filter("F");
                break;
            case "G":
                filter("G");
                break;
            case "H":
                filter("H");
                break;
            case "I":
                filter("I");
                break;
            case "J":
                filter("J");
                break;
            case "K":
                filter("K");
                break;
            default:

        }

    }




}