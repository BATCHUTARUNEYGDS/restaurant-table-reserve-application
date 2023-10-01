package com.reserve.restaurantservice.service;

import com.reserve.restaurantservice.entities.NewsletterSubscribe;

import java.util.List;
import java.util.Optional;

public interface NewsLetterService {

    void addEmail(NewsletterSubscribe N);

    boolean checkExistence(String email );
}
