//package com.example.scanner;
//
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//
//import androidx.appcompat.widget.SearchView;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Roomstatus extends AppCompatActivity {
//
//    private static RecyclerView recyclerView;
//    private static RoomAdapter roomAdapter;
//    private static List<RoomDetails> roomDetailsList; // Original list
//    private static List<RoomDetails> filteredRoomDetailsList; // Filtered list
//
//    private static ImageView arrowback;
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_roomstatus);
//
//        recyclerView = findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        arrowback=findViewById(R.id.arrowback);
//
//        arrowback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(Roomstatus.this,GridActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//        // Set up SearchView
//        SearchView searchView = findViewById(R.id.search_view);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                filter(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                filter(newText);
//                return false;
//            }
//        });
//
//        // Fetch room details
//        new FetchRoomDetailsTask().execute("http://www.anasumrah.com/anasws/api/users/details");
//    }
//
//    public class FetchRoomDetailsTask extends AsyncTask<String, Void, List<RoomDetails>> {
//
//        @Override
//        protected List<RoomDetails> doInBackground(String... urls) {
//            String urlString = urls[0];
//            HttpURLConnection urlConnection = null;
//            BufferedReader reader = null;
//
//            try {
//                URL url = new URL(urlString);
//                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestMethod("GET");
//                urlConnection.connect();
//
//                // Check for successful response
//                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                    reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//                    StringBuilder jsonResponse = new StringBuilder();
//                    String line;
//
//                    while ((line = reader.readLine()) != null) {
//                        jsonResponse.append(line);
//                    }
//
//                    // Convert JSON to List<RoomDetails>
//                    Gson gson = new Gson();
//                    return gson.fromJson(jsonResponse.toString(), new TypeToken<List<RoomDetails>>(){}.getType());
//                }
//            } catch (Exception e) {
//                Log.e("Roomcleaning", "Error fetching data", e);
//            } finally {
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (Exception e) {
//                        Log.e("Roomcleaning", "Error closing reader", e);
//                    }
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(List<RoomDetails> roomDetails) {
//            if (roomDetails != null) {
//                roomDetailsList = roomDetails; // Save original list
//                filteredRoomDetailsList = new ArrayList<>(roomDetails); // Initialize filtered list
//                roomAdapter = new RoomAdapter(filteredRoomDetailsList, Roomstatus.this);
//                recyclerView.setAdapter(roomAdapter);
//            }
//        }
//    }
//
//    // Filter method for the SearchView
//    private void filter(String text) {
//        filteredRoomDetailsList.clear();
//        if (text.isEmpty()) {
//            filteredRoomDetailsList.addAll(roomDetailsList);
//        } else {
//            for (RoomDetails room : roomDetailsList) {
//                if (String.valueOf(room.getRoom_no()).contains(text)) {
//                    filteredRoomDetailsList.add(room);
//                }
//            }
//        }
//        roomAdapter.notifyDataSetChanged();
//    }
//}
package com.example.scanner;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Roomstatus extends AppCompatActivity {

    private EditText roomNoEditText;
    private Button findButton;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RoomStatusAdapter roomStatusAdapter;
    private List<JSONObject> roomDetailsList;

    private TextView dataTextView;
    private ImageView arrowback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomstatus);

        roomNoEditText = findViewById(R.id.inputroom_no);
        findButton = findViewById(R.id.find);
        progressBar = findViewById(R.id.progressbarlogin);
        recyclerView = findViewById(R.id.recycler_view);
        arrowback = findViewById(R.id.arrowback);

        dataTextView = findViewById(R.id.data);
        roomDetailsList = new ArrayList<>();
        roomStatusAdapter = new RoomStatusAdapter(roomDetailsList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(roomStatusAdapter);

        arrowback.setOnClickListener(v -> {
            Intent intent = new Intent(Roomstatus.this, GridActivity.class);
            startActivity(intent);
        });

        findButton.setOnClickListener(v -> {
            String roomNo = roomNoEditText.getText().toString().trim();
            if (!roomNo.isEmpty()) {
                new FetchRoomStatusTask().execute(roomNo);
            } else {
                Toast.makeText(Roomstatus.this, "Please enter a room number", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class FetchRoomStatusTask extends AsyncTask<String, Void, String> {
        private int responseCode;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            dataTextView.setText(""); // Clear previous data
            dataTextView.setVisibility(View.GONE);
            findButton.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String roomNo = params[0];
            String apiUrl = "http://www.anasumrah.com/anasws/api/users/roomstatus/" + roomNo;

            try {
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                responseCode = connection.getResponseCode();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                reader.close();
                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.GONE);
            findButton.setVisibility(View.VISIBLE);
            if (result != null) {
                try {
                    if (responseCode == 200) {
                        JSONArray jsonArray = new JSONArray(result);
                        if (jsonArray.length() > 0) {
                            roomDetailsList.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject roomDetails = jsonArray.getJSONObject(i);
                                roomDetailsList.add(roomDetails);
                            }
                            roomStatusAdapter.notifyDataSetChanged();
                            dataTextView.setVisibility(View.GONE);
                        } else {
                            dataTextView.setText("Room not found");
                            dataTextView.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    } else {
                        dataTextView.setText("Room not found");
                        dataTextView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    dataTextView.setText("Error parsing data");
                    dataTextView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            } else {
                dataTextView.setText("An error occurred");
                dataTextView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        }
    }
}
