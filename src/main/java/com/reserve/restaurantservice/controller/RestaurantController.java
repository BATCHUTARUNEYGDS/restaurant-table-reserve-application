package com.reserve.restaurantservice.controller;

import com.reserve.restaurantservice.dto.CreateRestaurantDto;
import com.reserve.restaurantservice.dto.RestaurantRatingDto;
import com.reserve.restaurantservice.dto.RestaurantTableDto;
import com.reserve.restaurantservice.entities.*;
import com.reserve.restaurantservice.mapper.RestaurantLocationMapper;
import com.reserve.restaurantservice.mapper.RestaurantTableMapper;
import com.reserve.restaurantservice.repository.RestaurantManagerRepository;
import com.reserve.restaurantservice.repository.RestaurantRepository;
import com.reserve.restaurantservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    RestaurantLocationService restaurantLocationService;

    @Autowired
    RestaurantRatingService restaurantRatingService;

    @Autowired
    RestaurantTableService restaurantTableService;

    @Autowired
    UserService userService;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    RestaurantTypeService restaurantTypeService;

    @Autowired
    RestaurantLocationMapper restaurantLocationMapper;

    @Autowired
    RestaurantTableMapper restaurantTableMapper;

    @Autowired
    UserLocationService userLocationService;

    @Autowired
    RestaurantManagerService restaurantManagerService;
    @Autowired
    private RestaurantManagerRepository restaurantManagerRepository;


    //CREATE
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/restaurantLocation")
    public ResponseEntity<String> createRestaurantLocation(@Valid @RequestBody RestaurantLocation restaurantLocation) {
        RestaurantLocation rl = restaurantLocationService.createRestaurantLocation(restaurantLocation);
        restaurantLocationMapper.entityToDto(rl);
        return ResponseEntity.status(HttpStatus.CREATED).body("A new location was created");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/cuisineType")
    public ResponseEntity<String> createCuisineType(@Valid @RequestBody RestaurantType restaurantType) {
        RestaurantType cuisine = restaurantTypeService.createRestaurantType(restaurantType);
        return ResponseEntity.status(HttpStatus.CREATED).body("A new cuisine was created");
    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/restaurantRating/toRestaurant/{restaurantId}/byUser/{userId}")
    public ResponseEntity<String> createRestaurantRating(@PathVariable Integer restaurantId, @PathVariable Integer userId, @Valid @RequestBody RestaurantRating restaurantRating) {
        Restaurant restaurant9 = restaurantService.getRestaurantById(restaurantId);
        User user1 = userService.findByUserId(userId);
        restaurantRating.setRestaurant(restaurant9);
        restaurantRating.setUser(user1);
        restaurantRatingService.createRestaurantRating(restaurantRating);
        return ResponseEntity.status(HttpStatus.CREATED).body("Rating was submitted");
    }

//GET


    //GET RESTAURANT


    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{restaurantTag}/Tag")
    public List<Restaurant> getRestaurantByRestaurantTag(@PathVariable String restaurantTag) {
        return restaurantService.getRestaurantByRestaurantTag(restaurantTag);
    }

    @GetMapping("/trail/{city}")
    public List<Restaurant> trail(@PathVariable String city, @RequestParam(required = false) String tag, @RequestParam(required = false) List<String> cuisine, @RequestParam(required = false) String sortBy, @RequestParam(required = false) String sortType, Pageable pageable) {
        return restaurantRepository.Trail(city, tag, cuisine, pageable).getContent().stream().collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/manager/{managerId}")
    public Optional<Restaurant> getRestaurantByManager(@PathVariable Integer managerId) {
        return restaurantService.getRestaurantByManager(managerId);
    }


    //GET RESTAURANT LOCATION

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{restaurantLocationId}/restaurantLocation")
    public RestaurantLocation getRestaurantLocationById(@PathVariable Integer restaurantLocationId) {
        return restaurantLocationService.getRestaurantLocationByRestaurantLocationId(restaurantLocationId);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN','MANAGER')")
    @GetMapping("/location/{city}/city")
    public List<RestaurantLocation> getLocationByCity(@PathVariable String city) {
        return restaurantLocationService.getRestaurantByLocation(city);
    }


    //GET RESTAURANT RATING

    @PreAuthorize("hasAnyRole('USER','MANAGER')")
    @GetMapping("/restaurantRating/{restaurantRatingId}")
    public RestaurantRating getRestaurantRatingByRestaurantRatingId(@PathVariable Integer restaurantRatingId) {
        return restaurantRatingService.getRestaurantRatingByRestaurantRatingId(restaurantRatingId);
    }

    @PreAuthorize("hasAnyRole('USER','MANAGER')")
    @GetMapping("/restaurantRating/{userId}/user")
    public List<RestaurantRatingDto> getRatingsGivenByUserId(@PathVariable Integer userId) {
        return restaurantRatingService.getrestaurantRatingByUserId(userId);
    }

    //GET RESTAURANT TABLE

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/restaurantTable/{restaurantTableId}")
    public RestaurantTable getRestaurantTableByRestaurantTableId(@PathVariable Integer restaurantTableId) {
        return restaurantTableService.getRestaurantTableByTableId(restaurantTableId);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/restaurantTable/{restaurantId}/restaurant")
    public List<RestaurantTableDto> getRestaurantTablesOfRestaurantId(@PathVariable Integer restaurantId) {
        List<RestaurantTable> tables = restaurantTableService.getAllRestaurantTablesByRestaurantId(restaurantId);
        return restaurantTableMapper.entityToDtos(tables);
    }


//PUT

    //RESTAURANT

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{restaurantId}/restaurantLocation/{locationId}")
    Restaurant assignLocationToRestaurant(@PathVariable Integer restaurantId, @PathVariable Integer locationId) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        RestaurantLocation restaurantLocation = restaurantLocationService.getRestaurantLocationByRestaurantLocationId(locationId);
        restaurant.assignLocation(restaurantLocation);
        return restaurantRepository.save(restaurant);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{restaurantLocationId}/updateLocation")
    RestaurantLocation updateLocation(@PathVariable Integer restaurantLocationId, @RequestBody RestaurantLocation restaurantLocation) {
        return restaurantLocationService.updateRestaurantLocation(restaurantLocationId, restaurantLocation);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{restaurantTypeName}/updateCuisine")
    RestaurantType updateCuisine(@PathVariable String restaurantTypeName, @RequestBody RestaurantType restaurantType) {
        return restaurantTypeService.updateRestaurantType(restaurantTypeName, restaurantType);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{restaurantId}/updateRestaurant")
    ResponseEntity<String> updateRestaurant(@PathVariable Integer restaurantId, @RequestBody CreateRestaurantDto restaurant) {
        return restaurantService.updateRestaurant(restaurantId, restaurant);
    }


//DELETE

    //RESTAURANT

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteRestaurant/{restaurantId}")
    ResponseEntity<String> deleteRestaurantById(@PathVariable Integer restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if (!restaurant.isEmpty()) {
            restaurant.get().setRestaurantLocation(null);
            restaurantRatingService.deleteRatingCountByRestaurantId(restaurantId);
            restaurantRatingService.deleteAllRatingsByRestaurantId(restaurantId);
            restaurantTableService.deleteAllRestaurantTablesByRestaurantId(restaurantId);
            return ResponseEntity.ok("restaurant deleted");
        } else {
            return ResponseEntity.ok("restaurant not exists");
        }
    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{tableId}")
    String deleteRestaurantTableById(@PathVariable Integer tableId) {
        return restaurantTableService.deleteRestaurantTableByTableId(tableId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteCuisine/{cuisineName}")
    ResponseEntity<String> deleteCuisineType(@PathVariable String cuisineName) {
        return restaurantTypeService.deleteCuisine(cuisineName);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteLocation/{pincode}")
    ResponseEntity<String> deleteRestaurantLocation(@PathVariable Integer pincode) {
        return restaurantLocationService.deleteRestaurantLocation(pincode);
    }
}
