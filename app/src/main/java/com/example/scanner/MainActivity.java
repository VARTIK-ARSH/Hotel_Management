//package com.example.scanner;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//public class MainActivity extends AppCompatActivity {
//
//    private static final String TAG = "MainActivity";
//    private Button loginBtn;
//    private EditText usernameEdt, passwordEdt;
//    private TextView forgetpass;
//    private SharedPreferences sharedPreferences;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        loginBtn = findViewById(R.id.login);
//        usernameEdt = findViewById(R.id.username);
//        passwordEdt = findViewById(R.id.password);
//        forgetpass = findViewById(R.id.forgetpassword);
//        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//
//        if (isLoggedIn()) {
//            String response = getUserResponse();
//            Log.d(TAG, "User already logged in with response: " + response);
//            navigateToMainActivity2(response);
//        }
//
//        loginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (usernameEdt.getText().toString().isEmpty() || passwordEdt.getText().toString().isEmpty()) {
//                    Toast.makeText(MainActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
//                } else {
//                    new FetchData().execute();
//                }
//            }
//        });
//    }
//
//    private boolean isLoggedIn() {
//        return sharedPreferences.getBoolean("isLoggedIn", false);
//    }
//
//    private String getUserResponse() {
//        return sharedPreferences.getString("userResponse", "");
//    }
//
//    private void saveLoginState(boolean isLoggedIn, String userResponse) {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("isLoggedIn", isLoggedIn);
//        editor.putString("userResponse", userResponse);
//        editor.apply();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }
//
//    class FetchData extends AsyncTask<Void, Void, String> {
//
//        @Override
//        protected String doInBackground(Void... voids) {
//            String responseString = null;
//            HttpURLConnection client = null;
//            try {
//                URL url = new URL("http://www.anasumrah.com/anasws/api/users/" + usernameEdt.getText().toString() + "/" + passwordEdt.getText().toString());
//                client = (HttpURLConnection) url.openConnection();
//                client.setRequestMethod("GET");
//                client.setRequestProperty("Accept", "application/json");
//
//                int responseCode = client.getResponseCode();
//                Log.d(TAG, "Response code: " + responseCode);
//
//                if (responseCode == HttpURLConnection.HTTP_OK) {
//                    try (BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream(), "utf-8"))) {
//                        StringBuilder response = new StringBuilder();
//                        String responseLine;
//                        while ((responseLine = br.readLine()) != null) {
//                            response.append(responseLine.trim());
//                        }
//                        responseString = response.toString();
//                    }
//                } else {
//                    responseString = "HTTP error code: " + responseCode;
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                responseString = "Exception: " + e.getMessage();
//            } finally {
//                if (client != null) {
//                    client.disconnect();
//                }
//            }
//            return responseString;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            Log.d(TAG, "Server response: " + result);
//            try {
//                JSONObject user = new JSONObject(result);
//                String username = user.getString("username");
//                String password = user.getString("password");
//
//                if (usernameEdt.getText().toString().equals(username) && passwordEdt.getText().toString().equals(password)) {
//                    Log.d(TAG, "Login successful, saving user to SharedPreferences");
//                    saveLoginState(true, result);
//                    navigateToMainActivity2(result);
//                } else {
//                    Toast.makeText(MainActivity.this, "Login failed: Invalid username or password", Toast.LENGTH_SHORT).show();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                Toast.makeText(MainActivity.this, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                Log.e(TAG, "Exception: " + e.getMessage());
//            }
//        }
//    }
//
//    private void navigateToMainActivity2(String response) {
//        Intent intent = new Intent(MainActivity.this, GridActivity.class);
//        intent.putExtra("response", response);
//        startActivity(intent);
//        finish();
//    }
//}
package com.example.scanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    private static final String TAG = "MainActivity";
    private Button loginBtn;
    private EditText usernameEdt, passwordEdt;
    private TextView forgetpass;
    private SharedPreferences sharedPreferences;
    private ConnectivityReceiver connectivityReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn = findViewById(R.id.login);
        usernameEdt = findViewById(R.id.username);
        passwordEdt = findViewById(R.id.password);
        forgetpass = findViewById(R.id.forgetpassword);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        if (isLoggedIn()) {
            String response = getUserResponse();
            Log.d(TAG, "User already logged in with response: " + response);
            navigateToMainActivity2(response);
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usernameEdt.getText().toString().isEmpty() || passwordEdt.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                } else if (!ConnectivityReceiver.isConnected(MainActivity.this)) {
                    navigateToErrorActivity();
                } else {
                    new FetchData().execute();
                }
            }
        });

        // Register connectivity receiver
        connectivityReceiver = new ConnectivityReceiver(this);
        registerReceiver(connectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(connectivityReceiver);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected) {
            navigateToErrorActivity();
        }
    }

    private boolean isLoggedIn() {
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    private String getUserResponse() {
        return sharedPreferences.getString("userResponse", "");
    }

    private void saveLoginState(boolean isLoggedIn, String userResponse) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", isLoggedIn);
        editor.putString("userResponse", userResponse);
        editor.apply();
    }

    class FetchData extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String responseString = null;
            HttpURLConnection client = null;
            try {
                URL url = new URL("http://www.anasumrah.com/anasws/api/users/" + usernameEdt.getText().toString() + "/" + passwordEdt.getText().toString());
                client = (HttpURLConnection) url.openConnection();
                client.setRequestMethod("GET");
                client.setRequestProperty("Accept", "application/json");

                int responseCode = client.getResponseCode();
                Log.d(TAG, "Response code: " + responseCode);

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream(), "utf-8"))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        responseString = response.toString();
                    }
                } else {
                    responseString = "HTTP error code: " + responseCode;
                }

            } catch (Exception e) {
                e.printStackTrace();
                responseString = "Exception: " + e.getMessage();
            } finally {
                if (client != null) {
                    client.disconnect();
                }
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d(TAG, "Server response: " + result);
            try {
                JSONObject user = new JSONObject(result);
                String username = user.getString("username");
                String password = user.getString("password");

                if (usernameEdt.getText().toString().equals(username) && passwordEdt.getText().toString().equals(password)) {
                    Log.d(TAG, "Login successful, saving user to SharedPreferences");
                    saveLoginState(true, result);
                    navigateToMainActivity2(result);
                } else {
                    Toast.makeText(MainActivity.this, "Login failed: Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void navigateToMainActivity2(String response) {
        Intent intent = new Intent(MainActivity.this, GridActivity.class);
        intent.putExtra("response", response);
        startActivity(intent);
        finish();
    }

    private void navigateToErrorActivity() {
        Intent intent = new Intent(MainActivity.this, ErrorActivity.class);
        startActivity(intent);
        finish();
    }
}


