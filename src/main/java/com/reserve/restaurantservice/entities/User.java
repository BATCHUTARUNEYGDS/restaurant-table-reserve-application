package com.reserve.restaurantservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@DynamicInsert
@DynamicUpdate
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(unique = true)
    @NotBlank(message = "Your username can't be empty")
    private String userName;

    @NotBlank(message = "Your password can't be empty")
    private String userPassword;

    @Column(unique = true)
    @Email
    private String userEmailId;

    @Column(unique = true)
    //@Pattern(regexp = "^(0/91)?[6-9][0-9]{10}$")
    @NotBlank(message = "Your phone number can't be empty")
    private String userPhoneNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userLocationId_fk")
    private UserLocation userLocation;

    @NotBlank(message = "Your full name can't be empty")
    private String userFullName;


    private Integer resetToken = null;

    private Long requestedTime = null;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<RestaurantRating> restaurantRatings;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Reservation> reservation;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserRating> userRatings;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();


    private Double avgRating = 0.0;

    private Integer reviewCount = 0;

    public User() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
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

    public UserLocation getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(UserLocation userLocation) {
        this.userLocation = userLocation;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public List<RestaurantRating> getRestaurantRatings() {
        return restaurantRatings;
    }

    public void setRestaurantRatings(List<RestaurantRating> restaurantRatings) {
        this.restaurantRatings = restaurantRatings;
    }

    public List<Reservation> getReservation() {
        return reservation;
    }

    public void setReservation(List<Reservation> reservation) {
        this.reservation = reservation;
    }

    public List<UserRating> getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(List<UserRating> userRatings) {
        this.userRatings = userRatings;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Integer getResetToken() {
        return resetToken;
    }

    public void setResetToken(Integer resetToken) {
        this.resetToken = resetToken;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Long getRequestedTime() {
        return requestedTime;
    }

    public void setRequestedTime(Long requestedTime) {
        this.requestedTime = requestedTime;
    }

}
