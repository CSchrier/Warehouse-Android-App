package com.example.warehouseapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {
    EditText jobText;
    String text;
    TextView output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        jobText = (EditText) findViewById(R.id.jobSearch);
        output = (TextView) findViewById(R.id.output);
    }


    public void search(View view) {
        text = jobText.getText().toString();
        //send some command to server with the job we want to know the location of


        //take the response from the server and add it to our output
    }
}