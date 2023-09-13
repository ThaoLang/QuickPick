package com.example.quickpick.components;

public class Notification {

    private String title;
    private String content;
    private String orderId;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private boolean status; //read or unread

    public boolean isReaded() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Notification(String title, String content, String orderId, boolean status, String date) {
        this.title = title;
        this.content = content;
        this.orderId = orderId;
        this.status= status;
        this.date=date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
