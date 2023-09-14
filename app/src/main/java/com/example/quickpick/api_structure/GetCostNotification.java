package com.example.quickpick.api_structure;

import java.util.List;

public class GetCostNotification {
    private String notificationType ;

    private List<GetCostResponse> listOfCostResponse;

    private String idUser;

    public GetCostNotification(String notificationType, List<GetCostResponse> listOfCostResponse, String idUser) {
        this.notificationType = notificationType;
        this.listOfCostResponse = listOfCostResponse;
        this.idUser = idUser;
    }
    public GetCostNotification(){}

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public List<GetCostResponse> getListOfCostResponse() {
        return listOfCostResponse;
    }

    public void setListOfCostResponse(List<GetCostResponse> listOfCostResponse) {
        this.listOfCostResponse = listOfCostResponse;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
