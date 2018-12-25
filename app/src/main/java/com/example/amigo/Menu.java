package com.example.amigo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.google.firebase.auth.FirebaseAuth;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
    public void MedFunction(View view){
        Intent intent = new Intent(this,MedicinesManegment.class);
        startActivity(intent);
    }
    public void EventsFunc(View view){
        Intent intent = new Intent(this,Events.class);
        startActivity(intent);
    }
    public void contactsFunc(View view){
        Intent intent = new Intent(this,ContactsToCall.class);
        startActivity(intent);
    }
    public void EmergencyFunc(View view){
        Intent intent = new Intent(this,Emergency.class);
        startActivity(intent);

    }
    public void UserExit(View view){
        FirebaseAuth myAuth = FirebaseAuth.getInstance();
        myAuth.signOut();
        Intent intent = new Intent(Menu.this,MainActivity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
