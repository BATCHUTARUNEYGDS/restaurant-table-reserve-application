package com.reserve.restaurantservice.service;

import com.google.zxing.WriterException;
import com.reserve.restaurantservice.dto.CreateReservationDto;
import com.reserve.restaurantservice.dto.ReservationDashboardDto;
import com.reserve.restaurantservice.dto.ReservationDto;
import com.reserve.restaurantservice.dto.UpdateReservationDto;
import com.reserve.restaurantservice.entities.Reservation;
import com.reserve.restaurantservice.entities.Restaurant;
import com.reserve.restaurantservice.entities.RestaurantTable;
import com.reserve.restaurantservice.entities.User;
import com.reserve.restaurantservice.exception.CantCreateException;
import com.reserve.restaurantservice.exception.NotFoundException;
import com.reserve.restaurantservice.mapper.ReservationMapper;
import com.reserve.restaurantservice.mapper.RestaurantTableMapper;
import com.reserve.restaurantservice.repository.ReservationRepository;
import com.reserve.restaurantservice.repository.RestaurantRepository;
import com.reserve.restaurantservice.repository.RestaurantTableRepository;
import com.reserve.restaurantservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    RestaurantTableRepository restaurantTableRepository;

    @Autowired
    ReservationMapper reservationMapper;

    @Autowired
    EmailService emailService;

    @Autowired
    RestaurantTableMapper restaurantTableMapper;




    public ResponseEntity<String> BookReservation(CreateReservationDto reservationDto) throws MessagingException {
        LocalDate date1 = LocalDate.now();
        LocalTime time1 = LocalTime.now();
        if (reservationDto.getDate().isAfter(date1.minusDays(1)) && reservationDto.getTimeSlot().isAfter(time1)) {
            Reservation reservation = new Reservation();
            User user = userRepository.findById(reservationDto.getUserId()).orElse(null);
            Restaurant restaurant = restaurantRepository.findById(reservationDto.getRestaurantId()).orElse(null);
            reservation.setStatus("pending");
            reservation.setDate(reservationDto.getDate());
            reservation.setTimeSlot(reservationDto.getTimeSlot());
            reservation.setRestaurant(restaurantRepository.findById(reservationDto.getRestaurantId()).orElse(null));
            reservation.setUser(userRepository.findById(reservationDto.getUserId()).orElse(null));
            reservation.setGuestName(reservationDto.getGuestName());
            reservation.setNoOfGuests(reservationDto.getNoOfGuests());
            reservation.setTableCustomization(reservationDto.getTableCustomization());
            reservationRepository.save(reservation);
            emailService.sendEmail(user.getUserEmailId(),
               "<p> Hey <b>" + user.getUserFullName() + "</b></p>"+"<p> your reservation was successful 🎉 </p>" + "<p> With name <b>" + reservation.getGuestName() + "</b> in restaurant <b>" + restaurant.getRestaurantName() + "</b>.</p>" +"<p> On <b>" + reservation.getDate() +"</b> at <b>" + reservation.getTimeSlot() + "</b> for <b>" + reservation.getNoOfGuests() + "</b> members ,</p>"
                       +"<p>Customization Options : <b>"+reservation.getTableCustomization()+"</b> </p>"+" "+" "+"<p> Thanks for reservation  ,</p>"+ "<p> your status was " + reservation.getStatus() + " wait for the status to confirm. Please note the location 📍" + restaurant.getFullAddress() + ".</p>" + "<p> </p>" + "<p> </p>" + "<p> </p>" + "<p>For any further help you can contact </p>"  + "<p>📞 Ph no: " + restaurant.getRestaurantContactNumber() +"</p>"+ "<p>📧 Mail: " + restaurant.getRestaurantMail() + ".</p>", "🍽 RESERVATION SUCCESSFUL 🍽");
            return ResponseEntity.status(HttpStatus.CREATED).body("Reservation was success and a mail sent to : "+user.getUserEmailId());
        }
        else{
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Please provide the time after 1 day");
        }

    }

    public ResponseEntity<byte[]> BookReservationQR(CreateReservationDto reservationDto) throws MessagingException, IOException, WriterException {
        LocalDate date1 = LocalDate.now();
        LocalTime time1 = LocalTime.now();
        if (reservationDto.getDate().isAfter(date1.minusDays(1))) {
            Reservation reservation = new Reservation();
            User user = userRepository.findById(reservationDto.getUserId()).orElse(null);
            Restaurant restaurant = restaurantRepository.findById(reservationDto.getRestaurantId()).orElse(null);
            reservation.setStatus("pending");
            reservation.setDate(reservationDto.getDate());
            reservation.setTimeSlot(reservationDto.getTimeSlot());
            reservation.setRestaurant(restaurantRepository.findById(reservationDto.getRestaurantId()).orElse(null));
            reservation.setUser(userRepository.findById(reservationDto.getUserId()).orElse(null));
            reservation.setGuestName(reservationDto.getGuestName());
            reservation.setNoOfGuests(reservationDto.getNoOfGuests());
            reservation.setTableCustomization(reservationDto.getTableCustomization());
            Reservation savedReservation = reservationRepository.save(reservation);
            emailService.sendEmail(user.getUserEmailId(),
                    "<p> Hey <b>" + user.getUserFullName() + "</b></p>"+"<p> your reservation was successful 🎉 </p>" + "<p> With name <b>" + reservation.getGuestName() + "</b> in restaurant <b>" + restaurant.getRestaurantName() + "</b>.</p>" +"<p> On <b>" + reservation.getDate() +"</b> at <b>" + reservation.getTimeSlot() + "</b> for <b>" + reservation.getNoOfGuests() + "</b> members ,</p>"
                            +"<p>Customization Options : <b>"+reservation.getTableCustomization()+"</b> </p>"+" "+" "+"<p> Thanks for reservation  ,</p>"+ "<p> your status was " + reservation.getStatus() + " wait for the status to confirm. Please note the location 📍" + restaurant.getFullAddress() + ".</p>" + "<p> </p>" + "<p> </p>" + "<p> </p>" + "<p>For any further help you can contact </p>"  + "<p>📞 Ph no: " + restaurant.getRestaurantContactNumber() +"</p>"+ "<p>📧 Mail: " + restaurant.getRestaurantMail() + ".</p>", "🍽 RESERVATION SUCCESSFUL 🍽");
            String newline = System.lineSeparator();
            String QRText = "ID : "+savedReservation.getReservationId()+newline+"Guest Name : "+reservation.getGuestName()+newline+"Number of members : "+reservation.getNoOfGuests()+newline+"Table Customization : "+reservation.getTableCustomization()+newline+"Date & Time Booked : "+reservation.getDate()+" -- "+reservation.getTimeSlot();
            return ResponseEntity.status(HttpStatus.CREATED).body(QRCodeGeneratorService.getQRCodeImage(QRText,300,300));
        }
        else{
            throw new CantCreateException("sorry please select the date after 24 hrs");
        }

    }



    @Override
    public Reservation getReservationById(Integer reservationId) {
        Reservation reservation1 = reservationRepository.findById(reservationId).orElse(null);
        if (reservation1 == null) {
            throw new NotFoundException("No Reservation with id :" + reservationId);
        } else {
            return reservation1;
        }
    }

    @Override
    public List<ReservationDto> getAllReservations(Pageable pageable) {
        List<Reservation> reservations = reservationRepository.findAll(pageable).getContent().stream().collect(Collectors.toList());
        if (reservations.isEmpty()) {
            throw new NotFoundException("No reservations made until now");
        } else {
            return reservationMapper.entityToDtos(reservations);
        }
    }

    @Override
    public List<ReservationDashboardDto> getAllReservationsBydate(LocalDate date) {
        List<Reservation> reservations = reservationRepository.findByDate(date);
        if (reservations.isEmpty()) {
            throw new NotFoundException("No reservations made until now");
        } else {
            List<ReservationDashboardDto> reservationDashboardDtos = new ArrayList<>();
            for (int i = 0; i < reservations.size(); i++) {
                ReservationDashboardDto reservationDashboardDto = new ReservationDashboardDto();
                reservationDashboardDto.setRestaurantId(reservations.get(i).getRestaurant().getRestaurantId());
                reservationDashboardDto.setReservationId(reservations.get(i).getReservationId());
                reservationDashboardDto.setRestaurantName(reservations.get(i).getRestaurant().getRestaurantName());
                reservationDashboardDto.setGuestName(reservations.get(i).getGuestName());
                reservationDashboardDto.setUserId(reservations.get(i).getUser().getUserId());
                reservationDashboardDto.setRestaurantAvgRating(reservations.get(i).getRestaurant().getRatingAverage());
                reservationDashboardDto.setRestaurantReviewCount(reservations.get(i).getRestaurant().getReviewCount());
                reservationDashboardDto.setUserAvgRating(reservations.get(i).getUser().getAvgRating());
                reservationDashboardDto.setUserReviewCount(reservations.get(i).getUser().getReviewCount());
                reservationDashboardDto.setDate(reservations.get(i).getDate());
                reservationDashboardDto.setTimeSlot(reservations.get(i).getTimeSlot());
                reservationDashboardDto.setNoOfGuests(reservations.get(i).getNoOfGuests());
                reservationDashboardDto.setTableCustomization(reservations.get(i).getTableCustomization());
                reservationDashboardDto.setStatus(reservations.get(i).getStatus());
                reservationDashboardDto.setReservedRestaurantTables(restaurantTableMapper.entityToDtos(reservations.get(i).getReservedRestaurantTables()));
                reservationDashboardDtos.add(reservationDashboardDto);
            }
            return reservationDashboardDtos;
        }
    }

    @Override
    public List<ReservationDto> getAllReservationsByDatetime(LocalDate date, LocalTime time) {
        List<Reservation> reservations2 = reservationRepository.findByDateTimeSlot(date, time);
        if (reservations2.isEmpty()) {
            throw new NotFoundException("No reservations made until now");
        } else {
            return reservationMapper.entityToDtos(reservations2);
        }
    }

    @Override
    public List<ReservationDto> getAllReservationsByRestaurant(Integer restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        if (restaurant == null) {
            throw new NotFoundException("No restaurant exist with given id:" + restaurantId);
        } else {
            List<Reservation> reservations2 = reservationRepository.findByRestaurant(restaurant);
            if (reservations2.isEmpty()) {
                throw new NotFoundException("No reservations made until now");
            } else {
                return reservationMapper.entityToDtos(reservations2);
            }
        }
    }

    @Override
    public List<ReservationDashboardDto> getAllReservationsByRestaurantDate(Integer restaurantId, LocalDate date) {
        Restaurant restaurant5 = restaurantRepository.findById(restaurantId).orElse(null);
        if (restaurant5 == null) {
            throw new NotFoundException("No restaurant exits with given id" + restaurantId);
        } else {
            List<Reservation> reservations = reservationRepository.findByRestaurantDate(restaurant5, date);
            if (reservations.isEmpty()) {
                throw new NotFoundException("No reservations made for " + restaurantId + " at date " + date);
            } else {
                List<ReservationDashboardDto> reservationDashboardDtos = new ArrayList<>();
                for (int i = 0; i < reservations.size(); i++) {

                    ReservationDashboardDto reservationDashboardDto = new ReservationDashboardDto();
                    reservationDashboardDto.setRestaurantId(reservations.get(i).getRestaurant().getRestaurantId());
                    reservationDashboardDto.setReservationId(reservations.get(i).getReservationId());
                    reservationDashboardDto.setRestaurantName(reservations.get(i).getRestaurant().getRestaurantName());
                    reservationDashboardDto.setGuestName(reservations.get(i).getGuestName());
                    reservationDashboardDto.setUserId(reservations.get(i).getUser().getUserId());
                    reservationDashboardDto.setRestaurantAvgRating(reservations.get(i).getRestaurant().getRatingAverage());
                    reservationDashboardDto.setRestaurantReviewCount(reservations.get(i).getRestaurant().getReviewCount());
                    reservationDashboardDto.setUserAvgRating(reservations.get(i).getUser().getAvgRating());
                    reservationDashboardDto.setUserReviewCount(reservations.get(i).getUser().getReviewCount());
                    reservationDashboardDto.setDate(reservations.get(i).getDate());
                    reservationDashboardDto.setTimeSlot(reservations.get(i).getTimeSlot());
                    reservationDashboardDto.setNoOfGuests(reservations.get(i).getNoOfGuests());
                    reservationDashboardDto.setTableCustomization(reservations.get(i).getTableCustomization());
                    reservationDashboardDto.setStatus(reservations.get(i).getStatus());
                    reservationDashboardDto.setReservedRestaurantTables(restaurantTableMapper.entityToDtos(reservations.get(i).getReservedRestaurantTables()));
                    reservationDashboardDtos.add(reservationDashboardDto);
                }
                return reservationDashboardDtos;
            }
        }
    }

    @Override
    public List<ReservationDashboardDto> getAllReservationsByRestaurantDateTime(Integer restaurantId, LocalDate reservedate, LocalTime time1, LocalTime time2) {
        Restaurant restaurant5 = restaurantRepository.findById(restaurantId).orElse(null);
        if (restaurant5 == null) {
            throw new NotFoundException("No restaurant exits with given id" + restaurantId);
        } else {
            List<Reservation> reservations = reservationRepository.findByRestaurantDate(restaurant5, reservedate);
            if (reservations.isEmpty()) {
                throw new NotFoundException("No reservations made for " + restaurantId + " at date " + reservedate);
            } else {
                List<ReservationDashboardDto> reservationDashboardDtos = new ArrayList<>();
                for (int i = 0; i < reservations.size(); i++) {
                    if(reservations.get(i).getTimeSlot().isAfter(time1) && reservations.get(i).getTimeSlot().isBefore(time2) ) {
                        ReservationDashboardDto reservationDashboardDto = new ReservationDashboardDto();
                        reservationDashboardDto.setRestaurantId(reservations.get(i).getRestaurant().getRestaurantId());
                        reservationDashboardDto.setReservationId(reservations.get(i).getReservationId());
                        reservationDashboardDto.setRestaurantName(reservations.get(i).getRestaurant().getRestaurantName());
                        reservationDashboardDto.setGuestName(reservations.get(i).getGuestName());
                        reservationDashboardDto.setUserId(reservations.get(i).getUser().getUserId());
                        reservationDashboardDto.setRestaurantAvgRating(reservations.get(i).getRestaurant().getRatingAverage());
                        reservationDashboardDto.setRestaurantReviewCount(reservations.get(i).getRestaurant().getReviewCount());
                        reservationDashboardDto.setUserAvgRating(reservations.get(i).getUser().getAvgRating());
                        reservationDashboardDto.setUserReviewCount(reservations.get(i).getUser().getReviewCount());
                        reservationDashboardDto.setDate(reservations.get(i).getDate());
                        reservationDashboardDto.setTimeSlot(reservations.get(i).getTimeSlot());
                        reservationDashboardDto.setNoOfGuests(reservations.get(i).getNoOfGuests());
                        reservationDashboardDto.setTableCustomization(reservations.get(i).getTableCustomization());
                        reservationDashboardDto.setStatus(reservations.get(i).getStatus());
                        reservationDashboardDto.setReservedRestaurantTables(restaurantTableMapper.entityToDtos(reservations.get(i).getReservedRestaurantTables()));
                        reservationDashboardDtos.add(reservationDashboardDto);
                    }
                }
                return reservationDashboardDtos;
            }
        }

    }

    @Override
    public List<ReservationDto> getAllReservationsByUser(Integer userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new NotFoundException("No user exist with given id:" + userId);
        } else {
            List<Reservation> reservations2 = reservationRepository.findByUser(user);
            if (reservations2.isEmpty()) {
                throw new NotFoundException("You didnt made any reservations until now");
            } else {
                return reservationMapper.entityToDtos(reservations2);
            }
        }
    }


    @Override
    public String deleteReservation(Integer reservationId) {
        Reservation reservation1 = reservationRepository.findById(reservationId).orElse(null);
        if (reservation1 == null) {
            throw new NotFoundException("No reservation made with given id: " + reservationId);
        } else {
            reservationRepository.deleteById(reservationId);
            return "You have successfully cancled your reservation";
        }
    }

    @Override
    public Reservation updateReservation(Integer userId, Integer restaurantId, Reservation reservation) {
        reservation.setUser(userRepository.findById(userId).orElse(null));
        reservation.setRestaurant(restaurantRepository.findById(restaurantId).orElse(null));
        return reservationRepository.save(reservation);
    }

    public ResponseEntity<String> editReservation(UpdateReservationDto updateReservationDto) throws MessagingException {

        Reservation pastDetails = reservationRepository.findById(updateReservationDto.getReservationId()).orElse(null);
        if (pastDetails.getStatus().equals("approved"))
        {
            return ResponseEntity.ok().body("This reservation was already approved and now you cant update any details contact the restaurant manager");
        }
       else if (pastDetails.getStatus().equals("pending")) {
            pastDetails.setStatus("pending");

            if(updateReservationDto.getDate() != null && updateReservationDto.getDate().isAfter(LocalDate.now()))
            {
                pastDetails.setDate(updateReservationDto.getDate());
            }
            if(updateReservationDto.getTimeSlot() != null)
            {
                pastDetails.setTimeSlot(updateReservationDto.getTimeSlot());
            }
            if(updateReservationDto.getGuestName() != null)
            {
                pastDetails.setGuestName(updateReservationDto.getGuestName());
            }
            if(updateReservationDto.getNoOfGuests() != null)
            {
                pastDetails.setNoOfGuests(updateReservationDto.getNoOfGuests());
            }
            if(updateReservationDto.getTableCustomization() != null)
            {
                pastDetails.setTableCustomization(updateReservationDto.getTableCustomization());
            }
            reservationRepository.save(pastDetails);

           emailService.sendEmail(pastDetails.getUser().getUserEmailId(), " Dear " + pastDetails.getUser().getUserFullName() + "," + " Your reservation details were successfully updated Now ." + " Here are the updated Details" + " " + " " + "GUEST NAME : " + pastDetails.getGuestName() +  "Number of guests : " + pastDetails.getNoOfGuests() +  "Date : " + pastDetails.getDate() +  "TimeSlot : " + pastDetails.getTimeSlot() +  "Status : " + pastDetails.getStatus(), " RESERVATION DETAILS UPDATED 🔁");
           return ResponseEntity.ok().body("Reservation details were updated check your email : " + pastDetails.getUser().getUserEmailId());
         }
       else if (pastDetails.getStatus().equals("declined")) {
            return ResponseEntity.ok().body("This reservation was declined so cant update the details");
       } else {
            return ResponseEntity.ok().body("please check the status");
        }
    }

    @Override
    public List<ReservationDashboardDto> currentRestaurantReservations(Integer restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        List<Reservation> reservations = reservationRepository.findByRestaurant(restaurant);
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        List<ReservationDashboardDto> reservationDashboardDtos = new ArrayList<>();
        for(int i=0; i<reservations.size();i++)
        {
            LocalDate reservationDate = reservations.get(i).getDate();
            LocalTime reservationTimeSlot = reservations.get(i).getTimeSlot();
            if( reservationDate.isAfter(date) || (reservationDate.isEqual(date) && (time.isBefore(reservationTimeSlot) || time.equals(reservationTimeSlot))))
            {
                ReservationDashboardDto reservationDashboardDto = new ReservationDashboardDto();
                reservationDashboardDto.setRestaurantId(reservations.get(i).getRestaurant().getRestaurantId());
                reservationDashboardDto.setReservationId(reservations.get(i).getReservationId());
                reservationDashboardDto.setRestaurantName(reservations.get(i).getRestaurant().getRestaurantName());
                reservationDashboardDto.setGuestName(reservations.get(i).getGuestName());
                reservationDashboardDto.setUserId(reservations.get(i).getUser().getUserId());
                reservationDashboardDto.setRestaurantAvgRating(reservations.get(i).getRestaurant().getRatingAverage());
                reservationDashboardDto.setRestaurantReviewCount(reservations.get(i).getRestaurant().getReviewCount());
                reservationDashboardDto.setUserAvgRating(reservations.get(i).getUser().getAvgRating());
                reservationDashboardDto.setUserReviewCount(reservations.get(i).getUser().getReviewCount());
                reservationDashboardDto.setDate(reservations.get(i).getDate());
                reservationDashboardDto.setTimeSlot(reservations.get(i).getTimeSlot());
                reservationDashboardDto.setNoOfGuests(reservations.get(i).getNoOfGuests());
                reservationDashboardDto.setTableCustomization(reservations.get(i).getTableCustomization());
                reservationDashboardDto.setStatus(reservations.get(i).getStatus());
                reservationDashboardDto.setReservedRestaurantTables(restaurantTableMapper.entityToDtos(reservations.get(i).getReservedRestaurantTables()));
                reservationDashboardDtos.add(reservationDashboardDto);
            }
        }
        return reservationDashboardDtos;
    }

    @Override
    public List<ReservationDashboardDto> pastRestaurantReservations(Integer restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        List<Reservation> reservations = reservationRepository.findByRestaurant(restaurant);
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        List<ReservationDashboardDto> reservationDashboardDtos = new ArrayList<>();
        for(int i=0; i<reservations.size();i++)
        {
            ReservationDashboardDto reservationDashboardDto = new ReservationDashboardDto();
            if(reservations.get(i).getDate().isBefore(date))
            {
                reservationDashboardDto.setReservationId(reservations.get(i).getReservationId());
                reservationDashboardDto.setRestaurantId(reservations.get(i).getRestaurant().getRestaurantId());
                reservationDashboardDto.setRestaurantName(reservations.get(i).getRestaurant().getRestaurantName());
                reservationDashboardDto.setGuestName(reservations.get(i).getGuestName());
                reservationDashboardDto.setUserId(reservations.get(i).getUser().getUserId());
                reservationDashboardDto.setRestaurantAvgRating(reservations.get(i).getRestaurant().getRatingAverage());
                reservationDashboardDto.setRestaurantReviewCount(reservations.get(i).getRestaurant().getReviewCount());
                reservationDashboardDto.setUserAvgRating(reservations.get(i).getUser().getAvgRating());
                reservationDashboardDto.setUserReviewCount(reservations.get(i).getUser().getReviewCount());
                reservationDashboardDto.setDate(reservations.get(i).getDate());
                reservationDashboardDto.setTimeSlot(reservations.get(i).getTimeSlot());
                reservationDashboardDto.setNoOfGuests(reservations.get(i).getNoOfGuests());
                reservationDashboardDto.setTableCustomization(reservations.get(i).getTableCustomization());
                reservationDashboardDto.setReservedRestaurantTables(restaurantTableMapper.entityToDtos(reservations.get(i).getReservedRestaurantTables()));
                if(reservations.get(i).getStatus().equals("pending"))
                {
                    reservations.get(i).setStatus("No Action Taken");
                    reservationRepository.save(reservations.get(i));
                    reservationDashboardDto.setStatus(reservations.get(i).getStatus());
                }
                else if(reservations.get(i).getStatus().equals("approved"))
                {
                    reservations.get(i).setStatus("Completed");
                    reservationRepository.save(reservations.get(i));
                    reservationDashboardDto.setStatus(reservations.get(i).getStatus());
                }
                else{reservationDashboardDto.setStatus(reservations.get(i).getStatus());}
                reservationDashboardDtos.add(reservationDashboardDto);
            }
            else if (date.isEqual(reservations.get(i).getDate()) && time.isAfter(reservations.get(i).getTimeSlot()))
            {
                reservationDashboardDto.setReservationId(reservations.get(i).getReservationId());
                reservationDashboardDto.setRestaurantId(reservations.get(i).getRestaurant().getRestaurantId());
                reservationDashboardDto.setRestaurantName(reservations.get(i).getRestaurant().getRestaurantName());
                reservationDashboardDto.setGuestName(reservations.get(i).getGuestName());
                reservationDashboardDto.setUserId(reservations.get(i).getUser().getUserId());
                reservationDashboardDto.setRestaurantAvgRating(reservations.get(i).getRestaurant().getRatingAverage());
                reservationDashboardDto.setRestaurantReviewCount(reservations.get(i).getRestaurant().getReviewCount());
                reservationDashboardDto.setUserAvgRating(reservations.get(i).getUser().getAvgRating());
                reservationDashboardDto.setUserReviewCount(reservations.get(i).getUser().getReviewCount());
                reservationDashboardDto.setDate(reservations.get(i).getDate());
                reservationDashboardDto.setTimeSlot(reservations.get(i).getTimeSlot());
                reservationDashboardDto.setNoOfGuests(reservations.get(i).getNoOfGuests());
                reservationDashboardDto.setTableCustomization(reservations.get(i).getTableCustomization());
                reservationDashboardDto.setReservedRestaurantTables(restaurantTableMapper.entityToDtos(reservations.get(i).getReservedRestaurantTables()));
                if(reservations.get(i).getStatus().equals("pending"))
                {
                    reservations.get(i).setStatus("No Action Taken");
                    reservationRepository.save(reservations.get(i));
                    reservationDashboardDto.setStatus(reservations.get(i).getStatus());
                }
                else if(reservations.get(i).getStatus().equals("approved"))
                {
                    reservations.get(i).setStatus("Completed");
                    reservationRepository.save(reservations.get(i));
                    reservationDashboardDto.setStatus(reservations.get(i).getStatus());
                }
                else{reservationDashboardDto.setStatus(reservations.get(i).getStatus());}
                reservationDashboardDtos.add(reservationDashboardDto);
            }
        }
        return reservationDashboardDtos;
    }

    @Override
    public List<ReservationDashboardDto> currentUserReservations(Integer userId) {
        User user = userRepository.findById(userId).orElse(null);
        List<Reservation> reservations = reservationRepository.findByUser(user);
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        List<ReservationDashboardDto> reservationDashboardDtos = new ArrayList<>();
        for(int i=0; i<reservations.size();i++) {
            LocalDate reservationDate = reservations.get(i).getDate();
            LocalTime reservationTimeSlot = reservations.get(i).getTimeSlot();
            if (reservationDate.isAfter(date) || (reservationDate.isEqual(date) && (time.isBefore(reservationTimeSlot) || time.equals(reservationTimeSlot)))) {
                ReservationDashboardDto reservationDashboardDto = new ReservationDashboardDto();
                reservationDashboardDto.setReservationId(reservations.get(i).getReservationId());
                reservationDashboardDto.setRestaurantId(reservations.get(i).getRestaurant().getRestaurantId());
                reservationDashboardDto.setRestaurantName(reservations.get(i).getRestaurant().getRestaurantName());
                reservationDashboardDto.setGuestName(reservations.get(i).getGuestName());
                reservationDashboardDto.setUserId(reservations.get(i).getUser().getUserId());
                reservationDashboardDto.setRestaurantAvgRating(reservations.get(i).getRestaurant().getRatingAverage());
                reservationDashboardDto.setRestaurantReviewCount(reservations.get(i).getRestaurant().getReviewCount());
                reservationDashboardDto.setUserAvgRating(reservations.get(i).getUser().getAvgRating());
                reservationDashboardDto.setUserReviewCount(reservations.get(i).getUser().getReviewCount());
                reservationDashboardDto.setDate(reservations.get(i).getDate());
                reservationDashboardDto.setTimeSlot(reservations.get(i).getTimeSlot());
                reservationDashboardDto.setNoOfGuests(reservations.get(i).getNoOfGuests());
                reservationDashboardDto.setTableCustomization(reservations.get(i).getTableCustomization());
                reservationDashboardDto.setStatus(reservations.get(i).getStatus());
                reservationDashboardDto.setReservedRestaurantTables(restaurantTableMapper.entityToDtos(reservations.get(i).getReservedRestaurantTables()));
                reservationDashboardDtos.add(reservationDashboardDto);
            }
        }
        return reservationDashboardDtos;
    }

    @Override
    public List<ReservationDashboardDto> pastUserReservations(Integer userId) {
        User user = userRepository.findById(userId).orElse(null);
        List<Reservation> reservations = reservationRepository.findByUser(user);
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        List<ReservationDashboardDto> reservationDashboardDtos = new ArrayList<>();
        if((reservations.size() != 0)) {
            for (int i = 0; i < reservations.size(); i++) {
                ReservationDashboardDto reservationDashboardDto = new ReservationDashboardDto();
                if (date.isAfter(reservations.get(i).getDate()) ) {
                    reservationDashboardDto.setRestaurantId(reservations.get(i).getRestaurant().getRestaurantId());
                    reservationDashboardDto.setReservationId(reservations.get(i).getReservationId());
                    reservationDashboardDto.setRestaurantName(reservations.get(i).getRestaurant().getRestaurantName());
                    reservationDashboardDto.setGuestName(reservations.get(i).getGuestName());
                    reservationDashboardDto.setUserId(reservations.get(i).getUser().getUserId());
                    reservationDashboardDto.setRestaurantAvgRating(reservations.get(i).getRestaurant().getRatingAverage());
                    reservationDashboardDto.setRestaurantReviewCount(reservations.get(i).getRestaurant().getReviewCount());
                    reservationDashboardDto.setUserAvgRating(reservations.get(i).getUser().getAvgRating());
                    reservationDashboardDto.setUserReviewCount(reservations.get(i).getUser().getReviewCount());
                    reservationDashboardDto.setDate(reservations.get(i).getDate());
                    reservationDashboardDto.setTimeSlot(reservations.get(i).getTimeSlot());
                    reservationDashboardDto.setNoOfGuests(reservations.get(i).getNoOfGuests());
                    reservationDashboardDto.setTableCustomization(reservations.get(i).getTableCustomization());
                    reservationDashboardDto.setReservedRestaurantTables(restaurantTableMapper.entityToDtos(reservations.get(i).getReservedRestaurantTables()));
                    if(reservations.get(i).getStatus().equals("pending"))
                    {
                        reservations.get(i).setStatus("No Action Taken");
                        reservationRepository.save(reservations.get(i));
                        reservationDashboardDto.setStatus(reservations.get(i).getStatus());
                    }
                    else if(reservations.get(i).getStatus().equals("approved"))
                    {
                        reservations.get(i).setStatus("Completed");
                        reservationRepository.save(reservations.get(i));
                        reservationDashboardDto.setStatus(reservations.get(i).getStatus());
                    } else {
                        reservationDashboardDto.setStatus(reservations.get(i).getStatus());
                    }
                    reservationDashboardDtos.add(reservationDashboardDto);
                }
                else if(date.isEqual(reservations.get(i).getDate()) && time.isAfter(reservations.get(i).getTimeSlot())){

                    reservationDashboardDto.setReservationId(reservations.get(i).getReservationId());
                    reservationDashboardDto.setRestaurantId(reservations.get(i).getRestaurant().getRestaurantId());
                    reservationDashboardDto.setRestaurantName(reservations.get(i).getRestaurant().getRestaurantName());
                    reservationDashboardDto.setGuestName(reservations.get(i).getGuestName());
                    reservationDashboardDto.setUserId(reservations.get(i).getUser().getUserId());
                    reservationDashboardDto.setRestaurantAvgRating(reservations.get(i).getRestaurant().getRatingAverage());
                    reservationDashboardDto.setRestaurantReviewCount(reservations.get(i).getRestaurant().getReviewCount());
                    reservationDashboardDto.setUserAvgRating(reservations.get(i).getUser().getAvgRating());
                    reservationDashboardDto.setUserReviewCount(reservations.get(i).getUser().getReviewCount());
                    reservationDashboardDto.setDate(reservations.get(i).getDate());
                    reservationDashboardDto.setTimeSlot(reservations.get(i).getTimeSlot());
                    reservationDashboardDto.setNoOfGuests(reservations.get(i).getNoOfGuests());
                    reservationDashboardDto.setTableCustomization(reservations.get(i).getTableCustomization());
                    reservationDashboardDto.setReservedRestaurantTables(restaurantTableMapper.entityToDtos(reservations.get(i).getReservedRestaurantTables()));
                    if (reservations.get(i).getStatus().equals("pending")) {
                        reservations.get(i).setStatus("No Action Taken");
                        reservationRepository.save(reservations.get(i));
                        reservationDashboardDto.setStatus(reservations.get(i).getStatus());
                    } else if (reservations.get(i).getStatus().equals("approved")) {
                        reservations.get(i).setStatus("Completed");
                        reservationRepository.save(reservations.get(i));
                        reservationDashboardDto.setStatus(reservations.get(i).getStatus());
                    } else {
                        reservationDashboardDto.setStatus(reservations.get(i).getStatus());
                    }
                    reservationDashboardDtos.add(reservationDashboardDto);
                }
            }
        } else {
            throw new NotFoundException("You didn't have any reservations");
        }
        return reservationDashboardDtos;


    }


}
