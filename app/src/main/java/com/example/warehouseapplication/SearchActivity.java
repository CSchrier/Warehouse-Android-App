package com.example.warehouseapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;





public class SearchActivity extends AppCompatActivity {
    EditText jobText;
    String text;
    TextView output;
    Socket s;
    PrintWriter pw;
    String ip = "192.168.29.49";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        jobText = (EditText) findViewById(R.id.jobSearch);
        output = (TextView) findViewById(R.id.output);
        getJobs();


    }

    public void getJobs(){
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    s = new Socket(ip, 4999);
                    pw = new PrintWriter(s.getOutputStream());
                    pw.println("1 2");
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



    public void search(View view) {
        text = jobText.getText().toString();
        //send some command to server with the job we want to know the location of


        //take the response from the server and add it to our output
    }
}