package com.librys.bibliotecalibrys.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.librys.bibliotecalibrys.security.userdetails.UserDetailsImpl;
import org.springframework.stereotype.Service;

import javax.security.auth.Subject;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Service
public class JwtTokenService {

    private static final String SECRET_KEY = "kfsjf#434tjeng";
    private static final String ISSUER = "librys-api";

    public String generateToken(UserDetailsImpl userDetails){

        try{
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withIssuedAt(creationDate())
                    .withExpiresAt(expirantionDate())
                    .withSubject(userDetails.getUsername())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new JWTCreationException("Erro ao gerar token", exception);
        }
    }

    public String getSubjectFromToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
return JWT.require(algorithm)
        .withIssuer(ISSUER)
        .build()
        .verify(token)
        .getSubject();
        }catch (JWTVerificationException exception){
            throw new JWTVerificationException("Tooken inválido ou expirado");
        }

    }

    private Instant expirantionDate() {
        return ZonedDateTime.now(ZoneId.of("America/Recife")).plusHours(4).toInstant();
    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("Ameria/Recife")).toInstant();
    }
}


