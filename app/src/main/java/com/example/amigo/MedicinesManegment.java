package com.example.amigo;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.widget.TextView;
import java.util.Calendar;

import com.google.android.gms.common.internal.Objects;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MedicinesManegment extends AppCompatActivity {
    private EditText MedicineName,PillsNum,Warning;
    String MedicineNameStr,WarningStr,PillsNumStr,TimeStr;
    private FirebaseAuth myAuth;
    private FirebaseUser user;
    private User currentUser;
    private String uid;
    private int hours,minutes;
    private HashMap<String,Medicine> med;
    private Medicine currMed;
    private HashMap<String,Medicine> newMed;
    private Context context = this;


    // int pillsNumToInt=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicines_manegment);
        MedicineName=findViewById(R.id.nameText);
        PillsNum=findViewById(R.id.dosage1);
        Warning=findViewById(R.id.warning);
        TimeStr="";
    }
    public void Finish(View view){
        //Getting and validating the medicine details
        MedicineNameStr=MedicineName.getText().toString().trim();
        if(MedicineNameStr.isEmpty()){
            MedicineName.setError("Medicine Name Required!");
            MedicineName.requestFocus();
            return;
        }
        WarningStr=Warning.getText().toString().trim();
        PillsNumStr=PillsNum.getText().toString().trim();
        if(PillsNumStr.isEmpty()){
            PillsNum.setError("Medicine Name Required!");
            PillsNum.requestFocus();
            return;
        }
        int pillsNumber=Integer.parseInt(PillsNumStr);
        if(pillsNumber<=0){
            PillsNum.setError("Pills number must be 1 or more!");
            PillsNum.requestFocus();
            return;
        }
        //All is valid, create the Medecine and update the medicines' list
        newMed=new HashMap<String, Medicine>();
        currMed = new Medicine(MedicineNameStr,pillsNumber,TimeStr,WarningStr);
        myAuth = FirebaseAuth.getInstance();
        user=myAuth.getCurrentUser();
        uid=user.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                med=dataSnapshot.getValue(User.class).getMyMedicine();
                editNewHash();
                newMed.put(currMed.getName(),currMed);
                FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("myMedicine").setValue(newMed);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //TimeStr
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hours);
        calendar.set(Calendar.MINUTE,minutes);
        calendar.set(Calendar.SECOND,0);
        Intent intent = new Intent("com.example.amigo.alarm");
        Bundle extras = new Bundle();
        extras.putString("medicineName",currMed.getName());
        extras.putString("pillsNum",String.valueOf(currMed.getPillsNum()));
        extras.putString("warning",currMed.getWarningMsg());
        intent.putExtras(extras);
        intent.setFlags(0 );
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        Intent finishIntent = new Intent(this,Menu.class);
        startActivity(finishIntent);


    }


    //Time piker button
    public void timePickFunc(View view){
        TimePickerDialog timePiker;
        timePiker = new TimePickerDialog(MedicinesManegment.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                TimeStr = "Time Picked: "+hourOfDay+":"+minute;
                hours=hourOfDay;
                minutes=minute;
                        Toast.makeText(MedicinesManegment.this, TimeStr, Toast.LENGTH_SHORT).show();
            }
        },0,0,true);
        timePiker.setTitle(null);
        timePiker.show();
    }

    void editNewHash() {
        Iterator it = med.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            newMed.put(pair.getKey().toString(),(Medicine) pair.getValue());
        }
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
                        Intent intent = new Intent(MedicinesManegment.this,Menu.class);
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
