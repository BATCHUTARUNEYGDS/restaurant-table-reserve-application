package com.reserve.restaurantservice.repository;

import com.reserve.restaurantservice.entities.Restaurant;
import com.reserve.restaurantservice.entities.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Integer> {

    List<RestaurantTable> findByTheRestaurant(Restaurant restaurant);

    RestaurantTable findByTableName(String tableName);

    @Query(value="SELECT * FROM restaurant_table WHERE restaurant_fk = ?1 AND table_name = ?2",nativeQuery = true)
    RestaurantTable findByRestaurantIdAndTableName(Integer restaurantId,String tableName);

}
