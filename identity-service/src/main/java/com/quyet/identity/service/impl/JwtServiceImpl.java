package com.quyet.identity.service.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.quyet.identity.entity.*;
import com.quyet.identity.exception.AppException;
import com.quyet.identity.exception.ErrorCode;
import com.quyet.identity.service.JwtService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtServiceImpl implements JwtService {

  @NonFinal
  @Value("${spring.application.name}")
  String issuer;

  @NonFinal
  @Value("${app-config.jwt.access-token-secret}")
  String accessTokenSecret;

  @NonFinal
  @Value("${app-config.jwt.access-token-expiration-ms}")
  Long accessTokenExpirationMs;

  @NonFinal
  @Value("${app-config.jwt.refresh-token-secret}")
  String refreshTokenSecret;

  @NonFinal
  @Value("${app-config.jwt.refresh-token-expiration-ms}")
  Long refreshTokenExpirationMs;

  /**
   * Validate the given JWT token
   *
   * @param token The JWT token as a string
   * @param isRefreshToken Flag indicating if the token is a refresh token
   * @return True if the token is valid, false otherwise
   * @throws ParseException If there is an error parsing the token
   * @throws JOSEException If there is an error verifying the token
   */
  @Override
  public boolean validateToken(String token, boolean isRefreshToken)
      throws ParseException, JOSEException {
    String secret = isRefreshToken ? refreshTokenSecret : accessTokenSecret;

    if (token == null || secret == null || token.isBlank() || secret.isBlank()) {
      return false;
    }

    SignedJWT signedJWT = SignedJWT.parse(token);
    JWSVerifier verifier = new MACVerifier(secret);

    if (signedJWT.verify(verifier)) {
      Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
      return expirationTime != null && new Date().before(expirationTime);
    }

    return false;
  }

  /**
   * Extract SignedJWT from the given token after validation
   *
   * @param token The JWT token as a string
   * @param isRefreshToken Flag indicating if the token is a refresh token
   * @return The extracted SignedJWT object
   * @throws ParseException If there is an error parsing the token
   * @throws JOSEException If there is an error verifying the token
   */
  @Override
  public SignedJWT getSignedJWTFromToken(String token, boolean isRefreshToken)
      throws ParseException, JOSEException {
    String secret = isRefreshToken ? refreshTokenSecret : accessTokenSecret;

    if (token == null || secret == null || token.isBlank() || secret.isBlank()) {
      throw new AppException(ErrorCode.UNAUTHENTICATED);
    }

    SignedJWT signedJWT = SignedJWT.parse(token);
    JWSVerifier verifier = new MACVerifier(secret);

    Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
    if (expirationTime == null || new Date().after(expirationTime)) {
      throw new AppException(ErrorCode.TOKEN_EXPIRED_EXCEPTION);
    }

    if (!signedJWT.verify(verifier)) {
      throw new AppException(ErrorCode.UNAUTHENTICATED);
    }

    return signedJWT;
  }

  /**
   * Build scope claim for JWT based on user roles and permissions
   *
   * @param user The user entity
   * @return The scope claim as a space-separated string
   */
  private String buildScopeClaim(User user) {
    // Build scope claim based on user roles/permissions
    StringJoiner scopeClaim = new StringJoiner(" ");
    List<UserRole> lisUserRoles = user.getListUserRole();
    if (lisUserRoles == null || lisUserRoles.isEmpty()) {
      return scopeClaim.toString();
    }

    // Iterate through user roles and permissions to build scope claim
    lisUserRoles.forEach(
        userRole -> {
          Role role = userRole.getRole();
          if (role == null) {
            return;
          }
          // Add role name as scope
          scopeClaim.add("ROLE_" + role.getRoleName());
          List<RolePermission> listRolePermissions = role.getListRolePermission();
          if (listRolePermissions == null || listRolePermissions.isEmpty()) {
            return;
          }
          listRolePermissions.forEach(
              rolePermission -> {
                Permission permission = rolePermission.getPermission();
                if (permission == null) {
                  return;
                }
                // Add permission name as scope
                scopeClaim.add(permission.getPermissionName());
              });
        });

    return scopeClaim.toString(); // Placeholder implementation
  }

  /**
   * Generate JWT access token for the given user
   *
   * @param user The user entity
   * @return The generated JWT access token as a string
   */
  @Override
  public String generateAccessToken(User user) {
    try {
      JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
      JWTClaimsSet jwtClaimsSet =
          new JWTClaimsSet.Builder()
              .subject(user.getUserId())
              .issuer(issuer)
              .issueTime(new Date())
              .expirationTime(
                  new Date(
                      Instant.now()
                          .plus(accessTokenExpirationMs, ChronoUnit.SECONDS)
                          .toEpochMilli()))
              .claim("scope", buildScopeClaim(user))
              .claim("email", user.getEmail())
              .jwtID(UUID.randomUUID().toString())
              .build();
      SignedJWT signedJWT = new SignedJWT(header, jwtClaimsSet);
      JWSSigner signer = new MACSigner(accessTokenSecret);
      signedJWT.sign(signer);
      return signedJWT.serialize();
    } catch (JOSEException e) {
      log.error("Error while signing JWT", e);
      throw new AppException(ErrorCode.TOKEN_CANNOT_CREATED);
    }
  }

  /**
   * Generate JWT refresh token for the given user
   *
   * @param user The user entity
   * @return The generated JWT refresh token as a string
   */
  @Override
  public String generateRefreshToken(User user) {
    try {
      JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
      JWTClaimsSet jwtClaimsSet =
          new JWTClaimsSet.Builder()
              .subject(user.getUserId())
              .issuer(issuer)
              .issueTime(new Date())
              .expirationTime(
                  new Date(
                      Instant.now()
                          .plus(refreshTokenExpirationMs, ChronoUnit.SECONDS)
                          .toEpochMilli()))
              .claim("email", user.getEmail())
              .jwtID(UUID.randomUUID().toString())
              .build();
      SignedJWT signedJWT = new SignedJWT(header, jwtClaimsSet);
      JWSSigner signer = new MACSigner(refreshTokenSecret);
      signedJWT.sign(signer);
      return signedJWT.serialize();
    } catch (JOSEException e) {
      log.error("Error while signing JWT", e);
      throw new AppException(ErrorCode.TOKEN_CANNOT_CREATED);
    }
  }
}
