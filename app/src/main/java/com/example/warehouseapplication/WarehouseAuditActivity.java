package com.example.warehouseapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;

public class WarehouseAuditActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_warehouse_audit);


    }

    public void getData() throws IOException {

    }



    public void StartAudit(View view) {
        Spinner spinner = findViewById(R.id.aisleSpinner);
        String aisle = spinner.toString();
        switch(aisle){
            case "A":
                filter("A");
                break;
            case "B":
                break;
            case "C":
                break;
            case "D":
                break;
            case "E":
                break;
            case "F":
                break;
            case "G":
                break;
            case "H":
                break;
            case "I":
                break;
            case "J":
                break;
            case "K":
                break;
            default:

        }

    }

    public void filter(String aisle){

    }


}