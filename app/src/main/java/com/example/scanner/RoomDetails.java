package com.example.scanner;

public class RoomDetails {
    private int room_no;
    private int action; // 1 for checkout
    private int clean_status; // 1 for clean, 0 for dirty
    private int itd_ID;
    private int itd_HOTEL_ID;

    public int getITD_HOTEL_ID() {
        return itd_HOTEL_ID;
    }

    public void setITD_HOTEL_ID(int itd_HOTEL_ID) {
        this.itd_HOTEL_ID = itd_HOTEL_ID;
    }

    public int getRoom_no() {
        return room_no;
    }

    public void setRoom_no(int room_no) {
        this.room_no = room_no;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getClean_status() {
        return clean_status;
    }

    public void setClean_status(int clean_status) {
        this.clean_status = clean_status;
    }

    public int getitd_ID() {
        return itd_ID;
    }

    public void setitd_ID(int itd_ID) {
        this.itd_ID = itd_ID;
    }
}

