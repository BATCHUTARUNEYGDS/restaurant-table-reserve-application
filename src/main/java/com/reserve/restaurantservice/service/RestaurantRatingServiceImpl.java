package com.reserve.restaurantservice.service;

import com.reserve.restaurantservice.dto.RatingCountDto;
import com.reserve.restaurantservice.dto.RestaurantRatingDto;
import com.reserve.restaurantservice.entities.RatingCount;
import com.reserve.restaurantservice.entities.Restaurant;
import com.reserve.restaurantservice.entities.RestaurantRating;
import com.reserve.restaurantservice.entities.User;
import com.reserve.restaurantservice.exception.NotFoundException;
import com.reserve.restaurantservice.mapper.RatingCountMapper;
import com.reserve.restaurantservice.mapper.RestaurantRatingMapper;
import com.reserve.restaurantservice.repository.RatingCountRepository;
import com.reserve.restaurantservice.repository.RestaurantRatingRepository;
import com.reserve.restaurantservice.repository.RestaurantRepository;
import com.reserve.restaurantservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantRatingServiceImpl implements RestaurantRatingService {

    @Autowired
    RestaurantRatingRepository restaurantRatingRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    RatingCountRepository ratingCountRepository;

    @Autowired
    RatingCountMapper ratingCountMapper;

    @Autowired
    RestaurantRatingMapper restaurantRatingMapper;


    @Override
    public RestaurantRating createRestaurantRating(RestaurantRating restaurantRating) {
        Restaurant restaurant6 = restaurantRepository.findById(restaurantRating.getRestaurant().getRestaurantId()).orElse(null);
        RatingCount ratingCount = ratingCountRepository.findByRestaurant(restaurant6);
        //Optional<RatingCount> ratingCount1 = ratingCountRepository.findById(ratingCount.getRatingCountId());
        if (restaurant6 != null) {
            restaurant6.setReviewCount(restaurant6.getReviewCount() + 1);
            if (restaurant6.getRatingAverage() == 0) {
                restaurant6.setRatingAverage((restaurant6.getRatingAverage() + (restaurantRating.getTasteRating() + restaurantRating.getHospitalityRating() + restaurantRating.getAmbienceRating()) / 3));
            } else {
                restaurant6.setRatingAverage((restaurant6.getRatingAverage() + (restaurantRating.getTasteRating() + restaurantRating.getHospitalityRating() + restaurantRating.getAmbienceRating()) / 3) / 2);
            }
            Integer average = (restaurantRating.getAmbienceRating() + restaurantRating.getHospitalityRating() + restaurantRating.getTasteRating()) / 3;
            if (average == 0) {
                ratingCount.setZeroCount(ratingCount.getZeroCount() + 1);
            }
            if (average == 1) {
                ratingCount.setOneCount(ratingCount.getOneCount() + 1);
            }
            if (average == 2) {
                ratingCount.setTwoCount(ratingCount.getTwoCount() + 1);
            }
            if (average == 3) {
                ratingCount.setThreeCount(ratingCount.getThreeCount() + 1);
            }
            if (average == 4) {
                ratingCount.setFourCount(ratingCount.getFourCount() + 1);
            }
            if (average == 5) {
                ratingCount.setFiveCount(ratingCount.getFiveCount() + 1);
            }
            restaurantRating.setTotalRating(average);
            return restaurantRatingRepository.save(restaurantRating);
        } else {
            throw new NotFoundException("No restaurant present with given restaurant id :" + restaurant6.getRestaurantId());
        }

    }

    @Override
    public RestaurantRating getRestaurantRatingByRestaurantRatingId(Integer restaurantRatingId) {
        RestaurantRating restaurantRating1 = restaurantRatingRepository.findById(restaurantRatingId).orElse(null);
        if (restaurantRating1 == null) {
            throw new NotFoundException("No rating was given with the id: " + restaurantRatingId);
        } else {
            return restaurantRating1;
        }
    }

    @Override
    public List<RestaurantRating> getAllRestaurantRating() {
        List<RestaurantRating> restaurantRatings = restaurantRatingRepository.findAll();
        if (restaurantRatings.isEmpty()) {
            throw new NotFoundException("No ratings were posted untill now");
        } else {
            return restaurantRatings;
        }
    }

    @Override
    public List<RestaurantRatingDto> getrestaurantRatingByRestaurantId(Integer restaurantId, Pageable pageable) {
        Restaurant restaurant1 = restaurantRepository.findById(restaurantId).orElse(null);
        String restaurantName = restaurant1.getRestaurantName();
        if (restaurant1 == null) {
            throw new NotFoundException("No restaurant with Id: " + restaurantId + "");
        } else {
            List<RestaurantRating> restaurantRatings = restaurantRatingRepository.findByRestaurant(restaurant1, pageable);
            if (restaurantRatings.isEmpty()) {
//                throw new NotFoundException("Sorry no ratings given to this restaurant " + restaurant1);
                return restaurantRatingMapper.entityToDtos(restaurantRatings);
            } else {
                List<RestaurantRatingDto> restaurantRatingDtos = restaurantRatingMapper.entityToDtos(restaurantRatings);
                for (int i = 0; i < restaurantRatingDtos.size(); i++) {
                    RestaurantRating Rr = restaurantRatings.get(i);
                    RestaurantRatingDto RrDto = restaurantRatingDtos.get(i);
                    RrDto.setRestaurantName(restaurantName);
                    RrDto.setUserName(Rr.getUser().getUserName());
                }
                return restaurantRatingDtos;
            }
        }
    }

    @Override
    public List<RestaurantRatingDto> getrestaurantRatingByUserId(Integer userId) {
        User user1 = userRepository.findById(userId).orElse(null);
        String userName = user1.getUserName();
        if (user1 == null) {
            throw new NotFoundException("No user exist with given userId:" + userId);
        } else {
            List<RestaurantRating> restaurantRatings = restaurantRatingRepository.findByUser(user1);
            if (restaurantRatings.isEmpty()) {
                throw new NotFoundException("Sorry no ratings given by this user " + user1);
            } else {
                List<RestaurantRatingDto> restaurantRatingDtos = restaurantRatingMapper.entityToDtos(restaurantRatings);
                for (int i = 0; i < restaurantRatingDtos.size(); i++) {
                    RestaurantRating Rr = restaurantRatings.get(i);
                    RestaurantRatingDto RrDto = restaurantRatingDtos.get(i);
                    RrDto.setRestaurantName(Rr.getRestaurant().getRestaurantName());
                    RrDto.setUserName(userName);
                }
                return restaurantRatingDtos;
            }
        }
    }

    @Override
    public RatingCountDto getRatingCountByRestaurantId(Integer restaurantId) {
        Restaurant restaurantForRatingCount = restaurantRepository.findById(restaurantId).orElse(null);
        Integer count = restaurantForRatingCount.getReviewCount();
        RatingCount rc = ratingCountRepository.findByRestaurant(restaurantForRatingCount);
        RatingCountDto rcD = ratingCountMapper.entityToDto(rc);
        rcD.setReviewCount(count);
        return rcD;
    }

    @Override
    public ResponseEntity<String> deleteAllRatingsByRestaurantId(Integer restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        List<RestaurantRating> restaurantRatings = restaurantRatingRepository.findByRestaurant(restaurant, Pageable.unpaged());
        for (int i = 0; i < restaurantRatings.size(); i++) {
            restaurantRatingRepository.deleteById(restaurantRatings.get(i).getRestaurantRatingId());
        }
        return ResponseEntity.ok("All restaurant ratings were deleted");
    }

    @Override
    public ResponseEntity<String> deleteRatingCountByRestaurantId(Integer restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        RatingCount ratingCount = ratingCountRepository.findByRestaurant(restaurant);
        ratingCountRepository.deleteById(ratingCount.getRatingCountId());
        return ResponseEntity.ok("deleted the rating count");
    }
}
