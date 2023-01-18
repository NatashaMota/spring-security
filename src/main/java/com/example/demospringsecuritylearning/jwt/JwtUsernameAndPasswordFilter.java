package com.example.demospringsecuritylearning.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class JwtUsernameAndPasswordFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtUsernameAndPasswordFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

   // @Override
   // public Authentication attemptAuthentication(HttpServletRequest request,
    //                                           HttpServletResponse response) throws AuthenticationException {
      //  try{
        //    UsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper().readValue(request.getInputStream(),
          //          UsernameAndPasswordAuthenticationRequest.class);
           // Authentication authentication = new UsernamePasswordAuthenticationToken(
             //   authenticationRequest.getUsername(), authenticationRequest.getPassword()
            //);
            //return authenticationManager.authenticate();
        //} catch (IOException e){
       //     throw new RuntimeException(e);
        //}
    //}

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                          FilterChain chain, Authentication authResult) throws IOException, ServletException {

        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String base64Key = Encoders.BASE64.encode(key.getEncoded());
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("role", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(30)))
                .signWith(SignatureAlgorithm.HS256, base64Key)
                .compact();

        response.addHeader("Authorization", "Bearer " + token);
    }
}
