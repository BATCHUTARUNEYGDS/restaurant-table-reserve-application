package com.reserve.restaurantservice.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class RestaurantManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer restaurantManagerId;


    @Column(unique = true)
    private String restaurantManagerUserName;

    private String restaurantManagerPassword;

    private String restaurantManagerFullName;

    @Column(unique = true)
    private String restaurantManagerEmailId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "restaurantManager_roles", joinColumns = @JoinColumn(name = "restaurantManager_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    public RestaurantManager() {
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Integer getRestaurantManagerId() {
        return restaurantManagerId;
    }

    public void setRestaurantManagerId(Integer restaurantManagerId) {
        this.restaurantManagerId = restaurantManagerId;
    }

    public String getRestaurantManagerUserName() {
        return restaurantManagerUserName;
    }

    public void setRestaurantManagerUserName(String restaurantManagerUserName) {
        this.restaurantManagerUserName = restaurantManagerUserName;
    }

    public String getRestaurantManagerPassword() {
        return restaurantManagerPassword;
    }

    public void setRestaurantManagerPassword(String restaurantManagerPassword) {
        this.restaurantManagerPassword = restaurantManagerPassword;
    }

    public String getRestaurantManagerFullName() {
        return restaurantManagerFullName;
    }

    public void setRestaurantManagerFullName(String restaurantManagerFullName) {
        this.restaurantManagerFullName = restaurantManagerFullName;
    }

    public String getRestaurantManagerEmailId() {
        return restaurantManagerEmailId;
    }

    public void setRestaurantManagerEmailId(String restaurantManagerEmailId) {
        this.restaurantManagerEmailId = restaurantManagerEmailId;
    }
}
