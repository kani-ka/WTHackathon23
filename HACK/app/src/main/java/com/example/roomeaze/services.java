package com.example.roomeaze;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class services extends AppCompatActivity implements View.OnClickListener {

    private ImageView cleanImageView, electImageView, carpImageView, wifiImageView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        // Initialize the ImageViews
        cleanImageView = findViewById(R.id.imageView3);
        electImageView = findViewById(R.id.imageView4);
        carpImageView = findViewById(R.id.imageView5);
        wifiImageView = findViewById(R.id.imageView6);

        // Set onClickListeners for each ImageView
        cleanImageView.setOnClickListener(this);
        electImageView.setOnClickListener(this);
        carpImageView.setOnClickListener(this);
        wifiImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Check which ImageView was clicked
        if (v == cleanImageView ) {
            // Launch CleanActivity
            Intent intent = new Intent(services.this, Booking.class);
            startActivity(intent);
        } else if (v == electImageView) {
            // Launch ElectActivity
            Intent intent = new Intent(services.this, Booking.class);
            startActivity(intent);
        } else if (v == carpImageView) {
            // Launch CarpActivity
            Intent intent = new Intent(services.this, Booking.class);
            startActivity(intent);
        } else if (v == wifiImageView) {
            // Launch WifiActivity
            Intent intent = new Intent(services.this, Booking.class);
            startActivity(intent);
        }
    }
}
