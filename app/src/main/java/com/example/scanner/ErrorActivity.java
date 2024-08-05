package com.example.scanner;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ErrorActivity extends AppCompatActivity {

    private TextView errorMsg;
    private Button retryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        errorMsg = findViewById(R.id.error_msg);
        retryButton = findViewById(R.id.retry_button);

        errorMsg.setText("No internet connection. Please check your connection and try again.");

        retryButton.setOnClickListener(v -> {
            if (isConnected()) {
                Intent intent = new Intent(ErrorActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                errorMsg.setText("Still no internet connection. Please check your connection and try again.");
            }
        });
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
