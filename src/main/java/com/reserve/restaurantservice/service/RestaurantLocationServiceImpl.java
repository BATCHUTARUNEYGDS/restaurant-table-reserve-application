package com.reserve.restaurantservice.service;

import com.reserve.restaurantservice.entities.RestaurantLocation;
import com.reserve.restaurantservice.exception.NotFoundException;
import com.reserve.restaurantservice.repository.RestaurantLocationRepository;
import com.reserve.restaurantservice.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantLocationServiceImpl implements RestaurantLocationService {

    @Autowired
    RestaurantLocationRepository restaurantLocationRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Override
    public RestaurantLocation getRestaurantByLocation(int pincode) {
        RestaurantLocation locationpincode = restaurantLocationRepository.findByPincode(pincode);
        if (locationpincode == null) {
            throw new NotFoundException("No pincode located with number : " + pincode);
        } else {
            return locationpincode;
        }
    }

    @Override
    public List<RestaurantLocation> getRestaurantByLocation(String city) {
        List<RestaurantLocation> restaurantCity = restaurantLocationRepository.findAllByCity(city);
        if (restaurantCity == null) {
            throw new NotFoundException("No location with name " + city + " is situated");
        }
        return restaurantCity;
    }

    @Override
    public RestaurantLocation getRestaurantLocationByRestaurantLocationId(Integer restaurantLocationId) {
        RestaurantLocation restaurantLocation4 = restaurantLocationRepository.findById(restaurantLocationId).orElse(null);
        if (restaurantLocation4 == null) {
            throw new NotFoundException("No restaurant location present at this Id " + restaurantLocationId);
        } else {
            return restaurantLocation4;
        }
    }


    @Override
    public List<RestaurantLocation> getRestaurantLocations(Pageable pageable) {
        Page<RestaurantLocation> locations = restaurantLocationRepository.findAll(pageable);
        if (locations.isEmpty()) {
            throw new NotFoundException("no loctions until now");
        } else {
            return locations.getContent().stream().collect(Collectors.toList());
        }
    }

    @Override
    public RestaurantLocation createRestaurantLocation(RestaurantLocation restaurantLocation) {
        return restaurantLocationRepository.save(restaurantLocation);
    }

    @Override
    public RestaurantLocation updateRestaurantLocation(Integer restaurantLocationId, RestaurantLocation restaurantLocation) {
        RestaurantLocation Location = restaurantLocationRepository.findById(restaurantLocationId).orElse(null);

        restaurantLocation.setArea(Location.getArea());
        restaurantLocation.setCity(Location.getCity());
        restaurantLocation.setPincode(Location.getPincode());

        return restaurantLocationRepository.save(Location);
    }

    @Override
    public ResponseEntity<String> deleteRestaurantLocation(Integer pincode) {
        Optional<RestaurantLocation> restaurantLocation = Optional.ofNullable(restaurantLocationRepository.findByPincode(pincode));
        if (restaurantLocation != null) {
            restaurantLocationRepository.deleteById(restaurantLocation.get().getLocationId());
            return ResponseEntity.ok("Location deleted");
        } else {
            throw new NotFoundException("No location exists with given data");
        }

    }


}
