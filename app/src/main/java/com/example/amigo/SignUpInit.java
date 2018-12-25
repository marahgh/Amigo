package com.example.amigo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpInit extends AppCompatActivity {
    private EditText email,userName,password,confirmPassword,contact1Name
            ,contact1Num,contact2Name,contact2Num,contact3Name,contact3Num;
    String emailStr,userNameStr,passwordStr,confirmPasswordStr,contact1NameStr
            ,contact1NumStr,contact2NameStr,contact2NumStr,contact3NameStr,contact3NumStr;
    private Context context = this;
    private int areMatched=0;
    private FirebaseAuth myAuth;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_init);
        userName = (EditText)findViewById(R.id.username);
        password =(EditText)findViewById(R.id.password);
        email=(EditText)findViewById(R.id.email);
//        confirmPassword = (EditText)findViewById(R.id.ConfPassword);

        contact1Name =(EditText)findViewById(R.id.Contact1);
        contact1Num = (EditText)findViewById(R.id.CellNum1);
        contact2Name =(EditText)findViewById(R.id.Contact2);
        contact2Num = (EditText)findViewById(R.id.CellNum2);
        contact3Name =(EditText)findViewById(R.id.Contact3);
        contact3Num = (EditText)findViewById(R.id.CellNum3);

    //Firebase initialzating
        myAuth = FirebaseAuth.getInstance();

    }


    void registerUser(){

        //Main Details
        userNameStr=userName.getText().toString().trim();
        if(userNameStr.isEmpty()){
            userName.setError("User Name Required!");
            userName.requestFocus();
            return;
        }
        emailStr=email.getText().toString().trim();
        if(emailStr.isEmpty()){
            email.setError("E-mail Required!");
            email.requestFocus();
            return;
        }
        if(!validate(emailStr)){
            email.setError("Enter a valid E-mail!");
            email.requestFocus();
            return;
        }
        passwordStr=password.getText().toString().trim();
        if(passwordStr.isEmpty()){
            password.setError("Password Required!");
            password.requestFocus();
            return;
        }
        if(passwordStr.length()<6){
            password.setError("Too Short Password!");
            password.requestFocus();
            return;
        }

//        confirmPasswordStr=confirmPassword.getText().toString();
//                if(passwordStr.equals(confirmPasswordStr)){
//            areMatched=1;
//        }

        //Contacts Details (Don't forget to check validity!)
        final HashMap<String,String> favContacts =new HashMap<String ,String>();
        contact1NameStr=contact1Name.getText().toString().trim();
        if(contact1NameStr.isEmpty()){
            contact1Name.setError("Contact name Required!");
            contact1Name.requestFocus();
            return;
        }
        contact1NumStr=contact1Num.getText().toString().trim();
        if(contact1NumStr.isEmpty()){
            contact1Num.setError("Phone Number Required!");
            contact1Num.requestFocus();
            return;
        }
        if(contact1NumStr.length()!=10){
            contact1Num.setError("Phone Number Must be 10 digits!");
            contact1Num.requestFocus();
            return;
        }
        contact2NameStr=contact2Name.getText().toString().trim();
        if(contact2NameStr.isEmpty()){
            contact2Name.setError("Contact name Required!");
            contact2Name.requestFocus();
            return;
        }

        contact2NumStr=contact2Num.getText().toString().trim();
        if(contact2NumStr.isEmpty()){
            contact2Num.setError("Phone Number Required!");
            contact2Num.requestFocus();
            return;
        }
        if(contact2NumStr.length()!=10){
            contact2Num.setError("Phone Number Must be 10 digits!");
            contact2Num.requestFocus();
            return;
        }
        contact3NameStr=contact3Name.getText().toString().trim();
        if(contact3NameStr.isEmpty()){
            contact3Name.setError("Contact name Required!");
            contact3Name.requestFocus();
            return;
        }
        contact3NumStr=contact3Num.getText().toString().trim();
        if(contact3NumStr.isEmpty()){
            contact3Num.setError("Phone Number Required!");
            contact3Num.requestFocus();
            return;
        }
        if(contact3NumStr.length()!=10){
            contact3Num.setError("Phone Number Must be 10 digits!");
            contact3Num.requestFocus();
            return;
        }

        favContacts.put(contact1NameStr,contact1NumStr);
        favContacts.put(contact2NameStr,contact2NumStr);
        favContacts.put(contact3NameStr,contact3NumStr);


        myAuth.createUserWithEmailAndPassword(emailStr, passwordStr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success, update UI with the signed-in user's information
                            User user= new User(userNameStr,emailStr,passwordStr,favContacts);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
//                                                Toast.makeText(SignUpInit.this, "user Created!", Toast.LENGTH_SHORT)
//                                                        .show();
                                                Intent intent = new Intent(SignUpInit.this,MedicinesManegment.class);
                                                startActivity(intent);
                                            }else{
                                                Toast.makeText(SignUpInit.this,task.getException().getMessage(), Toast.LENGTH_SHORT)
                                                        .show();
                                            }
                                        }
                                    });
                        } else {
                            // If sign up fails, display a message to the user.
                            Toast.makeText(SignUpInit.this, "Authentication failed.", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }

    public void MedInit(View view){
        registerUser();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Exit");

        // set dialog message
        alertDialogBuilder
                .setMessage("Are you sure you want to Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        Intent intent = new Intent(SignUpInit.this,MainActivity.class);
                        startActivity(intent);

                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }
}





