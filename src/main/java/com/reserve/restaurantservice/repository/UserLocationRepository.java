package com.reserve.restaurantservice.repository;

import com.reserve.restaurantservice.entities.User;
import com.reserve.restaurantservice.entities.UserLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface UserLocationRepository extends JpaRepository<UserLocation, Integer> {
    Page<UserLocation> findAll(Pageable pageable);

    @Query(value="select user_city from user_location", nativeQuery=true)
    List<String> findAllCities();

    @Query(value = "select user_area from user_location where user_location.user_city=?1", nativeQuery=true)
    List<String> findAllAreasByCity(String city);

    @Query(value = "select user_pin_code from user_location where user_location.user_city=?1 and user_location.user_area=?2", nativeQuery=true)
    List<Integer> findAllPincodesByCityArea(String city,String area);


    Optional<UserLocation> findByUserPinCode(Integer setPinCode);

}
