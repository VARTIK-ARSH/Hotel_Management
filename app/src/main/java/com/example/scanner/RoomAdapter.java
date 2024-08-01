package com.example.scanner;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private List<RoomDetails> roomList;
    private Context context;

    public RoomAdapter(List<RoomDetails> roomList, Context context) {
        this.roomList = roomList;
        this.context = context;
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


//        Log.d("RoomAdapter", "Room No: " + room.getRoom_no()  + ", Action: " + room.getAction() + ", Clean Status: " + room.getClean_status());

        // Check action status
        if ("1".equals(String.valueOf(room.getAction()))) { // Assuming getAction() returns String
            holder.actionButton.setEnabled(false);
            holder.actionButton.setText(" ");
            holder.actionButton.setBackgroundResource(R.drawable.button_background_completed);
        } else {
            holder.actionButton.setEnabled(true);
            holder.actionButton.setText("Checkout");
            holder.actionButton.setBackgroundResource(R.drawable.btnbackground); // Set default background

            holder.actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d("RoomAdapter", "hnc_room_no  :- " + room.getRoom_no()  + ",  hnc_itd_id  :- " + room.getitd_ID() + ", hnc_check_out  :- " + 1);

                }
            });
        }

        // Check clean status
        if ("1".equals(String.valueOf(room.getClean_status()))) { // Assuming getClean_status() returns String
            holder.cleanButton.setEnabled(false);
            holder.cleanButton.setText(" ");
            holder.cleanButton.setBackgroundResource(R.drawable.button_background_completed);
        } else {
            holder.cleanButton.setEnabled(true);
            holder.cleanButton.setText("Clean");
            holder.cleanButton.setBackgroundResource(R.drawable.btnbackground); // Set default background
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
}
