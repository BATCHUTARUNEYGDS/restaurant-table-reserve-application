package com.reserve.restaurantservice.controller;

import com.reserve.restaurantservice.dao.RestaurantDao;
import com.reserve.restaurantservice.dto.RatingCountDto;
import com.reserve.restaurantservice.dto.RestaurantDto;
import com.reserve.restaurantservice.dto.RestaurantRatingDto;
import com.reserve.restaurantservice.dto.RestaurantTypeDto;
import com.reserve.restaurantservice.entities.Restaurant;
import com.reserve.restaurantservice.entities.RestaurantFilter;
import com.reserve.restaurantservice.entities.RestaurantLocation;
import com.reserve.restaurantservice.entities.RestaurantType;
import com.reserve.restaurantservice.mapper.RestaurantMapper;
import com.reserve.restaurantservice.repository.RestaurantLocationRepository;
import com.reserve.restaurantservice.repository.RestaurantRepository;
import com.reserve.restaurantservice.service.RestaurantLocationService;
import com.reserve.restaurantservice.service.RestaurantRatingService;
import com.reserve.restaurantservice.service.RestaurantService;
import com.reserve.restaurantservice.service.RestaurantTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@CrossOrigin("*")
@RequestMapping("/public/restaurant")
public class PublicRestaurantController {

    @Autowired
    RestaurantTypeService restaurantTypeService;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    RestaurantLocationService restaurantLocationService;
    @Autowired
    RestaurantDao restaurantDao;

    @Autowired
    RestaurantMapper restaurantMapper;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    RestaurantRatingService restaurantRatingService;

    @Autowired
    RestaurantLocationRepository restaurantLocationRepository;

    // RESTAURANT

    @GetMapping("/{restaurantId}")
    public Restaurant getRestaurantById(@PathVariable Integer restaurantId) {
        return restaurantService.getRestaurantById(restaurantId);
    }

    @GetMapping("/all")
    public List<RestaurantDto> getAllRestaurants(Pageable pageable) {
        return restaurantService.getAllRestaurants(pageable);
    }

    @GetMapping("")
    public List<Restaurant> getRestaurants(Pageable pageable) {
        return restaurantService.getRestaurants(pageable);
    }

    @GetMapping("/{restaurantTypeId}/cuisineType")
    public List<RestaurantDto> getRestaurantByRestaurantType(@PathVariable Integer restaurantTypeId) {
        RestaurantType restaurantType1 = restaurantTypeService.getRestaurantTypeById(restaurantTypeId);
        return restaurantService.getRestaurantByRestaurantType(restaurantType1);
    }

    @GetMapping("/{city}/{cuisine}/cuisineType")
    public List<RestaurantDto> getRestaurantByCityAndCuisine(@PathVariable String city, @PathVariable String cuisine) {
        return restaurantService.getRestaurantsByCityAndCuisine(city, cuisine);
    }

    @GetMapping("/{pincode}/Pincode")
    public List<Restaurant> getRestaurantByPincode(@PathVariable Integer pincode, Pageable pageable) {
        RestaurantLocation restaurantLocation1 = restaurantLocationService.getRestaurantByLocation(pincode);
        return restaurantService.getRestaurantByLocation(restaurantLocation1, pageable);
    }

    @GetMapping("/{city}/city")
    public List<Restaurant> getRestaurentsByCity(@PathVariable String city, Pageable pageable) {
        return restaurantService.getByCity(city, pageable);
    }

    @PostMapping("/filters/")
    public List<Restaurant> getRestaurantByFilters(@RequestBody RestaurantFilter restaurantFilter) {
        return restaurantDao.findRestaurantByFilter(restaurantFilter);
    }

    @GetMapping("/filter/{city}/")
    public List<RestaurantDto> getRequiredRestaurants(@PathVariable String city, @RequestParam(required = false) String tag, @RequestParam(required = false) List<String> cuisine, @RequestParam(required = false) String sortBy, @RequestParam(required = false) String sortType, Pageable pageable) {
        if (sortBy.equals("approxForTwo")) {
            if (sortType.equals("asc")) {
                List<Restaurant> restaurantsPriceAsc = restaurantRepository.findByFiltersPriceAsc(city, tag, cuisine, pageable).getContent().stream().collect(Collectors.toList());
                return restaurantMapper.entityToDtos(restaurantsPriceAsc);
            } else {
                List<Restaurant> restaurantsPriceDesc = restaurantRepository.findByFiltersPriceDesc(city, tag, cuisine, pageable).getContent().stream().collect(Collectors.toList());
                return restaurantMapper.entityToDtos(restaurantsPriceDesc);
            }
        } else if (sortBy.equals("ratingAverage") && sortType.equals("desc")) {
            List<Restaurant> restaurantsRatingDesc = restaurantRepository.findByFiltersRating(city, tag, cuisine, pageable).stream().collect(Collectors.toList());
            return restaurantMapper.entityToDtos(restaurantsRatingDesc);
        } else {
            List<Restaurant> restaurantsRatingAsc = restaurantRepository.findByFilters(city, tag, cuisine, pageable).stream().collect(Collectors.toList());
            return restaurantMapper.entityToDtos(restaurantsRatingAsc);
        }

    }

    @GetMapping("/{city}/restaurantNames")
    public List<String> getRestaurantsByName(@PathVariable String city) {
        return restaurantService.getRestaurantNamesByCity(city);
    }

    // RESTAURANT RATING
    @GetMapping("/restaurantRating/{restaurantId}/restaurant")
    public List<RestaurantRatingDto> getRatingsGivenToRestaurantId(@PathVariable Integer restaurantId, Pageable pageable) {
        return restaurantRatingService.getrestaurantRatingByRestaurantId(restaurantId, pageable);
    }

    // RATING COUNT
    @GetMapping("/ratingCount/{restaurantId}")
    public RatingCountDto getRatingCountByRestaurantId(@PathVariable Integer restaurantId) {
        return restaurantRatingService.getRatingCountByRestaurantId(restaurantId);
    }


    //RESTAURANT TYPE

    @GetMapping("/ObjectcuisineTypes")
    public List<RestaurantTypeDto> getAllCuisineTypeObjects() {
        List<RestaurantTypeDto> cuisineObjects = restaurantTypeService.getCuisineObjects();
        return cuisineObjects;
    }

    @GetMapping("/cuisineTypes")
    public List<String> getAllCuisineTypeNames() {
        List<String> cuisineNames = restaurantTypeService.getAllRestaurantTypeNames();
        return cuisineNames;
    }

    //LOCATION

    @GetMapping("/location/cities")
    public Set<String> getAllCities() {
        List<String> cityList = restaurantLocationRepository.findAllCities();
        Set<String> citySet = cityList.stream().collect(Collectors.toSet());
        return citySet;
    }

    @GetMapping("/location/{city}/area")
    public Set<String> getAllAreasByCity(@PathVariable String city) {
        List<String> areaList = restaurantLocationRepository.findAllAreasByCity(city);
        Set<String> areaSet = areaList.stream().collect(Collectors.toSet());
        return areaSet;
    }

    @GetMapping("/location/{city}/{area}/pincode")
    public Set<Integer> getAllPincodesByCityArea(@PathVariable String city, @PathVariable String area) {
        List<Integer> pincodeList = restaurantLocationRepository.findAllPincodesByCityArea(city, area);
        Set<Integer> pincodeSet = pincodeList.stream().collect(Collectors.toSet());
        return pincodeSet;
    }

    @GetMapping("/location/{pincode}/pincode")
    public RestaurantLocation getLocationByPincode(@PathVariable Integer pincode) {
        return restaurantLocationService.getRestaurantByLocation(pincode);
    }


    @GetMapping("/restaurantLocation")
    public List<RestaurantLocation> getRestaurantLocations(Pageable pageable) {
        return restaurantLocationService.getRestaurantLocations(pageable);
    }

    //TOP 3
    @GetMapping("/Top3/{city}")
    public List<Restaurant> get3RestaurantsByCity(@PathVariable String city) {
        return restaurantService.getTop3(city);
    }


}
