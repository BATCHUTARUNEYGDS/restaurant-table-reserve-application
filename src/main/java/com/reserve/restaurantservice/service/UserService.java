package com.reserve.restaurantservice.service;
import com.reserve.restaurantservice.dto.*;
import com.reserve.restaurantservice.entities.ResetPassword;
import com.reserve.restaurantservice.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import java.util.List;

public interface UserService{

    public ResponseEntity<String> createUser(UserDto userDto);
    public void deleteUser( Integer id);
    public User retrieveUser(Integer id);
    public ResponseEntity<List<UserDto>> retrieveAllUsers(Pageable pageable);
    public User updateUser(EditUserDTO editUserDTO);
    public User findByUserId(Integer userId);
    public ResponseEntity<UserInfoResponse> login(LoginJson loginJson);
    public ResponseEntity<String> forgotPassword(String userEmail) throws MessagingException;
    public ResponseEntity<String> resetPassword(ResetPassword resetPassword) throws MessagingException;
    public User upassword(UPasswordDTO uPasswordDTO);

}
