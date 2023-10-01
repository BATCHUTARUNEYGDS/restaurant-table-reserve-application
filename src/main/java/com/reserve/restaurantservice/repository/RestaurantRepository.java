package com.reserve.restaurantservice.repository;

import com.reserve.restaurantservice.entities.Restaurant;
import com.reserve.restaurantservice.entities.RestaurantLocation;
import com.reserve.restaurantservice.entities.RestaurantManager;
import com.reserve.restaurantservice.entities.RestaurantType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    Page<Restaurant> findAll(Pageable pageable);


    List<Restaurant> findByRestaurantLocationCityOrderByRatingAverageDesc(String city, Pageable pageable);

    Optional<Restaurant> findByRestaurantMail(String restaurantMail);

    Optional<Restaurant> findByRestaurantManager(RestaurantManager restaurantManager);

    List<Restaurant> findByRestaurantCusineType(RestaurantType restaurantType);

    List<Restaurant> findByRestaurantLocationCityAndRestaurantCusineTypeCuisineOrderByRatingAverageDesc(String city, String cuisine);

    List<Restaurant> findByRestaurantLocation(RestaurantLocation restaurantLocation, Pageable pageable);

    Optional<Restaurant> findByrestaurantName(String restaurantName);

    List<Restaurant> findByTag(String restaurantTag);

    @Query(value = "SELECT restaurant_name FROM restaurant WHERE restaurant_location_fk IN " +
            "(SELECT location_id FROM restaurant_location WHERE city =:c)",nativeQuery = true)
    List<String> findRestaurantNameByRestaurantLocationCity(@Param("c") String city);

    @Query(value = "SELECT * FROM restaurant WHERE restaurant_location_fk IN (SELECT location_id FROM restaurant_location WHERE city =?1) and (tag = ?2 OR ?2 is null) and restaurant_id IN (SELECT restaurant_id from restaurant_cuisine_type WHERE restaurant_type_id IN (SELECT restaurant_type_id FROM restaurant_type where cuisine IN ?3)) ORDER BY restaurant_name",nativeQuery = true)
    Page<Restaurant> findByFilters(String city, String tag, List<String> Cuisine, Pageable pageable);

    @Query(value = "SELECT * FROM restaurant WHERE restaurant_location_fk IN (SELECT location_id FROM restaurant_location WHERE city =?1) and (tag = ?2 OR ?2 is null) and restaurant_id IN (SELECT restaurant_id from restaurant_cuisine_type WHERE restaurant_type_id IN (SELECT restaurant_type_id FROM restaurant_type where cuisine IN ?3))ORDER BY approx_for_two ASC",nativeQuery = true)
    Page<Restaurant> findByFiltersPriceAsc(String city, String tag, List<String> Cuisine, Pageable pageable);

    @Query(value = "SELECT * FROM restaurant WHERE restaurant_location_fk IN (SELECT location_id FROM restaurant_location WHERE city =?1) and (tag = ?2 OR ?2 is null) and restaurant_id IN (SELECT restaurant_id from restaurant_cuisine_type WHERE restaurant_type_id IN (SELECT restaurant_type_id FROM restaurant_type where cuisine IN ?3))ORDER BY approx_for_two DESC",nativeQuery = true)
    Page<Restaurant> findByFiltersPriceDesc(String city, String tag, List<String> Cuisine, Pageable pageable);

    @Query(value = "SELECT * FROM restaurant WHERE restaurant_location_fk IN (SELECT location_id FROM restaurant_location WHERE city =?1) and (tag = ?2 OR ?2 is null) and restaurant_id IN (SELECT restaurant_id from restaurant_cuisine_type WHERE restaurant_type_id IN (SELECT restaurant_type_id FROM restaurant_type where cuisine IN ?3))ORDER BY rating_average DESC",nativeQuery = true)
    Page<Restaurant> findByFiltersRating(String city, String tag, List<String> Cuisine, Pageable pageable);

    @Query(value = "SELECT * FROM restaurant WHERE restaurant_location_fk IN (SELECT location_id FROM restaurant_location WHERE city =?1) and (tag = ?2 OR ?2 is null) and restaurant_id IN (SELECT restaurant_id from restaurant_cuisine_type WHERE restaurant_type_id IN (SELECT restaurant_type_id FROM restaurant_type where cuisine IN ?3)) ORDER BY rating_average DESC",nativeQuery = true)
    Page<Restaurant> Trail(String city, String tag, List<String> Cuisine, Pageable pageable);





}
