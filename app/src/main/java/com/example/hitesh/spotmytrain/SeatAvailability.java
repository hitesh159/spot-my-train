package com.example.hitesh.spotmytrain;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SeatAvailability extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_availability);
        ActionBar bar=getSupportActionBar();
        bar.setTitle("Seat availability");
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f4ba33")));
        Intent intent=getIntent();
    }
}
