package com.reserve.restaurantservice.repository;

import com.reserve.restaurantservice.entities.RestaurantType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RestaurantTypeRepository extends JpaRepository<RestaurantType, Integer> {

    RestaurantType findByCuisine(String Cuisine);

    @Query(value = "select cuisine from RestaurantType")
    List<String> findAllCuisine();

    @Query(value = "DELETE FROM restaurant_cuisine_type WHERE restaurant_id = ?1 AND restaurant_type_id = ?2",nativeQuery = true)
    void unAssignCuisineFromRestaurant(Integer restaurantId,Integer restaurantTypeId);
}