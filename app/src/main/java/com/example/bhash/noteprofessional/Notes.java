package com.example.bhash.noteprofessional;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Notes extends AppCompatActivity {


    private int l1;
    private EditText ed1;
    private EditText ed2;
    private static final String TAG = "Notes";
    private int saveflag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        ed1=(EditText)findViewById(R.id.t1);
        ed2=(EditText)findViewById(R.id.t2);
        Intent i=getIntent();
        Bundle b=i.getExtras();
        if(b!=null)
        {
            ed1.setText(b.getString("head1"));
            ed2.setText(b.getString("text"));
            l1=b.getInt("location");
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.homemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save:
                String s=ed1.getText().toString();
                if(s.matches("")){
                    Log.d(TAG, "onOptionsItemSelected: noheadcame");
                    Toast.makeText(this,"please enter a heading",Toast.LENGTH_SHORT).show();
                }
                else{
                    saveit();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
public void saveit(){
    String s=ed1.getText().toString();
    if(s.matches("")){
        Log.d(TAG, "onOptionsItemSelected: noheadcame");
        Toast.makeText(this,"please enter a heading",Toast.LENGTH_SHORT).show();
    }
    else {
        Date d = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("E MM.dd.yyyy  hh:mm a");
        String s1 = ft.format(d);
        Intent i = new Intent();
        i.putExtra("head1", ed1.getText().toString());
        i.putExtra("date1", s1);
        i.putExtra("data1", ed2.getText().toString());
        i.putExtra("location", l1);
        saveflag = 1;
        Log.d(TAG, "saveit: Came");
        setResult(RESULT_OK, i);
        finish();
    }
}
    public void dontsave(){
        saveflag=1;
        Intent i1=new Intent();
        setResult(RESULT_CANCELED,i1);
        finish();
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if(saveflag==0) {
            AlertDialog.Builder save = new AlertDialog.Builder(this);
            save.setTitle("Save Notes");
            save.setMessage("Do You Want to Save?");
            save.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    saveit();
                }
            });
            save.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dontsave();

                }
            });
            AlertDialog deletedialog = save.create();
            deletedialog.show();
        }
        else{
            Log.d(TAG, "onPause: already saved");
        }

        //super.onBackPressed();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("result", l1);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        l1=savedInstanceState.getInt("result");



    }
}
