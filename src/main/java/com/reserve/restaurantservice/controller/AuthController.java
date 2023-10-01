package com.reserve.restaurantservice.controller;


import com.reserve.restaurantservice.dto.*;
import com.reserve.restaurantservice.entities.*;
import com.reserve.restaurantservice.mapper.RestaurantTableMapper;
import com.reserve.restaurantservice.mapper.UserMapper;
import com.reserve.restaurantservice.repository.RestaurantManagerRepository;
import com.reserve.restaurantservice.repository.RestaurantRepository;
import com.reserve.restaurantservice.repository.RoleRepository;
import com.reserve.restaurantservice.repository.UserRepository;
import com.reserve.restaurantservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserRatingService userRatingService;
    @Autowired
    UserService userService;
    @Autowired
    RestaurantManagerService restaurantManagerService;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    RestaurantTableService restaurantTableService;

    @Autowired
    RestaurantTableMapper restaurantTableMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    RestaurantManagerRepository managerRepository;

    @Autowired
    AdminService adminService;


    @PostMapping("/user/register")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @PostMapping("/user/login")
    public ResponseEntity<UserInfoResponse> login(@RequestBody LoginJson loginJson) {
        return userService.login(loginJson);
    }

    @PostMapping("/restaurant/register")
    public RestaurantManager createRestaurantManager(@RequestBody RestaurantManagerDto restaurantManagerDto) {
        return restaurantManagerService.createRestaurantManager(restaurantManagerDto);
    }

    @PostMapping("/restaurant/login")
    public ResponseEntity<AuthResponseDTO> restaurantLogin(@RequestBody LoginJson loginJson) {
        return restaurantManagerService.restaurantLogin(loginJson);
    }

    @PostMapping("/admin/login")
    public ResponseEntity<AdminResponseDTO> adminLogin(@RequestBody LoginJson loginJson) {
        return adminService.adminLogin(loginJson);
    }

    @PostMapping("/admin/reg")
    public Admin adminRegister(@RequestBody Admin admin) {
        return adminService.adminRegister(admin);
    }


    @PostMapping("restaurant/create")
    public ResponseEntity<String> createRestaurant(@RequestBody CreateRestaurantDto createRestaurantDto) {
        return restaurantService.addRestaurant(createRestaurantDto);
    }

    @PostMapping("/{restaurantId}/restaurantTables")
    public List<RestaurantTableDto> createRestaurantTables(@PathVariable Integer restaurantId, @Valid @RequestBody List<RestaurantTable> restaurantTables) {
        Restaurant restaurant6 = restaurantService.getRestaurantById(restaurantId);
        for (int i = 0; i < restaurantTables.size(); i++) {
            restaurantTables.get(i).setTheRestaurant(restaurant6);
            restaurantTableService.createRestaurantTable(restaurantTables.get(i));
        }
        return restaurantTableMapper.entityToDtos(restaurantTables);
    }

    @PostMapping("/forgot")
    public ResponseEntity<String> forgotPassword(@RequestParam("email") String userEmail) throws MessagingException {
        return userService.forgotPassword(userEmail);
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPassword resetPassword) throws MessagingException {
        return userService.resetPassword(resetPassword);
    }


    @PutMapping("/update")
    public User updateUser(@RequestBody EditUserDTO editUserDTO) {
        return userService.updateUser(editUserDTO);
    }

    @PutMapping("/update/password")
    public User updateUser(@RequestBody UPasswordDTO uPasswordDTO) {
        return userService.upassword(uPasswordDTO);
    }


}
