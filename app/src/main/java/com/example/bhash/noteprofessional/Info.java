package com.example.bhash.noteprofessional;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Info extends AppCompatActivity {
    private TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        t=(TextView)findViewById(R.id.t1);
        t.setText("Multi-Notes"+"\n"+"© July 2017 Bhasheyam "+"\n"+"Version 1.0");
    }
}
