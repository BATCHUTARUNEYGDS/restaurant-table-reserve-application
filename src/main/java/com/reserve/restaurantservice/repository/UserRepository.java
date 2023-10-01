package com.reserve.restaurantservice.repository;
import com.reserve.restaurantservice.dto.UserDto;
import com.reserve.restaurantservice.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Lazy;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUserEmailId(String userEmailId);
    Optional<User> findByUserName(String userName);
    Optional<User> findByUserPhoneNumber(String userPhoneNumber);
    Page<User> findAll(Pageable pageable);

    Boolean existsByUserEmailId(String userEmailId);

    Optional<User> findByResetToken(Integer resetToken);

    //Lazy<Object> findById(User );
}
