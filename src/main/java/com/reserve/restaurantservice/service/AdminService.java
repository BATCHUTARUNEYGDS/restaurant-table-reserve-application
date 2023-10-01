package com.reserve.restaurantservice.service;

import com.reserve.restaurantservice.dto.AdminResponseDTO;
import com.reserve.restaurantservice.dto.LoginJson;
import com.reserve.restaurantservice.entities.Admin;
import org.springframework.http.ResponseEntity;

public interface AdminService {

    ResponseEntity<AdminResponseDTO> adminLogin(LoginJson loginJson);

    Admin adminRegister(Admin admin);


}
