package com.example.hitesh.spotmytrain;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class TrainsBetweenStations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trains_between_stations);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f4ba33")));
        final AutoCompleteTextView source=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        final SuggestionAdapter adapter=new SuggestionAdapter(this,source.getText().toString());
        source.setAdapter(adapter);
        source.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item=  adapter.getCode(i);
                Toast.makeText(getApplicationContext(),item,Toast.LENGTH_SHORT).show();
            }
        });

        final AutoCompleteTextView destination=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView2);
        final SuggestionAdapter adapter1=new SuggestionAdapter(this,destination.getText().toString());
        destination.setAdapter(adapter1);
        destination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item=adapter.getCode(i);
                Toast.makeText(getApplicationContext(),item,Toast.LENGTH_SHORT).show();
            }
        });

        Button search=(Button)findViewById(R.id.button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean pass1=false;
                boolean pass2=false;
                String str = source.getText().toString();
                int pos1=0,pos2=0;
                ListAdapter listAdapter = source.getAdapter();
                for(int i = 0; i < listAdapter.getCount(); i++) {
                    String temp = listAdapter.getItem(i).toString();
                    if(str.compareTo(temp) == 0) {
                        pos1=i;
                        pass1=true;
                        break;
                    }
                }
                if (!pass1)
                source.setText("");
                 str=destination.getText().toString();
                 listAdapter = destination.getAdapter();
                for(int i = 0; i < listAdapter.getCount(); i++) {
                    String temp = listAdapter.getItem(i).toString();
                    if(str.compareTo(temp) == 0) {
                        pos2=i;
                        pass2=true;
                        break;
                    }
                }
                if (!pass2)
                destination.setText("");
                if (!pass1||!pass2){
                    Toast.makeText(getApplicationContext(),"invalid source or destination",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent=new Intent(getApplicationContext(),TrainsList.class);
                intent.putExtra("source",adapter.getCode(pos1));
                intent.putExtra("destination",adapter1.getCode(pos2));
                startActivity(intent);
            }
        });
    }

}
