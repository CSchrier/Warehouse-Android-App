package com.example.warehouseapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;





public class SearchActivity extends AppCompatActivity {
    EditText jobText;
    String searchedJob;
    TextView output;
    ScrollView scrollView;
    TextView searchedText;
    Socket s;
    PrintWriter pw;
    String ip = "192.168.29.49";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        jobText = (EditText) findViewById(R.id.jobSearch);
        output = (TextView) findViewById(R.id.listOfJobs);
        searchedText = (TextView) findViewById(R.id.searchedJobs);
        getJobs();


    }

    public void getJobs(){
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    s = new Socket(ip, 4999);
                    pw = new PrintWriter(s.getOutputStream());
                    InputStreamReader in;
                    pw.println("1 2");
                    pw.flush();
                    in = new InputStreamReader(s.getInputStream());

                    BufferedReader br = new BufferedReader(in);
                    String string = br.readLine();
                    output = (TextView) findViewById(R.id.listOfJobs);
                    output.setText(string);

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
        searchedText.setText("");
        searchedJob = jobText.getText().toString();
        //send some command to server with the job we want to know the location of

        if(searchedJob.length()>4){
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {
                        s = new Socket(ip, 4999);
                        pw = new PrintWriter(s.getOutputStream());
                        InputStreamReader in;
                        pw.println("1 3 "+searchedJob);
                        pw.flush();

                        in = new InputStreamReader(s.getInputStream());
                        BufferedReader br = new BufferedReader(in);
                        String string = br.readLine();
                        searchedText = (TextView) findViewById(R.id.searchedJobs);
                        searchedText.setText(string);

                    }  catch (IOException e){
                        Thread.currentThread().interrupt();
                        System.out.println(
                                "Thread was interrupted, Failed to complete operation");
                    }
                }
            });
            thread.start();
        }



        //take the response from the server and add it to our output
    }
}