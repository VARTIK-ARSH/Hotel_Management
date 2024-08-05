package com.example.scanner;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.SearchView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Roomcleaning extends AppCompatActivity {

    private static RecyclerView recyclerView;
    private static RoomAdapter roomAdapter;
    private static List<RoomDetails> roomDetailsList; // Original list
    private static List<RoomDetails> filteredRoomDetailsList; // Filtered list

    private static ImageView arrowback;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomcleaning);

        recyclerView = findViewById(R.id.ROOMCleaningrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrowback=findViewById(R.id.arrowback);

        arrowback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Roomcleaning.this,GridActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // Set up SearchView
        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        // Fetch room details
        new FetchRoomDetailsTask().execute("http://www.anasumrah.com/anasws/api/users/details");
    }

    public class FetchRoomDetailsTask extends AsyncTask<String, Void, List<RoomDetails>> {

        @Override
        protected List<RoomDetails> doInBackground(String... urls) {
            String urlString = urls[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Check for successful response
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder jsonResponse = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        jsonResponse.append(line);
                    }

                    // Convert JSON to List<RoomDetails>
                    Gson gson = new Gson();
                    return gson.fromJson(jsonResponse.toString(), new TypeToken<List<RoomDetails>>(){}.getType());
                }
            } catch (Exception e) {
                Log.e("Roomcleaning", "Error fetching data", e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (Exception e) {
                        Log.e("Roomcleaning", "Error closing reader", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<RoomDetails> roomDetails) {
            if (roomDetails != null) {
                // Filter based on action value
                roomDetailsList = new ArrayList<>();
                for (RoomDetails room : roomDetails) {
                    if ("1".equals(String.valueOf(room.getAction()))) {
                        roomDetailsList.add(room);
                    }
                }

                filteredRoomDetailsList = new ArrayList<>(roomDetailsList); // Initialize filtered list
                roomAdapter = new RoomAdapter(filteredRoomDetailsList, Roomcleaning.this);
                recyclerView.setAdapter(roomAdapter);
            }
        }
    }

    // Filter method for the SearchView
    private void filter(String text) {
        filteredRoomDetailsList.clear();
        if (text.isEmpty()) {
            filteredRoomDetailsList.addAll(roomDetailsList);
        } else {
            for (RoomDetails room : roomDetailsList) {
                if (String.valueOf(room.getRoom_no()).contains(text)) {
                    filteredRoomDetailsList.add(room);
                }
            }
        }
        roomAdapter.notifyDataSetChanged();
    }
}
