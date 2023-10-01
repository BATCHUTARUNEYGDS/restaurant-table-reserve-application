package com.reserve.restaurantservice.repository;

import com.reserve.restaurantservice.entities.Reservation;
import com.reserve.restaurantservice.entities.Restaurant;
import com.reserve.restaurantservice.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findByRestaurant(Restaurant restaurant);

    List<Reservation> findByUser(User user);

    List<Reservation> findByDate(LocalDate date);

    @Query("SELECT re FROM Reservation re WHERE re.date = ?1 AND re.timeSlot = ?2")
    List<Reservation> findByDateTimeSlot(LocalDate date, LocalTime timeSlot);

    @Query("SELECT re FROM Reservation re WHERE re.restaurant = ?1 AND re.date = ?2")
    List<Reservation> findByRestaurantDate(Restaurant restaurant, LocalDate date);

    Page<Reservation> findAll(Pageable pageable);
}
