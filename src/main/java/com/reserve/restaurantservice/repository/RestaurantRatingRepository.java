package com.reserve.restaurantservice.repository;

import com.reserve.restaurantservice.entities.Restaurant;
import com.reserve.restaurantservice.entities.RestaurantRating;
import com.reserve.restaurantservice.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRatingRepository extends JpaRepository<RestaurantRating, Integer> {

    List<RestaurantRating> findByRestaurant(Restaurant restaurant, Pageable pageable);

    List<RestaurantRating> findByUser(User user);


}
