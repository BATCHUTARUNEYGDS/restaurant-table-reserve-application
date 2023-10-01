package com.reserve.restaurantservice.dto;

public class AdminResponseDTO {


    private Integer adminId;
    private String adminUserName;
    private String adminEmailId;
    private String accessToken;

    public AdminResponseDTO(Integer adminId, String adminUserName, String adminEmailId, String accessToken) {
        this.adminId = adminId;
        this.adminUserName = adminUserName;
        this.adminEmailId = adminEmailId;
        this.accessToken = accessToken;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminUserName() {
        return adminUserName;
    }

    public void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    public String getAdminEmailId() {
        return adminEmailId;
    }

    public void setAdminEmailId(String adminEmailId) {
        this.adminEmailId = adminEmailId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
