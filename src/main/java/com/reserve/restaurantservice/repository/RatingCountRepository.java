package com.reserve.restaurantservice.repository;

import com.reserve.restaurantservice.entities.RatingCount;
import com.reserve.restaurantservice.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatingCountRepository extends JpaRepository<RatingCount, Integer> {

    RatingCount findByRestaurant(Restaurant restaurant);
}
