package com.example.hitesh.spotmytrain;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LiveStatus extends AppCompatActivity {

    EditText searchNumber;
    Button Search;
    ProgressBar progressBar;
    TableLayout tableLayout;
    TextView notrain;

    class GetLiveStatus extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                Log.i("result", result);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.INVISIBLE);

            if (s != null) {
                tableLayout.setVisibility(View.VISIBLE);
                try {
                    JSONObject status = new JSONObject(s);
                    String current_station = status.getString("current_station");
                    Log.i("current", current_station);
                    TextView name = (TextView) findViewById(R.id.name);
                    name.setText((new JSONObject(status.getString("train"))).getString("name"));
                    TextView currentStation = (TextView) findViewById(R.id.currentStation);
                    currentStation.setText((new JSONObject(current_station).getString("name")) + "(" + (new JSONObject(current_station).getString("code")) + ")");
                    TextView position = (TextView) findViewById(R.id.position);
                    position.setText(status.getString("position"));
                    int p = -1;
                    String pos = status.getString("position");
                    if (!pos.contains("late") || pos.contains("0 minutes")) {

                        position.setTextColor(Color.parseColor("#65fc14"));
                    } else {
                        position.setTextColor(Color.parseColor("#fc1919"));
                    }
                    JSONArray route = new JSONArray(status.getString("route"));
                    JSONObject temp = null;
                    for (int i = route.length() - 1; i >= 0; i--) {
                        temp = route.getJSONObject(i);
                        if (temp.getBoolean("has_arrived")) {
                            p = i;
                            break;
                        }
                    }
                    if (p != route.length() - 1) {
                        TextView next = (TextView) findViewById(R.id.nextStation);
                        TextView eta = (TextView) findViewById(R.id.eta);
                        TextView etd = (TextView) findViewById(R.id.etd);
                        temp = route.getJSONObject(p + 1);
                        String nextStation = temp.getString("station");
                        eta.setText(temp.getString("actarr"));
                        etd.setText(temp.getString("actdep"));
                        next.setText((new JSONObject(nextStation).getString("name")) + "(" + (new JSONObject(nextStation).getString("code")) + ")");
                    } else {
                        TextView next = (TextView) findViewById(R.id.nextStation);
                        next.setText("reached destination");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    notrain = (TextView) findViewById(R.id.notrain);
                    notrain.setVisibility(View.VISIBLE);
                }
            } else {
                notrain = (TextView) findViewById(R.id.notrain);
                tableLayout.setVisibility(View.INVISIBLE);
                notrain.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_status);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f4ba33")));
        bar.setTitle("Live Status");
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Search = (Button) findViewById(R.id.searchButton);
        searchNumber = (EditText) findViewById(R.id.searchNumber);
        notrain = (TextView) findViewById(R.id.notrain);
        tableLayout = (TableLayout) findViewById(R.id.table);
        tableLayout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchNumber.toString() != "") {
                    progressBar.setVisibility(View.VISIBLE);
                    notrain.setVisibility(View.INVISIBLE);
                    Calendar c = Calendar.getInstance();
                    System.out.println("Current time => " + c.getTime());

                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String formattedDate = df.format(c.getTime());
                    Log.i("date", formattedDate);
                    GetLiveStatus status = new GetLiveStatus();

                    status.execute("https://api.railwayapi.com/v2/live/train/" + searchNumber.getText().toString() + "/date/" + formattedDate + "/apikey/r1lwgecbag/");
                }
            }
        });
    }
}
