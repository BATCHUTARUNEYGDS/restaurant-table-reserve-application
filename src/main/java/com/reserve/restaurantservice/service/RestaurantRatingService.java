package com.reserve.restaurantservice.service;

import com.reserve.restaurantservice.dto.RatingCountDto;
import com.reserve.restaurantservice.dto.RestaurantRatingDto;
import com.reserve.restaurantservice.entities.RestaurantRating;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestaurantRatingService {

    RestaurantRating createRestaurantRating(RestaurantRating restaurantRating);

    RestaurantRating getRestaurantRatingByRestaurantRatingId(Integer restaurantRatingId);

    List<RestaurantRating> getAllRestaurantRating();

    List<RestaurantRatingDto> getrestaurantRatingByRestaurantId(Integer restaurantId, Pageable pageable);

    List<RestaurantRatingDto> getrestaurantRatingByUserId(Integer userId);

    RatingCountDto getRatingCountByRestaurantId(Integer restaurantId);

    ResponseEntity<String> deleteAllRatingsByRestaurantId(Integer restaurantId);

    ResponseEntity<String> deleteRatingCountByRestaurantId(Integer restaurantId);
}
