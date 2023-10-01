package com.reserve.restaurantservice.controller;

import com.reserve.restaurantservice.dto.UserDto;
import com.reserve.restaurantservice.entities.Restaurant;
import com.reserve.restaurantservice.entities.User;
import com.reserve.restaurantservice.entities.UserLocation;
import com.reserve.restaurantservice.entities.UserRating;
import com.reserve.restaurantservice.repository.UserLocationRepository;
import com.reserve.restaurantservice.repository.UserRatingRepository;
import com.reserve.restaurantservice.repository.UserRepository;
import com.reserve.restaurantservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserLocationRepository userLocationRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserLocationService userLocationService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRatingService userRatingService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserRatingRepository userRatingRepository;
    @Autowired
    private EmailService emailService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public ResponseEntity<List<UserDto>> retrieveAllUsers(Pageable pageable) {
        return userService.retrieveAllUsers(pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/location")
    UserLocation createUserLocation(@RequestBody UserLocation userLocation) {
        return userLocationService.createUserLocation(userLocation);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/{userId}/{restaurantId}/rating")
    public ResponseEntity<String> createUserRating(@PathVariable Integer userId, @PathVariable Integer restaurantId, @RequestBody UserRating userRating) {
        User user = userService.findByUserId(userId);
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        userRating.setUser(user);
        userRating.setRestaurant(restaurant);
        userRatingService.createUserRating(userRating);
        return ResponseEntity.ok("Rating submitted successfully");
    }


//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
//    @PutMapping("/{userId}/userLocation/{locationId}")
//    public User assignLocationToUser(@PathVariable Integer userId, @PathVariable Integer locationId)
//    {
//        User user = userService.findByUserId(userId);
//        UserLocation userLocation = userLocationService.findByUserLocationId(locationId);
//        user.setUserLocation(userLocation);
//        return userRepository.save(user);
//    }

//    @PreAuthorize("hasRole('USER')")
//    @PutMapping("")
//    public User updateUser(@RequestBody User user){
//        return userService.updateUser(user);
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{pincode}")
    ResponseEntity<String> deleteUserLocation(@PathVariable Integer pincode) {
        UserLocation ulocation = userLocationRepository.findByUserPinCode(pincode).orElse(null);
        userLocationService.deleteUserLocation(ulocation.getUserLocationId());
        return ResponseEntity.ok("User Location deleted");
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{userLocationId}/updateLocation")
    UserLocation updateLocation(@PathVariable Integer userLocationId, @RequestBody UserLocation userLocation) {
        return userLocationService.editUserLocation(userLocationId, userLocation);
    }

}

