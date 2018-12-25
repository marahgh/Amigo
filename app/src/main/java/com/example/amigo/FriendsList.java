package com.example.amigo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import static android.widget.AbsListView.CHOICE_MODE_MULTIPLE;
import static android.widget.AbsListView.CHOICE_MODE_SINGLE;

public class FriendsList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ListView listView=(ListView) findViewById(R.id.listView);
        listView.setChoiceMode(CHOICE_MODE_MULTIPLE);
        String [] items={"Moanes Mrowat","Eman Ayoub","Hadeel Ghoummed","Mohammad Othman"};
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,R.layout.row_layout,R.id.check,items);
        listView.setAdapter(adapter);
//        ActionBar acionBar= getSupportActionBar();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }



};
