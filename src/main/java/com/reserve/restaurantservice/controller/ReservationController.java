package com.reserve.restaurantservice.controller;

import com.google.zxing.WriterException;
import com.reserve.restaurantservice.dto.CreateReservationDto;
import com.reserve.restaurantservice.dto.ReservationDashboardDto;
import com.reserve.restaurantservice.dto.ReservationDto;
import com.reserve.restaurantservice.dto.UpdateReservationDto;
import com.reserve.restaurantservice.entities.Reservation;
import com.reserve.restaurantservice.entities.ReservationDecline;
import com.reserve.restaurantservice.entities.RestaurantTable;
import com.reserve.restaurantservice.entities.User;
import com.reserve.restaurantservice.exception.AlreadyExistException;
import com.reserve.restaurantservice.exception.NotFoundException;
import com.reserve.restaurantservice.mapper.ReservationMapper;
import com.reserve.restaurantservice.repository.ReservationRepository;
import com.reserve.restaurantservice.repository.UserRepository;
import com.reserve.restaurantservice.service.EmailService;
import com.reserve.restaurantservice.service.ReservationService;
import com.reserve.restaurantservice.service.RestaurantTableService;
import com.reserve.restaurantservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @Autowired
    RestaurantTableService restaurantTableService;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    ReservationMapper reservationMapper;


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody CreateReservationDto reservation) throws MessagingException {
        return reservationService.BookReservation(reservation);
    }

    @PostMapping(value = "/createQR", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> createQR(@RequestBody CreateReservationDto reservation) throws MessagingException, IOException, WriterException {
        return reservationService.BookReservationQR(reservation);
    }


    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/restaurant/{restaurantId}/date/{date}")
    public List<ReservationDashboardDto> getAllReservationsByRestaurantOnDate(@PathVariable Integer restaurantId, @PathVariable String date) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate reservedate = LocalDate.parse(date, format);
        List<ReservationDashboardDto> reservationDashboardDtoList = reservationService.getAllReservationsByRestaurantDate(restaurantId, reservedate);
        return reservationDashboardDtoList;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/restaurant/{restaurantId}/date/{date}/{time}")
    public List<ReservationDashboardDto> getAllReservationsByRestaurantOnDateTime(@PathVariable Integer restaurantId, @PathVariable String date, @PathVariable String time) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate reservedate = LocalDate.parse(date, format);
        List<ReservationDashboardDto> reservationDashboardDtoList = new ArrayList<>();
        if (time.equalsIgnoreCase("Morning")) {
            LocalTime time1 = LocalTime.parse("00:00:00");
            LocalTime time2 = LocalTime.parse("11:59:59");
            reservationDashboardDtoList = reservationService.getAllReservationsByRestaurantDateTime(restaurantId, reservedate, time1, time2);
        } else if (time.equalsIgnoreCase("Afternoon")) {
            LocalTime time1 = LocalTime.parse("12:00:00");
            LocalTime time2 = LocalTime.parse("16:00:00");
            reservationDashboardDtoList = reservationService.getAllReservationsByRestaurantDateTime(restaurantId, reservedate, time1, time2);
        } else if (time.equalsIgnoreCase("Evening")) {
            LocalTime time1 = LocalTime.parse("16:00:00");
            LocalTime time2 = LocalTime.parse("18:30:00");
            reservationDashboardDtoList = reservationService.getAllReservationsByRestaurantDateTime(restaurantId, reservedate, time1, time2);
        } else if (time.equalsIgnoreCase("Night")) {
            LocalTime time1 = LocalTime.parse("18:30:00");
            LocalTime time2 = LocalTime.parse("23:59:59");
            reservationDashboardDtoList = reservationService.getAllReservationsByRestaurantDateTime(restaurantId, reservedate, time1, time2);
        } else if (time.equalsIgnoreCase("all")) {
            reservationDashboardDtoList = reservationService.getAllReservationsByRestaurantDate(restaurantId, reservedate);

        }
        return reservationDashboardDtoList;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{reservationId}/tables/")
    public ResponseEntity<String> assignTableToReservation(@PathVariable Integer reservationId, @RequestParam final List<Integer> tableId) throws MessagingException {
        Reservation reservation = reservationService.getReservationById(reservationId);
        User user = userService.findByUserId(reservation.getUser().getUserId());
        if (reservation.getStatus().equals("pending")) {
            for (int i = 0; i < tableId.stream().count(); i++) {
                if (reservation.getRestaurant().getRestaurantTable().contains(restaurantTableService.getRestaurantTableByTableId(tableId.get(i)))) {
                    RestaurantTable restaurantTable = restaurantTableService.getRestaurantTableByTableId(tableId.get(i));
                    reservation.assignTableToReservation(restaurantTable);
                } else {
                    throw new NotFoundException("Please check the table Ids, few of the entered table Ids not belongs to your restaurant .");
                }
            }
            reservation.setStatus("approved");
            reservationRepository.save(reservation);
            emailService.sendEmail(user.getUserEmailId(),
                    "<p> Hey <b>" + user.getUserFullName() + "</b></p><p> your reservation was <b>accepted</b> 🎊 by the restaurant <b>" + reservation.getRestaurant().getRestaurantName() + "</b></p><p> You were reserved for :<b>" + tableId.stream().count() + "</b> tables." + "<p> Your tables id's were <b>" + tableId + "</b></p> Get ready to visit the restaurant on 📅<b>" + reservation.getDate() + "</b> at ⏰ <b>" + reservation.getTimeSlot()
                            + "</b> </p> <p> </p> <p> Thanks for reservation 💖 ," + " your status was <b>" + reservation.getStatus() + "</b></p> <p> Please note the location 📍" + reservation.getRestaurant().getFullAddress() + ".</p>" + "<p> </p>" + "<p> </p>" + "<p> </p>" + "<p>For any further help you can contact </p>" + "<p>📞 Ph no: <b>" + reservation.getRestaurant().getRestaurantContactNumber() + "</b></p> <p>📧 Mail: <b>" + reservation.getRestaurant().getRestaurantMail() + "</b></p>.", "RESERVATION ACCEPTED ✅");
            return ResponseEntity.ok().body("assigning tables was successful and email was sent to user " + user.getUserEmailId());
        } else if (reservation.getStatus().equals("approved")) {
            throw new AlreadyExistException("already approved and tables were assigned to this reservation");
        } else if (reservation.getStatus().equals("declined")) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("This reservation was declined so cant approve now");
        } else if (reservation.getStatus().equals("Completed")) {
            throw new AlreadyExistException("already this reservation was approved and tables assigned and service completed");
        } else {
            return ResponseEntity.ok().body("Once Check the status");
        }
    }


    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/decline")
    public ResponseEntity<String> declineReservationDto(@RequestBody ReservationDecline reservationDecline) throws MessagingException {
        //Reservation reservation = reservationService.getReservationById(reservationDecline.getReservationId());
        Reservation reservation = reservationRepository.findById(reservationDecline.getReservationId()).orElse(null);
        if (reservation.getStatus().equals("approved")) {
            reservation.setStatus("declined");
            emailService.sendEmail(reservation.getUser().getUserEmailId(), "<p> Dear <b>" + reservation.getUser().getUserFullName() + "</b>,</p>" + " <p>Sorry your reservation was canceled. 😌 </p>" + "<p> We know that initially your reservation was accepted but due to some reasons your reservation was canceled by restaurant : <b>" + reservation.getRestaurant().getRestaurantName() + "</b></p>" + " <p>The Reason for Declining is : <b>" + reservationDecline.getMessage() + "</b>" + " We sincerely apologize for this please book again in your convince</p>", "RESERVATION CANCELED 😌");
            reservationService.updateReservation(reservation.getUser().getUserId(), reservation.getRestaurant().getRestaurantId(), reservation);
            return ResponseEntity.ok().body("approved reservation was now declined and a mail was sent to user ");
        } else if (reservation.getStatus().equals("pending")) {
            reservation.setStatus("declined");
            emailService.sendEmail(reservation.getUser().getUserEmailId(), "<p> Dear <b>" + reservation.getUser().getUserFullName() + "</b>,</p>" + "<p> Sorry your reservation was not accepted by the restaurant 😌 <b>" + reservation.getRestaurant().getRestaurantName() + "</b></p>" + "<p> The Reason for Declining is <b>" + reservationDecline.getMessage() + "</b></p>" + "<p> We sincerely apologize for this please book again in your convince </p>", " RESERVATION NOT ACCEPTED ❌");
            reservationService.updateReservation(reservation.getUser().getUserId(), reservation.getRestaurant().getRestaurantId(), reservation);
            return ResponseEntity.ok().body("declining reservation was done and a mail was sent to user ");
        } else if (reservation.getStatus().equals("declined")) {
            return ResponseEntity.ok().body("This reservation was already declined by you");
        } else {
            return ResponseEntity.ok().body("please check the status");
        }
    }


    @PreAuthorize("hasAnyRole('USER','MANAGER')")
    @PutMapping("/edit")
    public ResponseEntity<String> updateReservation(@RequestBody UpdateReservationDto updateReservationDto) throws MessagingException {
        return reservationService.editReservation(updateReservationDto);
    }


    @PreAuthorize("hasRole('USER')")
    @PutMapping("{userId}/{reservationId}/cancel")
    public ResponseEntity<String> cancelReservation(@PathVariable Integer userId, @PathVariable Integer reservationId) throws MessagingException {

        Reservation initialreservation = reservationService.getReservationById(reservationId);
        if (initialreservation.getStatus().equals("approved")) {

            if ((initialreservation.getDate().isAfter(LocalDate.now()) || initialreservation.getDate().isEqual(LocalDate.now())) && LocalTime.now().isBefore(initialreservation.getTimeSlot().minusHours(4))) {
                initialreservation.setStatus("canceled");
                reservationService.updateReservation(userId, initialreservation.getRestaurant().getRestaurantId(), initialreservation);
                emailService.sendEmail(initialreservation.getUser().getUserEmailId(), "<p> Dear <b>" + initialreservation.getUser().getUserFullName() + "</b>,</p>" + "<p> As you requested, we had canceled your reservation on : <b>" + initialreservation.getDate() + "</b> at : <b>" + initialreservation.getTimeSlot() + "</b></p>" + "<p>we appreciate your time and we hope you will stay with us on your next booking!.</p>" + "<p>We were eagerly waiting for your next order 😊 </p>", "RESERVATION CANCELED 😌");
                return ResponseEntity.ok().body("Approved Reservation was canceled by User , check your email : " + initialreservation.getUser().getUserEmailId());
            } else {
                return ResponseEntity.ok().body("Approved Reservation cannot be canceled by User before 4 hours. ");
            }


        } else if (initialreservation.getStatus().equals("pending") && userId.equals(initialreservation.getUser().getUserId())) {
            initialreservation.setStatus("canceled");
            reservationService.updateReservation(userId, initialreservation.getRestaurant().getRestaurantId(), initialreservation);
            emailService.sendEmail(initialreservation.getUser().getUserEmailId(), "<p> Dear <b>" + initialreservation.getUser().getUserFullName() + "</b>,</p>" + "<p> As you requested, we had canceled your reservation on : <b>" + initialreservation.getDate() + "</b> at : <b>" + initialreservation.getTimeSlot() + "</b></p>" + "<p>we appreciate your time and we hope you will stay with us on your next booking!.</p>" + "<p>We were eagerly waiting for your next order 😊 </p>", "RESERVATION CANCELED 😌");
            return ResponseEntity.ok().body("Reservation was canceled check your email : " + initialreservation.getUser().getUserEmailId());
        } else if (initialreservation.getStatus().equals("declined")) {
            return ResponseEntity.ok().body("This reservation was already declined");
        } else {
            return ResponseEntity.ok().body("please check the status");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public List<ReservationDto> getAllReservations(Pageable pageable) {
        return reservationService.getAllReservations(pageable);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/restaurant/{restaurantId}")
    public List<ReservationDto> getAllReservationsOfaRestaurant(@PathVariable Integer restaurantId) {
        return reservationService.getAllReservationsByRestaurant(restaurantId);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/{userId}")
    public List<ReservationDto> getAllReservationsDoneByUser(@PathVariable Integer userId) {
        return reservationService.getAllReservationsByUser(userId);
    }

    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    @GetMapping("/{reservationId}")
    public Reservation getReservationById(@PathVariable Integer reservationId) {
        return reservationService.getReservationById(reservationId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{reservationId}")
    ResponseEntity<String> deleteReservationById(@PathVariable Integer reservationId) {
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.ok().body("reservation was deleted");
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("restaurant/{restaurantId}/pastReservations")
    List<ReservationDashboardDto> getRestaurantPastReservations(@PathVariable Integer restaurantId) {
        return reservationService.pastRestaurantReservations(restaurantId);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("restaurant/{restaurantId}/currentReservations")
    List<ReservationDashboardDto> getRestaurantCurrentReservations(@PathVariable Integer restaurantId) {
        return reservationService.currentRestaurantReservations(restaurantId);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("user/{userId}/pastReservations")
    List<ReservationDashboardDto> getUserPastReservations(@PathVariable Integer userId) {
        return reservationService.pastUserReservations(userId);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("user/{userId}/currentReservations")
    List<ReservationDashboardDto> getUserCurrentReservations(@PathVariable Integer userId) {
        return reservationService.currentUserReservations(userId);
    }

}
