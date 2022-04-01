package com.example.warehouseapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WarehouseMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_main);
    }

    public void goToAudits(View view){
        Intent intent = new Intent(this, AuditActivity.class);
        startActivity(intent);
    }
    public void goToFinishedGoods(View view){
        Intent intent = new Intent(this, FinishedGoodsActivity.class);
        startActivity(intent);
    }
    public void goToScrap(View view){
        Intent intent = new Intent(this, ScrapActivity.class);
        startActivity(intent);
    }
}