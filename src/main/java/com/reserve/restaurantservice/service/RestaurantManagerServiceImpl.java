package com.reserve.restaurantservice.service;

import com.reserve.restaurantservice.dto.AuthResponseDTO;
import com.reserve.restaurantservice.dto.LoginJson;
import com.reserve.restaurantservice.dto.RestaurantManagerDto;
import com.reserve.restaurantservice.entities.Restaurant;
import com.reserve.restaurantservice.entities.RestaurantManager;
import com.reserve.restaurantservice.entities.Role;
import com.reserve.restaurantservice.exception.AlreadyExistException;
import com.reserve.restaurantservice.exception.NotFoundException;
import com.reserve.restaurantservice.mapper.RestaurantManagerMapper;
import com.reserve.restaurantservice.repository.RestaurantManagerRepository;
import com.reserve.restaurantservice.repository.RestaurantRepository;
import com.reserve.restaurantservice.repository.RoleRepository;
import com.reserve.restaurantservice.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantManagerServiceImpl implements RestaurantManagerService {
    @Autowired
    RestaurantManagerRepository restaurantManagerRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    RestaurantManagerMapper restaurantManagerMapper;
    @Autowired
    JWTGenerator jwtGenerator;

    @Autowired
    RestaurantRepository restaurantRepository;

    public RestaurantManager createRestaurantManager(RestaurantManagerDto restaurantManagerDto) {

        Optional<RestaurantManager> existingRestaurantManagerUserName = restaurantManagerRepository.findByRestaurantManagerUserName(restaurantManagerDto.getRestaurantManagerUserName());
        Optional<RestaurantManager> existingRestaurantManagerEmailId = restaurantManagerRepository.findByRestaurantManagerEmailId(restaurantManagerDto.getRestaurantManagerEmailId());
        if (existingRestaurantManagerEmailId.isEmpty() && existingRestaurantManagerUserName.isEmpty()) {
            restaurantManagerDto.setRestaurantManagerPassword(this.bCryptPasswordEncoder.encode(restaurantManagerDto.getRestaurantManagerPassword()));
            Role roles = roleRepository.findByName("MANAGER").get();
            restaurantManagerDto.setRoles(Collections.singletonList(roles));
            return restaurantManagerRepository.save(restaurantManagerMapper.dtoToEntity(restaurantManagerDto));
        } else {
            if (existingRestaurantManagerUserName.isPresent()) {
                throw new AlreadyExistException("Restaurant Manager UserName already exists!!");
            }
            {
                throw new AlreadyExistException("Restaurant Manager Email already exists!!");
            }
        }

    }

    public void deleteRestaurantManager(Integer id) {
        restaurantManagerRepository.deleteById(id);
    }

    public RestaurantManager retrieveRestaurantManager(Integer id) {
        return restaurantManagerRepository.findById(id).orElseThrow(() -> new NotFoundException("Restaurant Manager Not Found"));
    }

    public List<RestaurantManagerDto> retrieveAllRestaurantManagerById(Pageable pageable) {
        return restaurantManagerMapper.entityToDtos(restaurantManagerRepository.findAll(pageable).getContent().stream().collect(Collectors.toList()));
    }

    public RestaurantManager updateRestaurantManager(RestaurantManager restaurantManager) {
        Optional<RestaurantManager> restaurantExits = restaurantManagerRepository.findById(restaurantManager.getRestaurantManagerId());
        if (restaurantExits.isEmpty()) {
            throw new NotFoundException("Record not found with id: " + restaurantManager.getRestaurantManagerId());
        } else {
            return restaurantManagerRepository.save(restaurantManager);
        }
    }

    public ResponseEntity<AuthResponseDTO> restaurantLogin(LoginJson loginJson) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginJson.getUserName(), loginJson.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        RestaurantManager restaurantManager = restaurantManagerRepository.findByRestaurantManagerEmailId(loginJson.getUserName()).orElse(null);
        Restaurant restaurant = restaurantRepository.findByRestaurantManager(restaurantManager).orElse(null);
        String token = jwtGenerator.generateToken(authentication);

        if(restaurant!=null)
        {
            return ResponseEntity.ok().body(new AuthResponseDTO(restaurantManager.getRestaurantManagerId(),restaurantManager.getRestaurantManagerUserName(),restaurant.getRestaurantId(),token));
        }
        else {
            return ResponseEntity.ok().body(new AuthResponseDTO(restaurantManager.getRestaurantManagerId(),restaurantManager.getRestaurantManagerUserName(),null,token));

        }

//        Optional<RestaurantManager> restaurantManager =  restaurantManagerRepository.findByRestaurantManagerEmailId(loginJson.getUserName());
//        if(restaurantManager == null){
//            throw new NotFoundException("User does not exist.");
//        }
//        if(!bCryptPasswordEncoder.matches(loginJson.getPassword(),restaurantManager.get().getRestaurantManagerPassword())){
//            throw new NotFoundException("Password mismatch.");
//        }
//        else{
//            return ResponseEntity.ok().body("UserLoginSuccess");
//
//        }
    }

}
