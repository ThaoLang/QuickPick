package com.example.quickpick.api_structure;

import com.example.quickpick.constants.NotificationTypes;

public class GetDirectionNotification {
    private String userId;
    private String notificationType = NotificationTypes.GET_DIRECTION_NOTIFICATION;
    private Geometry geometry;

    public GetDirectionNotification(String userId, String notificationType, Geometry geometry) {
        this.userId = userId;
        this.notificationType = notificationType;
        this.geometry = geometry;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
}
