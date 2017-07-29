package com.example.bhash.noteprofessional;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

/**
 * Created by bhash on 22-07-2017.
 */

public class loadHandler extends RecyclerView.Adapter<Displayer>{

    private List<Datastore> accessdata;
    private Home ref;
    private static final String TAG = "loadHandler";

    public loadHandler(List<Datastore> accessdata, Home act) {
        this.accessdata = accessdata;
        this.ref = act;
        Log.d(TAG, "loadHandler: Came");
    }

    @Override
    public Displayer onCreateViewHolder( ViewGroup parent, int viewType) {
        View datalaay= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviews,parent,false);
        datalaay.setOnClickListener(ref);
        datalaay.setOnLongClickListener(ref);
        Log.d(TAG, "onCreateViewHolder: Came");
        return new Displayer(datalaay);

    }

    @Override
    public void onBindViewHolder(Displayer holder, int position) {
        Datastore dat=accessdata.get(position);
        holder.t1.setText(dat.getHead());
        Log.d(TAG, "onBindViewHolder: "+dat.getHead());
        holder.t2.setText(dat.getDate());
        if(dat.getData().length()>80){
            String s=dat.getData().substring(0,79)+"...";
            holder.t3.setText(s);

        }else {
            holder.t3.setText(dat.getData());
        }
        Log.d(TAG, "onBindViewHolder: Came");
    }

    @Override
    public int getItemCount() {
        return accessdata.size();
    }
}
