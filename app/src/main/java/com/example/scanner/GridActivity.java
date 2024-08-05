//package com.example.scanner;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.cardview.widget.CardView;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//public class GridActivity extends AppCompatActivity {
//
//
//    private CardView roomstatus;
//    private CardView ROOMCleaning;
//    private CardView KeyStatus;
//    private CardView Checkout;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_grid);
//
//        roomstatus=findViewById(R.id.roomstatus);
//        ROOMCleaning=findViewById(R.id.ROOMCleaning);
//        KeyStatus=findViewById(R.id.KeyStatus);
//        Checkout=findViewById(R.id.Checkout);
//
//
//        roomstatus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(GridActivity.this, Roomstatus.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//        ROOMCleaning.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(GridActivity.this, Roomcleaning.class);
//                startActivity(intent);
//                finish();
//
//            }
//        });
//        KeyStatus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(GridActivity.this, Keystatus.class);
//                startActivity(intent);
//                finish();
//
//            }
//        });
//        Checkout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(GridActivity.this, Checkout.class);
//                startActivity(intent);
//                finish();
//
//            }
//        });
//
//
//
//
//    }
//}
package com.example.scanner;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class GridActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    private CardView roomstatus;
    private CardView ROOMCleaning;
    private CardView KeyStatus;
    private CardView Checkout;

    private ConnectivityReceiver connectivityReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_grid);

        roomstatus = findViewById(R.id.roomstatus);
        ROOMCleaning = findViewById(R.id.ROOMCleaning);
        KeyStatus = findViewById(R.id.KeyStatus);
        Checkout = findViewById(R.id.Checkout);

        roomstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInternetAndProceed(Roomstatus.class);
            }
        });
        ROOMCleaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInternetAndProceed(Roomcleaning.class);
            }
        });
        KeyStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInternetAndProceed(Keystatus.class);
            }
        });
        Checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInternetAndProceed(Checkout.class);
            }
        });
    }

    private void checkInternetAndProceed(Class<?> activityClass) {
        if (ConnectivityReceiver.isConnected(this)) {
            Intent intent = new Intent(GridActivity.this, activityClass);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(GridActivity.this, ErrorActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register the receiver to listen for network changes
        connectivityReceiver = new ConnectivityReceiver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(connectivityReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the receiver to stop listening for network changes
        unregisterReceiver(connectivityReceiver);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected) {
            Toast.makeText(this, "No internet connection. Please check your connection.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(GridActivity.this, ErrorActivity.class);
            startActivity(intent);
        }
    }
}
