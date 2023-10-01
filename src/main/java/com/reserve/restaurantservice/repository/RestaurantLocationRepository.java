package com.reserve.restaurantservice.repository;

import com.reserve.restaurantservice.entities.RestaurantLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantLocationRepository extends JpaRepository<RestaurantLocation, Integer> {

    RestaurantLocation findByPincode(Integer pincode);

    List<RestaurantLocation> findAllByCity(String city);

    Page<RestaurantLocation> findAll(Pageable pageable);

    @Query(value = "select city from restaurant_location", nativeQuery = true)
    List<String> findAllCities();

    @Query(value = "select area from restaurant_location where restaurant_location.city=?1", nativeQuery = true)
    List<String> findAllAreasByCity(String city);

    @Query(value = "select pincode from restaurant_location where restaurant_location.city=?1 and restaurant_location.area=?2", nativeQuery = true)
    List<Integer> findAllPincodesByCityArea(String city, String area);
}
