package com.example.hitesh.spotmytrain;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Toast;

public class StationStatus extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {

    Button search;
    Snackbar snackbar;
    private NetworkStateReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_status);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f4ba33")));
        bar.setTitle("station status");
        receiver=new NetworkStateReceiver();
        receiver.addListener(this);
        this.registerReceiver(receiver,new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        final AutoCompleteTextView textView=(AutoCompleteTextView)findViewById(R.id.station_name);
        final SuggestionAdapter adapter=new SuggestionAdapter(this,textView.getText().toString());
        textView.setAdapter(adapter);
         search=findViewById(R.id.search_station);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean pass=false;
                String str=textView.getText().toString();
                int pos=0;
                ListAdapter listAdapter=textView.getAdapter();
                for(int i = 0; i < listAdapter.getCount(); i++) {
                    String temp = listAdapter.getItem(i).toString();
                    if(str.compareTo(temp) == 0) {
                        pos=i;
                        pass=true;
                        break;
                    }
                }
                if (!pass){
                    textView.setText("");
                    Toast.makeText(getApplicationContext(),"invalid station",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent=new Intent(StationStatus.this,TrainsAtStation.class);
                intent.putExtra("station",adapter.getCode(pos));
                startActivity(intent);
            }
        });
    }

    @Override
    public void networkAvailable() {
        search.setEnabled(true);
        if (snackbar!=null&&snackbar.isShown()){
            snackbar.dismiss();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        receiver.removeListener(this);
        this.unregisterReceiver(receiver);
    }

    @Override
    public void networkUnavailable() {
        Log.i("conn","unavailable");
         snackbar=Snackbar.make(findViewById(R.id.stationstatus),"no internet connection", BaseTransientBottomBar.LENGTH_INDEFINITE);
        search.setEnabled(false);
        snackbar.show();
    }
}
