package com.reserve.restaurantservice.dao;

import com.reserve.restaurantservice.entities.Restaurant;
import com.reserve.restaurantservice.entities.RestaurantFilter;
import com.reserve.restaurantservice.entities.RestaurantType;
import com.reserve.restaurantservice.repository.RestaurantTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class RestaurantDaoImpl implements RestaurantDao {

    @Autowired
    RestaurantTypeRepository restaurantTypeRepository;
    @PersistenceContext
    EntityManager em;

    @Override
    public List<Restaurant> findRestaurantByFilter(RestaurantFilter restaurantFilter) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Restaurant> cq = cb.createQuery(Restaurant.class);
        Root<Restaurant> restaurantRoot = cq.from(Restaurant.class);


        Predicate restaurantLocationPredicate = cb.equal(restaurantRoot.get("restaurantLocation").get("city"), restaurantFilter.getLocation());
        Predicate restaurantTagPredicate = cb.equal(restaurantRoot.get("tag"), restaurantFilter.getTag());
        Predicate restaurantTypePredicate = cb.equal(restaurantRoot.get("restaurantCusineType").get("cuisine"), restaurantFilter.getCuisineType());
        //cq.where(restaurantRoot.get("restaurantCusineType").get("cuisine").in(restaurantFilter.getCuisineType()));


        if (restaurantFilter.getSortBy().equals("approxForTwo")) {
            if (restaurantFilter.getSortType().equals("asc")) {
                cq.where(cb.and(restaurantLocationPredicate, restaurantTagPredicate, restaurantTypePredicate)).orderBy(cb.asc(restaurantRoot.get("approxForTwo")));
            } else if (restaurantFilter.getSortType().equals("desc")) {
                cq.where(cb.and(restaurantLocationPredicate, restaurantTagPredicate, restaurantTypePredicate)).orderBy(cb.desc(restaurantRoot.get("approxForTwo")));
            }
        } else if (restaurantFilter.getSortBy().equals("ratingAverage")) {
            if (restaurantFilter.getSortType().equals("asc")) {
                cq.where(cb.and(restaurantLocationPredicate, restaurantTagPredicate, restaurantTypePredicate)).orderBy(cb.asc(restaurantRoot.get("ratingAverage")));
            } else if (restaurantFilter.getSortType().equals("desc")) {
                cq.where(cb.and(restaurantLocationPredicate, restaurantTagPredicate, restaurantTypePredicate)).orderBy(cb.desc(restaurantRoot.get("ratingAverage")));
            }
        }


        TypedQuery<Restaurant> query = em.createQuery(cq);
        return query.getResultList();

    }
}
