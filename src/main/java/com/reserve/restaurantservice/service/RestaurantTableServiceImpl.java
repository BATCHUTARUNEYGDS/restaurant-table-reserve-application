package com.reserve.restaurantservice.service;

import com.reserve.restaurantservice.entities.Restaurant;
import com.reserve.restaurantservice.entities.RestaurantTable;
import com.reserve.restaurantservice.exception.NotFoundException;
import com.reserve.restaurantservice.repository.ReservationRepository;
import com.reserve.restaurantservice.repository.RestaurantRepository;
import com.reserve.restaurantservice.repository.RestaurantTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantTableServiceImpl implements RestaurantTableService {

    @Autowired
    RestaurantTableRepository restaurantTableRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    ReservationRepository reservationRepository;


    public RestaurantTable createRestaurantTable(RestaurantTable restaurantTable) {
        return restaurantTableRepository.save(restaurantTable);
    }


    public RestaurantTable getRestaurantTableByTableId(Integer tableId) {
        RestaurantTable restaurantTable1 = restaurantTableRepository.findById(tableId).orElse(null);
        if (restaurantTable1 == null) {
            throw new NotFoundException("No table exists with provide TableId : " + tableId);
        } else {
            return restaurantTable1;
        }
    }


    public List<RestaurantTable> getAllRestaurantTablesByRestaurantId(Integer restaurantId) {
        Restaurant restaurant1 = restaurantRepository.findById(restaurantId).orElse(null);
        if (restaurant1 == null) {
            throw new NotFoundException("No Restaurant Account Exists with given Restaurant id : " + restaurantId);
        } else {
            return restaurantTableRepository.findByTheRestaurant(restaurant1);
        }
    }


    public List<RestaurantTable> getAllRestaurantTablesByRestaurantName(String restaurantName) {
        Restaurant restaurant3 = restaurantRepository.findByrestaurantName(restaurantName).orElse(null);
        if (restaurant3 == null) {
            throw new NotFoundException("No Restaurant Account Exists with given Restaurant name: " + restaurantName);
        } else {
            return restaurantTableRepository.findByTheRestaurant(restaurant3);
        }
    }

    @Override
    public String deleteRestaurantTableByTableId(Integer tableId) {
        RestaurantTable restaurantTable2 = restaurantTableRepository.findById(tableId).orElse(null);
        if (restaurantTable2 == null) {
            throw new NotFoundException("This table does not exist");
        } else {
            restaurantTableRepository.deleteById(tableId);
            return "This table was removed from the restaurant " + restaurantTable2.getRestaurant().getRestaurantName();
        }
    }

    @Override
    public ResponseEntity<String> deleteAllRestaurantTablesByRestaurantId(Integer restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        List<RestaurantTable> Tables = restaurantTableRepository.findByTheRestaurant(restaurant);
        for (int i = 0; i < Tables.size(); i++) {
            restaurantTableRepository.deleteById(Tables.get(i).getTableId());
        }
        return ResponseEntity.ok("All tables deleted");
    }
}
