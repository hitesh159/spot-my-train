package com.example.hitesh.spotmytrain;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.appus.splash.Splash;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Options> list;
    OptionsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f4ba33")));
        Splash.Builder splash = new Splash.Builder(this,getSupportActionBar());
        splash.setBackgroundColor(Color.parseColor("#f4ba33"));
        splash.setSplashImage(getResources().getDrawable(R.drawable.logo3));
        splash.perform();
        list=new ArrayList<Options>();
        adapter=new OptionsAdapter(getApplicationContext(),list);
        ListView OptionsList=(ListView)findViewById(R.id.options);
        OptionsList.setAdapter(adapter);


        list.add(0,new Options(R.drawable.logo,"Live status"));
        list.add(1,new Options(R.drawable.logo,"trains between stations"));
        list.add(2,new Options(R.drawable.logo,"station status"));
        adapter.notifyDataSetChanged();
        OptionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),Integer.toString(i),Toast.LENGTH_SHORT).show();
                if(i==0){
                    Intent intent=new Intent(MainActivity.this,LiveStatus.class);
                    startActivity(intent);
                }
                else if (i==1){
                    Intent intent=new Intent(MainActivity.this,TrainsBetweenStations.class);
                    startActivity(intent);
                }
                else if (i==2){
                    Intent intent=new Intent(MainActivity.this,StationStatus.class);
                    startActivity(intent);
                }
            }
        });
    }
}
