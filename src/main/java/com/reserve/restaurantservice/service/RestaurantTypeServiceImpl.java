package com.reserve.restaurantservice.service;

import com.reserve.restaurantservice.dto.RestaurantTypeDto;
import com.reserve.restaurantservice.entities.RestaurantType;
import com.reserve.restaurantservice.exception.NotFoundException;
import com.reserve.restaurantservice.mapper.RestaurantTypeMapper;
import com.reserve.restaurantservice.repository.RestaurantTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantTypeServiceImpl implements RestaurantTypeService {

    @Autowired
    RestaurantTypeRepository restaurantTypeRepository;

    @Autowired(required = false)
    RestaurantTypeDto restaurantTypeDto;

    @Autowired
    RestaurantTypeMapper restaurantTypeMapper;

    @Override
    public RestaurantType getRestaurantTypeById(Integer restaurantTypeId) {
        return restaurantTypeRepository.findById(restaurantTypeId).orElse(null);
    }

    @Override
    public List<RestaurantType> getAllRestaurantTypes() {
        return restaurantTypeRepository.findAll();
    }

    @Override
    public RestaurantType createRestaurantType(RestaurantType restaurantType) {
        return restaurantTypeRepository.save(restaurantType);
    }

    @Override
    public RestaurantType getRestaurantTypeByCuisineName(String cuisine) {
        return restaurantTypeRepository.findByCuisine(cuisine);
    }

    @Override
    public RestaurantType updateRestaurantType(String cuisine, RestaurantType restaurantType) {
        RestaurantType restaurantType8 = restaurantTypeRepository.findByCuisine(cuisine);
        if (restaurantType8 != null) {
            if (restaurantType.getCuisine() != null) {
                restaurantType8.setCuisine(restaurantType.getCuisine());
                return restaurantType8;
            } else {
                return restaurantType8;
            }
        } else {
            throw new NotFoundException("Cant edit no restaurantType exist with name : " + cuisine);
        }
    }

    @Override
    public List<String> getAllRestaurantTypeNames() {
        return restaurantTypeRepository.findAllCuisine();
    }

    @Override
    public List<RestaurantTypeDto> getCuisineObjects() {
        List<RestaurantType> restaurantTypeEntity = restaurantTypeRepository.findAll();
        return restaurantTypeMapper.entityToDtos(restaurantTypeEntity);
    }

    @Override
    public ResponseEntity<String> deleteCuisine(String cuisine) {
        Optional<RestaurantType> cuisineType = Optional.ofNullable(restaurantTypeRepository.findByCuisine(cuisine));
        if (cuisineType == null) {
            throw new NotFoundException("No cuisine type exists with the name : " + cuisine);
        } else {
            restaurantTypeRepository.deleteById(cuisineType.get().getRestaurantTypeId());
            return ResponseEntity.ok("Cuisine type deleted");
        }
    }
}