package com.reserve.restaurantservice.repository;

import com.reserve.restaurantservice.entities.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Optional<Admin> findByAdminUserName(String adminUserName);

    Optional<Admin> findByAdminEmailId(String adminEmailId);

    Page<Admin> findAll(Pageable pageable);
}
