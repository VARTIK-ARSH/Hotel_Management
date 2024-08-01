package com.example.scanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GridActivity extends AppCompatActivity {


    private CardView roomstatus;
    private CardView ROOMCleaning;
    private CardView KeyStatus;
    private CardView Checkout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_grid);

        roomstatus=findViewById(R.id.roomstatus);
        ROOMCleaning=findViewById(R.id.ROOMCleaning);
        KeyStatus=findViewById(R.id.KeyStatus);
        Checkout=findViewById(R.id.Checkout);


        roomstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GridActivity.this, Roomstatus.class);
                startActivity(intent);
            }
        });
        ROOMCleaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GridActivity.this, Roomcleaning.class);
                startActivity(intent);
            }
        });
        KeyStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GridActivity.this, Keystatus.class);
                startActivity(intent);
            }
        });
        Checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GridActivity.this, Checkout.class);
                startActivity(intent);
            }
        });




    }
}