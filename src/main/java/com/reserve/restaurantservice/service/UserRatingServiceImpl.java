package com.reserve.restaurantservice.service;

import com.reserve.restaurantservice.entities.User;
import com.reserve.restaurantservice.entities.UserRating;
import com.reserve.restaurantservice.exception.NotFoundException;
import com.reserve.restaurantservice.repository.UserRatingRepository;
import com.reserve.restaurantservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRatingServiceImpl implements UserRatingService {

    @Autowired
    UserRatingRepository userRatingRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void createUserRating(UserRating userRating) {
        userRating.setOverAllRating((userRating.getPunctuality() + userRating.getBehaviour()) / 2);
        userRatingRepository.save(userRating);
        User user1 = userRepository.findById(userRating.getUser().getUserId()).orElse(null);
        List<UserRating> ratingList = userRatingRepository.findByUser(user1);
        double sumRating = 0.0;

        for (UserRating ur : ratingList) {
            sumRating = sumRating + ur.getOverAllRating();
        }

        double avgRating = sumRating / ratingList.size();

        User user = userRepository.findById(user1.getUserId()).get();
        user.setReviewCount(user.getReviewCount() + 1);
        user.setAvgRating(avgRating);
        userRepository.save(user);


    }

    public List<UserRating> retrieveAllUsersRating(Pageable pageable) {
        return userRatingRepository.findAll(pageable).getContent().stream().collect(Collectors.toList());
    }

    public UserRating retrieveUserRating(int id) {
        return userRatingRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found in our records"));
    }

}
