package com.reserve.restaurantservice.security;

import com.reserve.restaurantservice.entities.Admin;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class AdminTokenGenerator {

        public String generateToken(Admin admin) {
            String username = admin.getAdminFullName();
            Date currentDate = new Date();
            Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

            String token = Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(expireDate)
                    .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SECRET)
                    .compact();
            return token;
        }

}
