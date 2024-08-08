package com.example.scanner;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
public class RoomStatusAdapter extends RecyclerView.Adapter<RoomStatusAdapter.RoomStatusViewHolder> {

    private List<JSONObject> roomDetailsList;
    private Context context;

    public RoomStatusAdapter(List<JSONObject> roomDetailsList, Context context) {
        this.roomDetailsList = roomDetailsList;
        this.context = context;
    }

    @NonNull
    @Override
    public RoomStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_room_status, parent, false);
        return new RoomStatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomStatusViewHolder holder, int position) {
        JSONObject roomDetails = roomDetailsList.get(position);

        try {
            holder.tripCodeTextView.setText(String.valueOf(roomDetails.getInt("trip_code")));
            holder.agentCodeTextView.setText(roomDetails.getString("agent_code"));
            holder.inDateTextView.setText(roomDetails.getString("in_date"));
            holder.nightsTextView.setText(String.valueOf(roomDetails.getInt("nights")));
            holder.outDateTextView.setText(roomDetails.getString("out_date"));
            holder.roomTypeTextView.setText(roomDetails.getString("room_type"));
            holder.roomNoTextView.setText(String.valueOf(roomDetails.getInt("room_no")));
            holder.statusTextView.setText(roomDetails.getString("status"));
            holder.guestNameTextView.setText(roomDetails.getString("guest_name"));
            holder.contactTextView.setText(roomDetails.getString("contact_no"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return roomDetailsList.size();
    }

    public static class RoomStatusViewHolder extends RecyclerView.ViewHolder {
        TextView tripCodeTextView;
        TextView agentCodeTextView;
        TextView inDateTextView;
        TextView nightsTextView;
        TextView outDateTextView;
        TextView roomTypeTextView;
        TextView roomNoTextView;
        TextView statusTextView;
        TextView guestNameTextView;
        TextView contactTextView;

        public RoomStatusViewHolder(@NonNull View itemView) {
            super(itemView);
            tripCodeTextView = itemView.findViewById(R.id.trip_code);
            agentCodeTextView = itemView.findViewById(R.id.agent_code);
            inDateTextView = itemView.findViewById(R.id.in_date);
            nightsTextView = itemView.findViewById(R.id.nights);
            outDateTextView = itemView.findViewById(R.id.out_date);
            roomTypeTextView = itemView.findViewById(R.id.room_type);
            roomNoTextView = itemView.findViewById(R.id.room_no);
            statusTextView = itemView.findViewById(R.id.status);
            guestNameTextView = itemView.findViewById(R.id.guest_name);
            contactTextView = itemView.findViewById(R.id.contact);
        }
    }
}
