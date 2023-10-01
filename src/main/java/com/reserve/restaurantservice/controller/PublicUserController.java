package com.reserve.restaurantservice.controller;

import com.reserve.restaurantservice.entities.NewsletterSubscribe;
import com.reserve.restaurantservice.entities.UserRating;
import com.reserve.restaurantservice.exception.AlreadyExistException;
import com.reserve.restaurantservice.repository.NewsLetterRepository;
import com.reserve.restaurantservice.repository.UserLocationRepository;
import com.reserve.restaurantservice.repository.UserRepository;
import com.reserve.restaurantservice.service.EmailService;
import com.reserve.restaurantservice.service.NewsLetterService;
import com.reserve.restaurantservice.service.UserRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@CrossOrigin("*")
@RequestMapping("/public/user")
public class PublicUserController {

    @Autowired
    UserLocationRepository userLocationRepository;

    @Autowired
    UserRatingService userRatingService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    NewsLetterService newsLetterService;
    @Autowired
    NewsLetterRepository newsLetterRepository;


    //@RequestMapping(value = "/name", method = RequestMethod.GET)

    @GetMapping("/location/cities")
    public Set<String> getAllCities() {
        List<String> cityList = userLocationRepository.findAllCities();
        Set<String> citySet = cityList.stream().collect(Collectors.toSet());
        return citySet;
    }

    @GetMapping("/location/{city}/area")
    public Set<String> getAllAreasByCity(@PathVariable String city) {
        List<String> areaList = userLocationRepository.findAllAreasByCity(city);
        Set<String> areaSet = areaList.stream().collect(Collectors.toSet());
        return areaSet;
    }

    @GetMapping("/location/{city}/{area}/pincode")
    public Set<Integer> getAllPincodesByCityArea(@PathVariable String city, @PathVariable String area) {
        List<Integer> pincodeList = userLocationRepository.findAllPincodesByCityArea(city, area);
        Set<Integer> pincodeSet = pincodeList.stream().collect(Collectors.toSet());
        return pincodeSet;
    }

    @GetMapping("/rating")
    public List<UserRating> retrieveAllUsersRating(Pageable pageable) {
        return userRatingService.retrieveAllUsersRating(pageable);
    }

    @PostMapping("/subscribeNewsletter")
    public ResponseEntity<String> emailSubscribe(@RequestBody NewsletterSubscribe N) throws MessagingException {
        boolean result = N.getSubscribedEmail().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
        if (result) {
            if (!newsLetterService.checkExistence(N.getSubscribedEmail())) {
                newsLetterService.addEmail(N);
                emailService.sendEmail(N.getSubscribedEmail(),
                        "<b>Hey your Subscription got confirmed.</b>" + "<p>Thanks for Signing up to our Newsletter.</p>"
                                + "<p>By signing upto our <b> Reserve Newsletter </b>,each month you will be the first to hear about out latest " +
                                "recepies straight to you inbox.</p>" + "<b><p>Thank You</p></b>",
                        "News Letter Subscription");
                return ResponseEntity.ok().body("Subscription is confirmed");
            } else {
                throw new AlreadyExistException("Given email id already exist");
            }
        } else {
            return ResponseEntity.ok().body("Given email-id is not valid");
        }

    }


}
