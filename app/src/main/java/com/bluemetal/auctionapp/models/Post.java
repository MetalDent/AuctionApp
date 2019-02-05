package com.bluemetal.auctionapp.models;

public class Post {
    private String itemName;
    private String description;
    private float baseBid;
    private String startTime;
    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getBaseBid() {
        return baseBid;
    }

    public void setBaseBid(float baseBid) {
        this.baseBid = baseBid;
    }
}
