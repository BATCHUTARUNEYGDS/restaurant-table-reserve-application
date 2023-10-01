package com.reserve.restaurantservice.service;

import com.reserve.restaurantservice.dto.*;
import com.reserve.restaurantservice.entities.ResetPassword;
import com.reserve.restaurantservice.entities.Role;
import com.reserve.restaurantservice.entities.User;
import com.reserve.restaurantservice.entities.UserLocation;
import com.reserve.restaurantservice.exception.AlreadyExistException;
import com.reserve.restaurantservice.exception.NotFoundException;
import com.reserve.restaurantservice.mapper.UserMapper;
import com.reserve.restaurantservice.repository.RoleRepository;
import com.reserve.restaurantservice.repository.UserLocationRepository;
import com.reserve.restaurantservice.repository.UserRepository;
import com.reserve.restaurantservice.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    EmailService emailService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    JWTGenerator jwtGenerator;

    @Autowired
    UserLocationRepository userLocationRepository;


    public ResponseEntity<String> createUser(UserDto userDto) {

        Optional<User> existingUserName = userRepository.findByUserName(userDto.getUserName());
        Optional<User> existingUserEmailId = userRepository.findByUserEmailId(userDto.getUserEmailId());
        Optional<User> existingUserPhoneNumber = userRepository.findByUserPhoneNumber(userDto.getUserPhoneNumber());

        if (existingUserName.isEmpty() && existingUserEmailId.isEmpty() && existingUserPhoneNumber.isEmpty()) {
            userDto.setUserPassword(this.bCryptPasswordEncoder.encode(userDto.getUserPassword()));
            Role roles = roleRepository.findByName("USER").get();
            userDto.setRoles(Collections.singletonList(roles));
            UserLocation userLocation = userLocationRepository.findByUserPinCode(userDto.getPincode()).orElse(null);
            User user = userMapper.dtoToEntity(userDto);
            user.setUserLocation(userLocation);
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully registered");
        } else {
            if (existingUserName.isPresent()) {
                throw new AlreadyExistException("User already exists!!");
            }
            if (existingUserEmailId.isPresent()) {
                throw new AlreadyExistException("EmailId already exists!!");
            }
            {
                throw new AlreadyExistException("Phone Number already exists!!");
            }
        }
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public User retrieveUser(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User Not Found"));
    }


    public ResponseEntity<List<UserDto>> retrieveAllUsers(Pageable pageable) {
        return new ResponseEntity<>(userMapper.entityToDtos(userRepository.findAll(pageable).getContent().stream().collect(Collectors.toList())), HttpStatus.OK);
    }

    public User updateUser(EditUserDTO editUserDTO) {
        User user = userRepository.findById(editUserDTO.getId()).orElse(null);
        if (user == null) {
            throw new NotFoundException("Record not found with id: " + user.getUserId());
        } else {
            if (editUserDTO.getUserName() != null) {
                user.setUserName(editUserDTO.getUserName());
                userRepository.save(user);
            }
            if (editUserDTO.getEmailId() != null) {
                user.setUserEmailId(editUserDTO.getEmailId());
                userRepository.save(user);
            }
            if (editUserDTO.getPhNo() != null) {
                user.setUserPhoneNumber(editUserDTO.getPhNo());
                userRepository.save(user);
            }
            if (editUserDTO.getfName() != null) {
                user.setUserFullName(editUserDTO.getfName());
                userRepository.save(user);
            }
        }
        return user;
    }

    public User upassword(UPasswordDTO uPasswordDTO) {
        User user = userRepository.findById(uPasswordDTO.getUid()).orElse(null);
        if (user == null) {
            throw new NotFoundException("Record not found with id: " + user.getUserId());
        } else {
            if (uPasswordDTO.getOldPassword() != null && bCryptPasswordEncoder.matches(uPasswordDTO.getOldPassword(), user.getUserPassword())) {
                user.setUserPassword(this.bCryptPasswordEncoder.encode(uPasswordDTO.getNewPassword()));
                return userRepository.save(user);
            } else {
                throw new NotFoundException("Old Password incorrect");
            }
        }
    }

    @Override
    public User findByUserId(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }


    public ResponseEntity<UserInfoResponse> login(LoginJson loginJson) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginJson.getUserName(),
                        loginJson.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Optional<User> user = userRepository.findByUserEmailId(loginJson.getUserName());

        String token = jwtGenerator.generateToken(authentication);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, token).body(new UserInfoResponse(user.get().getUserId(), user.get().getUserName(), user.get().getUserEmailId(), token));

    }

    public ResponseEntity<String> forgotPassword(String userEmail) throws MessagingException {
        Optional<User> optional = userRepository.findByUserEmailId(userEmail);
        if (!optional.isPresent()) {
            return ResponseEntity.badRequest().body("We didn't find an account for the email address");
        } else {
            User user = optional.get();
            Random rn = new Random();
            user.setResetToken(rn.nextInt(999999));
            user.setRequestedTime(System.currentTimeMillis());
            userRepository.save(user);
            emailService.sendEmail(user.getUserEmailId(), "Hi " + user.getUserName() + "." + "<p>To reset your password, Use this 6 digit passcode: </p>" + "<p><b>" + user.getResetToken() + "</b></p>" + "<p>Note: This OTP is set to expire in 5 minutes.</p>" + "<p>If you did not requested a password reset request, you can safely ignore this email and don't share this OTP to anyone.</p>", "Password Reset Request - Valid for 5mins");
            return ResponseEntity.ok().body("A password reset code has been sent to " + user.getUserEmailId());
        }
    }

    public ResponseEntity<String> resetPassword(ResetPassword resetPassword) throws MessagingException {
        Optional<User> user = userRepository.findByResetToken(resetPassword.getResetToken());
        long currentTimeInMillis = System.currentTimeMillis();
        if (user.isPresent()) {
            if (user.get().getRequestedTime() + (5 * 60 * 1000) > currentTimeInMillis) {
                user.get().setUserPassword(this.bCryptPasswordEncoder.encode(resetPassword.getPassword()));
                user.get().setResetToken(null);
                user.get().setRequestedTime(null);
                userRepository.save(user.get());
                emailService.sendEmail(user.get().getUserEmailId(), "<p>Hi " + user.get().getUserName() + ".</p>" + "<p>Your password has been changed successfully, as you asked.</p>" + "<p>If you didn't ask to change your password, please change your password and make it secure as soon as possible.</p>", "Password changed successfully");
                return ResponseEntity.ok("Password Reset Successful");
            } else {
                user.get().setResetToken(null);
                user.get().setRequestedTime(null);
                userRepository.save(user.get());
                return ResponseEntity.badRequest().body("OTP expired, Try Again");
            }
        } else {
            return ResponseEntity.badRequest().body("Enter Valid OTP");
        }
    }
}
