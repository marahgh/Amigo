package com.example.amigo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class timePicker extends AppCompatActivity {
    String Time;
    /*int Minutes,Hours;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);
       Time = findViewById(R.id.timePicker).toString();
       /* Minutes=Integer.parseInt(Time.substring(0,2));
        Hours=Integer.parseInt(Time.substring(3,5));
    */}
    public void backToMedicinesFunc(View view){
        Intent intent = new Intent(this,MedicinesManegment.class);
        startActivity(intent);
    }
}
