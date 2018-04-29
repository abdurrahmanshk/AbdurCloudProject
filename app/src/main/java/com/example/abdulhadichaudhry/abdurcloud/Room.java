package com.example.abdulhadichaudhry.abdurcloud;

import java.util.ArrayList;
import java.util.HashMap;

public class Room {
    String bookedby;
    String features;
    String floor;
    String cost;
    String roomno;
    String roomstatus;
    String roomtype;
    String available;
    HashMap<String,String> photos;

    public Room() {
    }


    public HashMap<String,String> getPhotos() {
        return photos;
    }

    public void setPhotos(HashMap<String,String> photos) {
        this.photos = photos;
    }

    public Room(String bookedby, String features, String floor, String cost, String roomno, String roomstatus, String roomtype, String available ,ArrayList<String> photos) {
        this.bookedby = bookedby;
        this.features = features;
        this.floor = floor;
        this.cost = cost;
        this.roomno = roomno;
        this.roomstatus = roomstatus;
        this.roomtype = roomtype;

        this.photos=new HashMap<>();
    }

    public String getBookedby() {
        return bookedby;
    }

    public void setBookedby(String bookedby) {
        this.bookedby = bookedby;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getRoomno() {
        return roomno;
    }

    public void setRoomno(String roomno) {
        this.roomno = roomno;
    }

    public String getRoomstatus() {
        return roomstatus;
    }

    public void setRoomstatus(String roomstatus) {
        this.roomstatus = roomstatus;
    }

    public String getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }
}
