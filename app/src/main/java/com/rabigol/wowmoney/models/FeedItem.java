package com.rabigol.wowmoney.models;

/**
 * Created by Artur.Ziaev on 23.10.2016.
 */

public class FeedItem {
    private int id;
    private int ownerId;
    private String ownerName;
    private String message;
    private String photo; // TODO: think about url it
    private int timestamp;

    public FeedItem(int id, int ownerId, String ownerName, int timestamp, String message, String photo) {
        this.id = id;
        this.message = message;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.photo = photo;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getPhoto() {
        return photo;
    }
}
