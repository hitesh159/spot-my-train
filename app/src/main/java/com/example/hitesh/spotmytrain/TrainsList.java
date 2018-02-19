package com.example.hitesh.spotmytrain;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class TrainsList extends AppCompatActivity {

    ListView listView;
    List<TrainInfo> trains;
    ProgressBar progressBar;
    TrainInfoAdapter adapter;
    class GetTrains extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String result="";
            try {
                URL url=new URL(strings[0]);
                HttpsURLConnection connection=(HttpsURLConnection)url.openConnection();
                InputStream inputStream=connection.getInputStream();
                InputStreamReader reader=new InputStreamReader(inputStream);
                int data=reader.read();
                while (data!=-1){
                    char current=(char)data;
                    result+=current;
                    data=reader.read();
                }
                Log.i("result",result);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                progressBar.setVisibility(View.INVISIBLE);
                listView.setVisibility(View.VISIBLE);
                JSONObject res=new JSONObject(s);
                JSONArray train=new JSONArray(res.getString("trains"));
                trains.clear();
                for (int i=0;i<train.length();i++){
                    JSONObject temp=train.getJSONObject(i);
                    String from=(new JSONObject(temp.getString("from_station"))).getString("code");
                    String to=(new JSONObject(temp.getString("to_station"))).getString("code");
                    trains.add(i,new TrainInfo(temp.getString("name"),temp.getString("number"),from+"-"+to,temp.getString("src_departure_time"),temp.getString("dest_arrival_time"),"source:"+(new JSONObject(temp.getString("from_station"))).getString("name"),"destination:"+(new JSONObject(temp.getString("to_station"))).getString("name"),""));
                }
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trains_list);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f4ba33")));
        Intent intent=getIntent();
        Toast.makeText(getApplicationContext(),intent.getStringExtra("source"),Toast.LENGTH_SHORT).show();
        String source=intent.getStringExtra("source");
        String destination=intent.getStringExtra("destination");
        listView=(ListView) findViewById(R.id.trainslist);
        listView.setVisibility(View.INVISIBLE);
        trains=new ArrayList<>();
        progressBar=(ProgressBar)findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.VISIBLE);
        //trains.add(0,new TrainInfo("houu","houu","houu","houu","houu","houu","houu","houu"));
        adapter=new TrainInfoAdapter(getApplicationContext(),trains);
        listView.setAdapter(adapter);
        GetTrains task=new GetTrains();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());
        task.execute("https://api.railwayapi.com/v2/between/source/"+source+"/dest/"+destination+"/date/"+formattedDate+"/apikey/e5ymegxrdf/");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1=new Intent(getApplicationContext(),SeatAvailability.class);
                intent1.putExtra("number",trains.get(i).getNumber());
                startActivity(intent1);
            }
        });
    }
}
