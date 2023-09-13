package com.example.quickpick.api_structure;

public class GetCostRequest {
    public static String ID_TOKEN="idToken";
    public static String USER_ID="userId";
    public static String SYSTEM_KEY="systemKey";
    public static String START_ADDRESS="startAddress";
    public static String END_ADDRESS="endAddress";

    private String idToken;
    private String userId;
    private String systemKey;
    private String startAddress;
    private String endAddress;

    public GetCostRequest(String idToken, String userId, String systemKey, String startAddress, String endAddress) {
        this.idToken = idToken;
        this.userId = userId;
        this.systemKey = systemKey;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSystemKey() {
        return systemKey;
    }

    public void setSystemKey(String systemKey) {
        this.systemKey = systemKey;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }
}
