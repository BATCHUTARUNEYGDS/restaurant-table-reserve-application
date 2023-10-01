package com.reserve.restaurantservice.service;
import com.reserve.restaurantservice.entities.UserRating;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface UserRatingService {
    public void createUserRating(UserRating userRating);
    public List<UserRating> retrieveAllUsersRating(Pageable pageable);
    public UserRating retrieveUserRating(int id);


}
