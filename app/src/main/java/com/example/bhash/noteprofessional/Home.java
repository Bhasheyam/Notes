package com.example.bhash.noteprofessional;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Home extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener  {

    public ArrayList<Datastore>  maindata;
    private RecyclerView displayarea;
    private loadHandler adapter;
    public static final int updated=1;
    public static final int updated1=2;
    private static final String TAG = "Home";
    private boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        maindata = new ArrayList<Datastore>();
        displayarea = (RecyclerView) findViewById(R.id.Recycle);
        adapter = new loadHandler(maindata, this);
        displayarea.setAdapter(adapter);
        displayarea.setLayoutManager(new LinearLayoutManager(this));
        if (flag) {
            Toast.makeText(this, "loading Please Wait", Toast.LENGTH_LONG).show();
        } else {
            new seperatetask(this).execute("storage.json");
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notesmenu,menu);
        return true;
    }
    @Override
    public void onClick(View v) {
        Intent i = new Intent(getApplicationContext(),Notes.class);
        int temp=displayarea.getChildLayoutPosition(v);
        Log.d(TAG, "onClick: "+temp);
        Datastore d=maindata.get(temp);
        i.putExtra("head1",d.getHead());
        i.putExtra("text",d.getData());
        i.putExtra("location",(int)temp);
        startActivityForResult(i,updated);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == updated) {
            switch(resultCode) {
                case RESULT_OK:
                    Log.d(TAG, "onActivityResultreturn: " + data.getIntExtra("location", 0));
                    int tem = data.getIntExtra("location", 0);
                    maindata.remove(tem);
                    Datastore dd = new Datastore();
                    dd.setHead(data.getStringExtra("head1"));
                    dd.setDate(data.getStringExtra("date1"));
                    dd.setData(data.getStringExtra("data1"));
                    maindata.add(0, dd);
                    adapter.notifyDataSetChanged();
                case RESULT_CANCELED:
                    Log.d(TAG, "onActivityResult: Nochangemade");
                    default:
                        Log.d(TAG, "onActivityResult: noResult");

            }
        }
        else if (requestCode == updated1) {
                if (resultCode == RESULT_OK) {
                    Log.d(TAG, "onActivityResult: Cameonsave");
                    Datastore dd=new Datastore();
                    dd.setHead(data.getStringExtra("head1"));
                    Log.d(TAG, "onActivityResult: "+data.getStringExtra("head1"));
                    dd.setDate(data.getStringExtra("date1"));
                    dd.setData(data.getStringExtra("data1"));
                    maindata.add(0,dd);
                    adapter.notifyDataSetChanged();

                }
            else{
                    Log.d(TAG, "onActivityResult:resultcodfault,updated1");
                }
            } else {
            Log.d(TAG, "onActivityResult:intentcodfault");

        }

    }

    public boolean onLongClick(View v) {
        final int temp=displayarea.getChildLayoutPosition(v);
        AlertDialog.Builder delete=new AlertDialog.Builder(this);

        delete.setPositiveButton("Delete",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            deleteit(temp);
            }
        });
        delete.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        delete.setTitle("Delete Notes");
        delete.setMessage("Do You Want to delete?");
        AlertDialog deletedialog=delete.create();
        deletedialog.show();
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add1:

                Intent i = new Intent(getApplicationContext(),Notes.class);
                startActivityForResult(i,updated1);

                break;
            case R.id.info1:
                Intent i1 = new Intent(getApplicationContext(),Info.class);
                startActivity(i1);

        }
        return super.onOptionsItemSelected(item);
    }
    public void deleteit(int d){
        maindata.remove(d);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        Saveit();
        Log.d(TAG, "onPause: Came and saved the file");
        super.onPause();
    }



    public void Saveit(){
         try {
    FileOutputStream out = getApplicationContext().openFileOutput("storage.json", Context.MODE_PRIVATE);
    JsonWriter write=new JsonWriter(new OutputStreamWriter(out,"UTF-8"));
    write.setIndent(" ");
    write.beginArray();
    for(Datastore de:maindata){
        saving(write,de);
    }
   write.endArray();
             Log.d(TAG, "Saveit: save happend"+write.toString());

    write.close();
    }
      catch(Exception e){
          e.getStackTrace();

        }
        }
public void saving(JsonWriter r,Datastore dr)throws IOException{
    r.beginObject();
    Log.d(TAG, "saving: saving this object" +dr.toString());
    r.name("head").value(dr.getHead());
    r.name("date").value(dr.getDate());
    r.name("data").value(dr.getData());
    Log.d(TAG, "saving:writing "+dr.getData()+" "+dr.getHead());
    r.endObject();
}
class seperatetask extends AsyncTask<String,Datastore,String>
    {
        private Context c1;

        public seperatetask(Context c){
            c1=c;
        }
        @Override
        protected String doInBackground(String... params) {
            try{
                flag=true;
                Log.d(TAG, "doInBackground: came"+params[0]);
                InputStream getit = c1.openFileInput(params[0]);
                JsonReader read=new JsonReader(new InputStreamReader(getit,"UTF-8"));
                read.beginArray();
                while(read.hasNext()){

                    maindata.add(objectload(read));
                }
                read.endArray();
                adapter.notifyDataSetChanged();
                flag=false;
                return "Welcome";
            }
            catch (FileNotFoundException f){
                Log.d(TAG, "doInBackground: nofile");
                return "welcome To Notes";
            }
            catch (Exception e){
                e.getStackTrace();
                return " error";
            }

        }
        public Datastore objectload(JsonReader r)throws IOException {
            Datastore dd = new Datastore();
            r.beginObject();
            while (r.hasNext()) {
                Log.d(TAG, "objectload: came1");

                if (r.nextName().equals("head")) {
                    String s = r.nextString();
                    Log.d(TAG, "objectload:reading json " + s);
                    dd.setHead(s);
                }

                if (r.nextName().equals("date")) {
                    String s1 = r.nextString();
                    Log.d(TAG, "objectload:reading json " + s1);
                    dd.setDate(s1);
                }
                if (r.nextName().equals("data")) {
                    String s2 = r.nextString();
                    Log.d(TAG, "objectload:reading json " + s2);
                    dd.setData(s2);
                } else {
                    Log.d(TAG, "objectload: came insede skip");
                    r.skipValue();
                }
            }
            r.endObject();
            Log.d(TAG, "objectload: cameand working");
            return dd;
        }




        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: sucess received");
            Toast.makeText(c1,s,Toast.LENGTH_SHORT).show();
        }
    }

}