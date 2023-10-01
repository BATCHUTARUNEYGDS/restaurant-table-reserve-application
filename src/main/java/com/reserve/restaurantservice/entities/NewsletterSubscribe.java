package com.reserve.restaurantservice.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NewsletterSubscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subscribeId;

    private String subscribedEmail;

    public Integer getSubscribeId() {
        return subscribeId;
    }

    public void setSubscribeId(Integer subscribeId) {
        this.subscribeId = subscribeId;
    }

    public String getSubscribedEmail() {
        return subscribedEmail;
    }

    public void setSubscribedEmail(String subscribedEmail) {
        this.subscribedEmail = subscribedEmail;
    }
}
