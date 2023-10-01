package com.reserve.restaurantservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
//@Data
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tableId;

    private String tableName;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_fk", referencedColumnName = "restaurantId")
    private Restaurant theRestaurant;


    private Integer seatsCount;

    private String customisationOptions;

    @JsonIgnore
    @ManyToMany(mappedBy = "reservedRestaurantTables")
    private List<Reservation> reservation;

    public RestaurantTable() {
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Restaurant getRestaurant() {
        return theRestaurant;
    }

    public void assignRestaurant(Restaurant restaurant) {
        this.theRestaurant = restaurant;
    }

    public Integer getSeatsCount() {
        return seatsCount;
    }

    public void setSeatsCount(Integer seatsCount) {
        this.seatsCount = seatsCount;
    }

    public String getCustomisationOptions() {
        return customisationOptions;
    }

    public void setCustomisationOptions(String customisationOptions) {
        this.customisationOptions = customisationOptions;
    }

    public List<Reservation> getReservation() {
        return reservation;
    }

    public void setReservation(List<Reservation> reservation) {
        this.reservation = reservation;
    }

    public Restaurant getTheRestaurant() {
        return theRestaurant;
    }

    public void setTheRestaurant(Restaurant theRestaurant) {
        this.theRestaurant = theRestaurant;
    }

    @Override
    public String toString() {
        return "RestaurantTable{" +
                "tableId=" + tableId +
                ", tableName='" + tableName + '\'' +
                ", theRestaurant=" + theRestaurant +
                ", seatsCount=" + seatsCount +
                ", customisationOptions='" + customisationOptions + '\'' +
                ", reservation=" + reservation +
                '}';
    }

}
