package com.example.amigo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MedNotification extends AppCompatActivity {
    TextView medicineName,pillsNum,warningMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_notification);
        String medicineNameStr= getIntent().getStringExtra("medicineName");
        String pillsNumStr=getIntent().getStringExtra("pillsNum") + " Pills";
        String warningMsgStr="Be Careful: "+getIntent().getStringExtra("warning");
        medicineName = findViewById(R.id.pills_name_notification);
        pillsNum = findViewById(R.id.pills_num_notificaion);
        warningMsg = findViewById(R.id.pills_warning_notification);
        medicineName.setText(medicineNameStr);
        pillsNum.setText(pillsNumStr);
        warningMsg.setText(warningMsgStr);
    }

}
