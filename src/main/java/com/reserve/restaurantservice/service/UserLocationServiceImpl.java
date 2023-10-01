package com.reserve.restaurantservice.service;

import com.reserve.restaurantservice.entities.UserLocation;
import com.reserve.restaurantservice.exception.NotFoundException;
import com.reserve.restaurantservice.repository.UserLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserLocationServiceImpl implements UserLocationService {
    @Autowired
    UserLocationRepository userLocationRepository;

    public UserLocation createUserLocation(UserLocation userLocation) {
        return userLocationRepository.save(userLocation);
    }

    public void deleteUserLocation(Integer id) {
        userLocationRepository.deleteById(id);
    }

    public UserLocation retrieveUserLocation(Integer id) {
        return userLocationRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found in our records"));
    }

    public List<UserLocation> retrieveAllUsersLocation(Pageable pageable) {
        return userLocationRepository.findAll(pageable).getContent().stream().collect(Collectors.toList());
    }

    public UserLocation findByUserLocationId(Integer userLocationId) {
        return userLocationRepository.findById(userLocationId).orElse(null);
    }

    @Override
    public UserLocation editUserLocation(Integer UserLocationId, UserLocation userLocation) {
        UserLocation Location = userLocationRepository.findById(UserLocationId).orElse(null);
        if (Location != null) {
            Location.setUserArea(userLocation.getUserArea());
            Location.setUserCity(userLocation.getUserCity());
            Location.setUserPinCode(userLocation.getUserPinCode());
            userLocationRepository.save(Location);
            return Location;
        } else {
            throw new NotFoundException("No location exists ");
        }
    }

}
