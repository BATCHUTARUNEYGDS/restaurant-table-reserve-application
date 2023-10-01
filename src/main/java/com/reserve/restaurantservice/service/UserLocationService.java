package com.reserve.restaurantservice.service;

import com.reserve.restaurantservice.entities.User;
import com.reserve.restaurantservice.entities.UserLocation;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserLocationService {

    public UserLocation createUserLocation(UserLocation userLocation);

    public void deleteUserLocation( Integer id);

    public UserLocation retrieveUserLocation(Integer id);

    public List<UserLocation> retrieveAllUsersLocation(Pageable pageable);

    public UserLocation findByUserLocationId(Integer userLocationId);

    UserLocation editUserLocation(Integer UserLocationId,UserLocation userLocation);


}
