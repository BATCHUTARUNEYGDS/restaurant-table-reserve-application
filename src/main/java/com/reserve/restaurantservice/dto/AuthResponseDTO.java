package com.reserve.restaurantservice.dto;


public class AuthResponseDTO {


    private Integer mId;
    private String mname;
    private Integer rId;
    private String accessToken;

    public AuthResponseDTO(Integer mId, String mname, Integer rId, String accessToken) {
        this.mId = mId;
        this.mname = mname;
        this.rId = rId;
        this.accessToken = accessToken;
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public Integer getrId() {
        return rId;
    }

    public void setrId(Integer rId) {
        this.rId = rId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}