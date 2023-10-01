package com.reserve.restaurantservice.security;

import com.reserve.restaurantservice.entities.Admin;
import com.reserve.restaurantservice.entities.RestaurantManager;
import com.reserve.restaurantservice.entities.Role;
import com.reserve.restaurantservice.entities.User;
import com.reserve.restaurantservice.repository.AdminRepository;
import com.reserve.restaurantservice.repository.RestaurantManagerRepository;
import com.reserve.restaurantservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    String ROLE_PREFIX = "ROLE_";


    private UserRepository userRepository;


    private RestaurantManagerRepository restaurantManagerRepository;

    private AdminRepository adminRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, RestaurantManagerRepository restaurantManagerRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.restaurantManagerRepository = restaurantManagerRepository;
        this.adminRepository = adminRepository;
    }


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUserEmailId(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
//        return new org.springframework.security.core.userdetails.User(user.getUserEmailId(),user.getUserPassword(),mapRolesToAuthorities(user.getRoles()));

        Optional<User> user = userRepository.findByUserEmailId(username);

        Optional<RestaurantManager> restaurantManager = restaurantManagerRepository.findByRestaurantManagerEmailId(username);
        Optional<Admin> admin = adminRepository.findByAdminEmailId(username);
        if(!user.isEmpty()){
                return new org.springframework.security.core.userdetails.User(user.get().getUserEmailId(),user.get().getUserPassword(),mapRolesToAuthorities(user.get().getRoles()));
            }

            else {
                if (!restaurantManager.isEmpty()){
                    return new org.springframework.security.core.userdetails.User(restaurantManager.get().getRestaurantManagerEmailId(),restaurantManager.get().getRestaurantManagerPassword(),mapRolesToAuthorities(restaurantManager.get().getRoles()));
                }
                if (!admin.isEmpty()){
                    return new org.springframework.security.core.userdetails.User(admin.get().getAdminEmailId(),admin.get().getAdminPassword(),mapRolesToAuthorities(admin.get().getRoles()));
                }
            }
        throw new UsernameNotFoundException("User "+ username + "not found");
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(ROLE_PREFIX+role.getName())).collect(Collectors.toList());
    }


}
