package com.reserve.restaurantservice.entities;

public class ResetPassword {

    private String password;

    private Integer resetToken;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getResetToken() {
        return resetToken;
    }

    public void setResetToken(Integer resetToken) {
        this.resetToken = resetToken;
    }
}
