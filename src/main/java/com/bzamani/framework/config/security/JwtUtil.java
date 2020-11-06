package com.bzamani.framework.config.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JwtUtil {

  private String secret;
  private int jwtExpirationInMs;

  @Value("${spring.security.jwt.secret}")
  public void setSecret(String secret) {
    this.secret = secret;
  }

  @Value("${spring.security.jwt.expirationDateInMs}")
  public void setJwtExpirationInMs(int jwtExpirationInMs) {
    this.jwtExpirationInMs = jwtExpirationInMs;
  }

  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    return doGenerateToken(claims, userDetails.getUsername());
  }

  private String doGenerateToken(Map<String, Object> claims, String subject) {
    return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
      .signWith(SignatureAlgorithm.HS512, secret).compact();

  }

  public boolean validateToken(String authToken) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
      throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
    } catch (ExpiredJwtException ex) {
      throw ex;
    }
  }

  public String getUsernameFromToken(String token) {
    Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    return claims.getSubject();
  }

  public List<SimpleGrantedAuthority> getRolesFromToken(String token) {
    Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    List<SimpleGrantedAuthority> roles = null;
    roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_" + getUsernameFromToken(token).toUpperCase()));
    return roles;

  }

}
