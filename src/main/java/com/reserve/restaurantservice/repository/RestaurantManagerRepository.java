package com.reserve.restaurantservice.repository;

import com.reserve.restaurantservice.entities.RestaurantManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantManagerRepository extends JpaRepository<RestaurantManager, Integer> {
    Optional<RestaurantManager> findByRestaurantManagerUserName(String restaurantManagerUserName);

    Optional<RestaurantManager> findByRestaurantManagerEmailId(String restaurantManagerEmailId);

    Page<RestaurantManager> findAll(Pageable pageable);
}
