package com.bluemetal.auctionapp.models;

public class Post {
    private String itemName;
    private String description;
    private String baseBid;
    private String startTime;
    private String endTime;
    private String imageUrl;

    public Post() {}

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

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

    public String getBaseBid() {
        return baseBid;
    }

    public void setBaseBid(String baseBid) {
        this.baseBid = baseBid;
    }
}
