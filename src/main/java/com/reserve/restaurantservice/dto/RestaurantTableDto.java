package com.reserve.restaurantservice.dto;

import javax.persistence.Id;

public class RestaurantTableDto {

    @Id
    private Integer tableId;
    private String tableName;
    private Integer seatsCount;
    private String customisationOptions;

    public RestaurantTableDto() {
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
}
