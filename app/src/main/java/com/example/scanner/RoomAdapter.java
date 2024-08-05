//package com.example.scanner;
//
//import android.content.Context;
//import android.os.AsyncTask;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.io.BufferedWriter;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.List;
//public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
//
//    private List<RoomDetails> roomList;
//    private Context context;
//
//    public RoomAdapter(List<RoomDetails> roomList, Context context) {
//        this.roomList = roomList;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.cleaningrecyclerview, parent, false);
//        return new RoomViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
//        RoomDetails room = roomList.get(position);
//        holder.roomNoTextView.setText(String.valueOf(room.getRoom_no()));
//
//        if ("1".equals(String.valueOf(room.getAction()))) {
//            holder.actionButton.setEnabled(false);
//            holder.actionButton.setText(" ");
//            holder.actionButton.setVisibility(View.GONE); // Hide the checkout button
//
//            holder.cleanButton.setVisibility(View.VISIBLE); // Show the clean button
//            if ("1".equals(String.valueOf(room.getClean_status()))) {
//                holder.cleanButton.setEnabled(false);
//                holder.cleanButton.setText(" ");
//                holder.cleanButton.setBackgroundResource(R.drawable.btnbackground);
//            } else {
//                holder.cleanButton.setEnabled(true);
//                holder.cleanButton.setText("Clean");
//                holder.cleanButton.setBackgroundResource(R.drawable.button_background_completed);
//
//                holder.cleanButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int currentPosition = holder.getAdapterPosition(); // Get the current position
//                        Log.d("RoomAdapter", "room no: " + room.getRoom_no() + ", hotel ID: " + room.getITD_HOTEL_ID());
//                        new PostRequestTask2(currentPosition).execute(room.getRoom_no(), room.getITD_HOTEL_ID());
//                    }
//                });
//            }
//        } else {
//            holder.actionButton.setEnabled(true);
//            holder.actionButton.setText("Checkout");
//            holder.actionButton.setBackgroundResource(R.drawable.btnbackground);
//            holder.actionButton.setVisibility(View.VISIBLE); // Show the checkout button
//            holder.cleanButton.setVisibility(View.GONE); // Hide the clean button
//
//            holder.actionButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int currentPosition = holder.getAdapterPosition();
//                    new PostRequestTask(currentPosition).execute(room.getRoom_no(), room.getitd_ID());
//                }
//            });
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return roomList.size();
//    }
//
//    public static class RoomViewHolder extends RecyclerView.ViewHolder {
//        TextView roomNoTextView;
//        Button actionButton;
//        Button cleanButton;
//
//        public RoomViewHolder(@NonNull View itemView) {
//            super(itemView);
//            roomNoTextView = itemView.findViewById(R.id.room_no_text);
//            actionButton = itemView.findViewById(R.id.action_button);
//            cleanButton = itemView.findViewById(R.id.clean_action_button);
//        }
//    }
//
//    private class PostRequestTask extends AsyncTask<Integer, Void, Boolean> {
//
//        private int position; // Store the position of the item to be removed
//
//        public PostRequestTask(int position) {
//            this.position = position; // Initialize with the position
//        }
//        @Override
//        protected Boolean doInBackground(Integer... params) {
//            int roomNo = params[0];
//            int itdId = params[1];
//            try {
//                URL url = new URL("http://www.anasumrah.com/anasws/api/users/updateCheckOutStatus");
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("POST");
//                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//                conn.setDoOutput(true);
//
//                String postData = "roomNo=" + roomNo + "&itdId=" + itdId;
//                OutputStream os = conn.getOutputStream();
//                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
//                writer.write(postData);
//                writer.flush();
//                writer.close();
//                os.close();
//
//                int responseCode = conn.getResponseCode();
//                return responseCode == HttpURLConnection.HTTP_OK;
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                return false;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(Boolean result) {
//            if (result) {
//                Toast.makeText(context, "Check-out updated successfully!", Toast.LENGTH_SHORT).show();
//                roomList.remove(position);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, roomList.size());
//            } else {
//                Toast.makeText(context, "Failed to update check-out status.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    private class PostRequestTask2 extends AsyncTask<Integer, Void, Boolean> {
//
//        private int position; // Store the position of the item to be removed
//
//        public PostRequestTask2(int position) {
//            this.position = position; // Initialize with the position
//        }
//
//        @Override
//        protected Boolean doInBackground(Integer... params) {
//            int roomNo = params[0];
//            int ITD_HOTEL_ID = params[1];
//            try {
//                URL url = new URL("http://www.anasumrah.com/anasws/api/users/cleanupdate");
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("POST");
//                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//                conn.setDoOutput(true);
//
//                String postData = "roomNo=" + roomNo + "&ITD_HOTEL_ID=" + ITD_HOTEL_ID;
//                OutputStream os = conn.getOutputStream();
//                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
//                writer.write(postData);
//                writer.flush();
//                writer.close();
//                os.close();
//
//                int responseCode = conn.getResponseCode();
//                return responseCode == HttpURLConnection.HTTP_OK;
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                return false;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(Boolean result) {
//            if (result) {
//                Toast.makeText(context, "Clean updated successfully!", Toast.LENGTH_SHORT).show();
//                roomList.remove(position);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, roomList.size());
//            } else {
//                Toast.makeText(context, "Failed to update Clean status.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//}

package com.example.scanner;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private List<RoomDetails> roomList;
    private Context context;
    private boolean isNetworkConnected = true; // Default to true, will update with broadcast receiver

    public RoomAdapter(List<RoomDetails> roomList, Context context) {
        this.roomList = roomList;
        this.context = context;

        // Register the network change receiver
        ConnectivityReceiver receiver = new ConnectivityReceiver(new ConnectivityReceiver.ConnectivityReceiverListener() {
            @Override
            public void onNetworkConnectionChanged(boolean isConnected) {
                isNetworkConnected = isConnected;
            }
        });

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(receiver, filter);

        // Check initial connectivity status
        isNetworkConnected = ConnectivityReceiver.isConnected(context);
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cleaningrecyclerview, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        RoomDetails room = roomList.get(position);
        holder.roomNoTextView.setText(String.valueOf(room.getRoom_no()));

        if ("1".equals(String.valueOf(room.getAction()))) {
            holder.actionButton.setEnabled(false);
            holder.actionButton.setText(" ");
            holder.actionButton.setVisibility(View.GONE); // Hide the checkout button

            holder.cleanButton.setVisibility(View.VISIBLE); // Show the clean button
            if ("1".equals(String.valueOf(room.getClean_status()))) {
                holder.cleanButton.setEnabled(false);
                holder.cleanButton.setText(" ");
                holder.cleanButton.setBackgroundResource(R.drawable.btnbackground);
            } else {
                holder.cleanButton.setEnabled(true);
                holder.cleanButton.setText("Clean");
                holder.cleanButton.setBackgroundResource(R.drawable.button_background_completed);

                holder.cleanButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isNetworkConnected) {
                            int currentPosition = holder.getAdapterPosition(); // Get the current position
                            Log.d("RoomAdapter", "room no: " + room.getRoom_no() + ", hotel ID: " + room.getITD_HOTEL_ID());
                            new PostRequestTask2(currentPosition).execute(room.getRoom_no(), room.getITD_HOTEL_ID());
                        } else {
                            context.startActivity(new Intent(context, ErrorActivity.class));
                        }
                    }
                });
            }
        } else {
            holder.actionButton.setEnabled(true);
            holder.actionButton.setText("Checkout");
            holder.actionButton.setBackgroundResource(R.drawable.btnbackground);
            holder.actionButton.setVisibility(View.VISIBLE); // Show the checkout button
            holder.cleanButton.setVisibility(View.GONE); // Hide the clean button

            holder.actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNetworkConnected) {
                        int currentPosition = holder.getAdapterPosition();
                        new PostRequestTask(currentPosition).execute(room.getRoom_no(), room.getitd_ID());
                    } else {
                        context.startActivity(new Intent(context, ErrorActivity.class));
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView roomNoTextView;
        Button actionButton;
        Button cleanButton;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            roomNoTextView = itemView.findViewById(R.id.room_no_text);
            actionButton = itemView.findViewById(R.id.action_button);
            cleanButton = itemView.findViewById(R.id.clean_action_button);
        }
    }

    private class PostRequestTask extends AsyncTask<Integer, Void, Boolean> {

        private int position; // Store the position of the item to be removed

        public PostRequestTask(int position) {
            this.position = position; // Initialize with the position
        }
        @Override
        protected Boolean doInBackground(Integer... params) {
            int roomNo = params[0];
            int itdId = params[1];
            try {
                URL url = new URL("http://www.anasumrah.com/anasws/api/users/updateCheckOutStatus");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setDoOutput(true);

                String postData = "roomNo=" + roomNo + "&itdId=" + itdId;
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(postData);
                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();
                return responseCode == HttpURLConnection.HTTP_OK;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(context, "Check-out updated successfully!", Toast.LENGTH_SHORT).show();
                roomList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, roomList.size());
            } else {
                Toast.makeText(context, "Failed to update check-out status.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class PostRequestTask2 extends AsyncTask<Integer, Void, Boolean> {

        private int position; // Store the position of the item to be removed

        public PostRequestTask2(int position) {
            this.position = position; // Initialize with the position
        }
        @Override
        protected Boolean doInBackground(Integer... params) {
            int roomNo = params[0];
            int itdId = params[1];
            try {
                URL url = new URL("http://www.anasumrah.com/anasws/api/users/updateCleanStatus");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setDoOutput(true);

                String postData = "roomNo=" + roomNo + "&itdId=" + itdId;
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(postData);
                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();
                return responseCode == HttpURLConnection.HTTP_OK;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(context, "Clean status updated successfully!", Toast.LENGTH_SHORT).show();
                roomList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, roomList.size());
            } else {
                Toast.makeText(context, "Failed to update clean status.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
