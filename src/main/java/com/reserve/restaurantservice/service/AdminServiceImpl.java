package com.reserve.restaurantservice.service;

import com.reserve.restaurantservice.dto.AdminResponseDTO;
import com.reserve.restaurantservice.dto.LoginJson;
import com.reserve.restaurantservice.entities.Admin;
import com.reserve.restaurantservice.entities.Role;
import com.reserve.restaurantservice.repository.AdminRepository;
import com.reserve.restaurantservice.repository.RestaurantTypeRepository;
import com.reserve.restaurantservice.repository.RoleRepository;
import com.reserve.restaurantservice.security.AdminTokenGenerator;
import com.reserve.restaurantservice.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTGenerator jwtGenerator;

    @Autowired
    AdminTokenGenerator adminTokenGenerator;

    @Autowired
    RestaurantTypeRepository restaurantTypeRepository;

    public Admin adminRegister(Admin admin) {
        admin.setAdminPassword(this.bCryptPasswordEncoder.encode(admin.getAdminPassword()));
        Role role = roleRepository.findByName("Admin").get();
        admin.setRoles(Collections.singletonList(role));
        return adminRepository.save(admin);

    }

    @Override
    public ResponseEntity<AdminResponseDTO> adminLogin(LoginJson loginJson) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginJson.getUserName(),
                        loginJson.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generateToken(authentication);

        Optional<Admin> adminOptional = adminRepository.findByAdminEmailId(loginJson.getUserName());


        return ResponseEntity.ok().header(
                        HttpHeaders.SET_COOKIE, token)
                .body(new AdminResponseDTO(adminOptional.get().getAdminId(), adminOptional.get().getAdminUserName(), adminOptional.get().getAdminEmailId(), token));
    }

}



