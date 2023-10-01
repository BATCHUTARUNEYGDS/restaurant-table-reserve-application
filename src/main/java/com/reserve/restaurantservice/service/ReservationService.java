package com.reserve.restaurantservice.service;

import com.google.zxing.WriterException;
import com.reserve.restaurantservice.dto.CreateReservationDto;
import com.reserve.restaurantservice.dto.ReservationDashboardDto;
import com.reserve.restaurantservice.dto.ReservationDto;
import com.reserve.restaurantservice.dto.UpdateReservationDto;
import com.reserve.restaurantservice.entities.Reservation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationService {


    ResponseEntity<String> BookReservation(CreateReservationDto reservationDto) throws MessagingException;

    ResponseEntity<byte[]> BookReservationQR(CreateReservationDto reservationDto) throws MessagingException, IOException, WriterException;

    Reservation getReservationById(Integer reservationId);

    List<ReservationDto> getAllReservations(Pageable pageable);

    List<ReservationDashboardDto> getAllReservationsBydate(LocalDate date);

    List<ReservationDto> getAllReservationsByDatetime(LocalDate date, LocalTime time);

    List<ReservationDto> getAllReservationsByRestaurant(Integer restaurantId);

    List<ReservationDashboardDto> getAllReservationsByRestaurantDate(Integer restaurantId, LocalDate date);

    List<ReservationDto> getAllReservationsByUser(Integer userId);

    //List<Reservation> getAllReservationsByRestaurantTable(Integer restaurantTableId);

    String deleteReservation(Integer reservationId);

    Reservation updateReservation(Integer userId, Integer restaurantId, Reservation reservation);

    ResponseEntity<String> editReservation(UpdateReservationDto updateReservationDto) throws MessagingException;

    List<ReservationDashboardDto> currentRestaurantReservations(Integer restaurantId);

    List<ReservationDashboardDto> pastRestaurantReservations(Integer restaurantId);

    List<ReservationDashboardDto> currentUserReservations(Integer userId);

    List<ReservationDashboardDto> pastUserReservations(Integer userId);

    List<ReservationDashboardDto> getAllReservationsByRestaurantDateTime(Integer restaurantId, LocalDate reservedate, LocalTime time1, LocalTime time2);
}
