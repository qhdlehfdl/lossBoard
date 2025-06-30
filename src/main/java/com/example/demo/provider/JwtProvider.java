package com.example.demo.provider;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${secret-key}")
    private String secretKey;
    @Value("${refresh-key}")
    private String refreshKey;

    public String createAccessToken(String ID){

        Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .setSubject(ID).setIssuedAt(new Date()).setExpiration(expiredDate)
                .compact();

        return jwt;
    }

    public String createRefreshToken(String ID){

        Date expiredDate = Date.from(Instant.now().plus(30, ChronoUnit.DAYS));
        Key key = Keys.hmacShaKeyFor(refreshKey.getBytes(StandardCharsets.UTF_8));

        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,refreshKey)
                .setSubject(ID).setIssuedAt(new Date()).setExpiration(expiredDate)
                .compact();

        return jwt;
    }

    public String validateAccessToken(String jwt){
        Claims claims = null;

        try{
            claims = Jwts.parser().setSigningKey(secretKey)
                    .parseClaimsJws(jwt).getBody();
        } catch (ExpiredJwtException exception){
            exception.printStackTrace();
            throw exception;
        }
        catch(Exception exception){
            exception.printStackTrace();
            return null;
        }

        return claims.getSubject();
    }

    public String validateRefreshToken(String jwt){
        Claims claims = null;

        try{
            claims = Jwts.parser().setSigningKey(refreshKey)
                    .parseClaimsJws(jwt).getBody();
        } catch (ExpiredJwtException exception){
            exception.printStackTrace();
            throw exception;
        }
        catch(Exception exception){
            exception.printStackTrace();
            return null;
        }

        return claims.getSubject();
    }
}
