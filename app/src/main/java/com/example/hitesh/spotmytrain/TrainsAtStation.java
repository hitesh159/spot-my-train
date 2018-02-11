package com.example.hitesh.spotmytrain;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class TrainsAtStation extends AppCompatActivity {

    ListView listView;
    List<TrainInfo> trains;
    ProgressBar progressBar;
    TrainInfoAdapter adapter;
    class GetContent extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String result="";
            try {
                URL url=new URL(strings[0]);
                HttpsURLConnection connection=(HttpsURLConnection) url.openConnection();
                InputStream inputStream=connection.getInputStream();
                InputStreamReader reader=new InputStreamReader(inputStream);
                int data=reader.read();
                while (data!=-1){
                    char current=(char)data;
                    result+=current;
                    data=reader.read();
                }
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
                JSONArray arr=new JSONArray(res.getString("trains"));
                for (int i=0;i<arr.length();i++){
                    JSONObject temp=arr.getJSONObject(i);
                    trains.add(i,new TrainInfo(temp.getString("name"),temp.getString("number"),temp.getString("delaydep"),temp.getString("schdep"),temp.getString("scharr"),"ETA:"+temp.getString("actarr"),"ETD:"+temp.getString("actdep"),""));
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
        setContentView(R.layout.activity_trains_at_station);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f4ba33")));
        bar.setTitle("trains list");
        Intent intent=getIntent();
        String code=intent.getStringExtra("station");
        trains=new ArrayList<>();
        adapter=new TrainInfoAdapter(this,trains);
        progressBar=findViewById(R.id.progressBar2);
        listView=findViewById(R.id.trains);
        listView.setVisibility(View.INVISIBLE);
        listView.setAdapter(adapter);
        progressBar.setVisibility(View.VISIBLE);
        GetContent task=new GetContent();
        task.execute("https://api.railwayapi.com/v2/arrivals/station/"+code+"/hours/2/apikey/r1lwgecbag/");
    }
}
