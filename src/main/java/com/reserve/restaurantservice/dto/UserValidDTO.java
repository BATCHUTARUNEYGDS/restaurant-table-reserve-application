package com.reserve.restaurantservice.dto;

public class UserValidDTO {
    private String accessRole;
    private boolean status;
    private int userId;
    private String userName;
    private String userEmailId;
    private String userPhoneNumber;
    private String userFullName;

    public UserValidDTO(String accessRole, boolean status, int userId, String userName, String userEmailId, String userPhoneNumber, String userFullName) {
        this.accessRole = accessRole;
        this.status = status;
        this.userId = userId;
        this.userName = userName;
        this.userEmailId = userEmailId;
        this.userPhoneNumber = userPhoneNumber;
        this.userFullName = userFullName;
    }

    public String getAccessRole() {
        return accessRole;
    }

    public void setAccessRole(String accessRole) {
        this.accessRole = accessRole;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }
}