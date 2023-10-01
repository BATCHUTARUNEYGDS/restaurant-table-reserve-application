package com.reserve.restaurantservice.repository;

import com.reserve.restaurantservice.entities.User;
import com.reserve.restaurantservice.entities.UserLocation;
import com.reserve.restaurantservice.entities.UserRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRatingRepository extends JpaRepository<UserRating,Integer> {
    Page<UserRating> findAll(Pageable pageable);


    List<UserRating> findByUser(User user);
}
