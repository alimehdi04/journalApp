package com.autisheimer.journalApp.model;

public class UserResponse {

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String message = "";

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    int statusCode;

    public UserResponse(int sc, String msg){
        statusCode = sc;
        message = msg;
    }

}

