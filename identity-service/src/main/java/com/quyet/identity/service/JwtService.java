package com.quyet.identity.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import com.quyet.identity.entity.User;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public interface JwtService {

  boolean validateToken(String token, boolean isRefreshToken) throws ParseException, JOSEException;

  SignedJWT getSignedJWTFromToken(String token, boolean isRefreshToken) throws ParseException, JOSEException;

  String generateAccessToken(User user);

  String generateRefreshToken(User user);
}
