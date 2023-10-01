package com.reserve.restaurantservice.repository;

import com.reserve.restaurantservice.entities.NewsletterSubscribe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NewsLetterRepository extends JpaRepository<NewsletterSubscribe,Integer> {

    Optional<NewsletterSubscribe> findBySubscribedEmail(String subscribedEmail);
}
