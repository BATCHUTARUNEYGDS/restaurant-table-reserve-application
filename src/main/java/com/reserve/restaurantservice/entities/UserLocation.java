package com.reserve.restaurantservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class UserLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userLocationId;
    private String userCity;


    @NotNull
    @NotBlank(message = "please select your user located area")
    private String userArea;
    private Integer userPinCode;

    @OneToMany(mappedBy = "userLocation")
    @JsonIgnore
    private List<User> users;

    public UserLocation(Integer userLocationId, String userCity, Integer userPinCode, List<User> users) {
        this.userLocationId = userLocationId;
        this.userCity = userCity;
        this.userPinCode = userPinCode;
        this.users = users;
    }

    public UserLocation() {
    }

    public Integer getUserLocationId() {
        return userLocationId;
    }

    public void setUserLocationId(Integer userLocationId) {
        this.userLocationId = userLocationId;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public Integer getUserPinCode() {
        return userPinCode;
    }

    public void setUserPinCode(Integer userPinCode) {
        this.userPinCode = userPinCode;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "UserLocation{" +
                "userLocationId=" + userLocationId +
                ", userCity='" + userCity + '\'' +
                ", userPinCode=" + userPinCode +
                ", users=" + users +
                '}';
    }
}
