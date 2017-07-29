package com.example.bhash.noteprofessional;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by bhash on 22-07-2017.
 */

public class Displayer extends RecyclerView.ViewHolder{
    public TextView t1;
    public TextView t2;
    public   TextView t3;
    private static final String TAG = "Displayer";

    public Displayer(View v){
        super(v);
        Log.d(TAG, "Displayer: Came");
        t1=(TextView)v.findViewById(R.id.Heading);
        t2=(TextView)v.findViewById(R.id.Date);
        t3=(TextView)v.findViewById(R.id.Content);

    }
}
