package com.example.amigo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.widget.Button;

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

public class ContactsToCall extends AppCompatActivity {
    private Button contact1,contact2,contact3;
    private FirebaseAuth myAuth;
    private FirebaseUser user;
    private User currentUser;
    private String uid;
    private HashMap<String,String> favContacts;
    private String contact1Name,contact2Name,contact3Name;
    private String contact1Num,contact2Num,contact3Num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_to_call);
        contact1 = findViewById(R.id.contact1);
        contact2 = findViewById(R.id.contact2);
        contact3 = findViewById(R.id.contact3);

        myAuth = FirebaseAuth.getInstance();
        user=myAuth.getCurrentUser();
        uid=user.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                favContacts=dataSnapshot.getValue(User.class).getFavContacts();
                getContactsData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//        contact1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View arg0) {
//                Uri number =Uri.parse("tel:+97249984054");
//                Intent callIntent = new Intent(Intent.ACTION_CALL,number);
//                if (ActivityCompat.checkSelfPermission(ContactsToCall.this,
//                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }
//                startActivity(callIntent);
//            }
//        });
    }


    void getContactsData(){
        Iterator it = favContacts.entrySet().iterator();
        int i=1;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if(i==1){
                contact1Name=pair.getKey().toString();
                contact1Num=pair.getValue().toString();
                contact1.setText(contact1Name);
            }
            if(i==2){
                contact2Name=pair.getKey().toString();
                contact2Num=pair.getValue().toString();
                contact2.setText(contact2Name);
            }
            if(i==3){
                contact3Name=pair.getKey().toString();
                contact3Num=pair.getValue().toString();
                contact3.setText(contact3Name);
            }
            i++;
        }
    }

    //Call contact1
    public void call1(View view) {
        Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts(
                "tel", contact1Num, null));
        startActivity(phoneIntent);
    }

    //Call contact2
    public void call2(View view) {
        Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts(
                "tel", contact2Num, null));
        startActivity(phoneIntent);
    }

    //Call contact3
    public void call3(View view) {
        Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts(
                "tel", contact3Num, null));
        startActivity(phoneIntent);
    }

}

