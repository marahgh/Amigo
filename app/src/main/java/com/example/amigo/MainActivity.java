package com.example.amigo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.DateFormatSymbols;


public class MainActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private String usernameStr;
    private String passwordStr;
    //Firebase
    private FirebaseAuth myAuth;
    private FirebaseDatabase database;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText)findViewById(R.id.username);
        password =(EditText)findViewById(R.id.password);
        myAuth = FirebaseAuth.getInstance();
        dialog=new ProgressDialog(MainActivity.this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(true);

    }





    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//      FirebaseUser currentUser = myAuth.getCurrentUser();
        if(myAuth.getCurrentUser()!=null){
            Intent intent = new Intent(MainActivity.this,Menu.class);
            startActivity(intent);

            //handle already logged in user
        }

//        updateUI(currentUser);
    }
    User user;
    void signIn(final String username,final String password){
        dialog.show();
        database.getInstance().getReference("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean found=false;
                for (DataSnapshot snapshot :dataSnapshot.getChildren()){
                    User user =snapshot.getValue(User.class);
                    if(user.getUserName().equals(username) && user.getPassword().equals(password)){
                        found=true;
                        myAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            Intent intent = new Intent(MainActivity.this,Menu.class);
                                            startActivity(intent);
                                        }else{
                                            dialog.cancel();
                                            dialog.dismiss();
                                            Toast.makeText(MainActivity.this,"Authentication Error",Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                    }
                }
                if(!found){
                    dialog.cancel();
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this,"Wrong password or username!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void logInFunc(View view){
        usernameStr=username.getText().toString();
        if(usernameStr.isEmpty()){
            username.setError("User Name Required!");
            username.requestFocus();
            return;
        }
        passwordStr=password.getText().toString();
        if(passwordStr.isEmpty()){
            password.setError("Password Required!");
            password.requestFocus();
            return;
        }

        signIn(usernameStr,passwordStr);
    }








    public void SignUpFunc(View view){
        Intent intent = new Intent(this,SignUpInit.class);
        startActivity(intent);
    }



    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle("Exit");

        // set dialog message
        alertDialogBuilder
                .setMessage("Are you sure you want to Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        finishAffinity();

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
