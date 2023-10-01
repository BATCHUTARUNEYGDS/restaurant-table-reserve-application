package com.reserve.restaurantservice.service;

import com.reserve.restaurantservice.entities.NewsletterSubscribe;
import com.reserve.restaurantservice.repository.NewsLetterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NewsLetterServiceImpl implements NewsLetterService {

    @Autowired
    NewsLetterRepository newsLetterRepository;

    @Override
    public void addEmail(NewsletterSubscribe N)  {
        newsLetterRepository.save(N);
    }

    @Override
    public boolean checkExistence(String email) {
        NewsletterSubscribe newsletter =newsLetterRepository.findBySubscribedEmail(email).orElse(null);
       if(newsletter!=null){
           return true;
       }
       else{ return false;}

    }


}
