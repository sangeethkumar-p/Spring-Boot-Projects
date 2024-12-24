package com.doc.swagger.SwaggerDocumentation.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service class for handling JSON Web Token (JWT) operations.
 * This class provides methods for generating, extracting information from,
 * and validating JWTs. It uses the HMAC-SHA256 algorithm for signing and
 * verifying tokens.
 */
@Service
public class JWTService {

    /**
     * The secret key used for signing and verifying JWTs.
     * Generated using the HMAC-SHA256 algorithm.
     */
    private final SecretKey securityKey;

    /**
     * Constructor to initialize the JWTService.
     * Generates a secret key using the HMAC-SHA256 algorithm.
     */
    public JWTService() {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("HmacSHA256 algorithm not available", e);
        }
        securityKey = keyGenerator.generateKey();
    }

    /**
     * Generates a JWT for the given username.
     *
     * @param userName The username for which the token is generated.
     * @return A JWT string.
     */
    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .and()
                .signWith(securityKey)
                .compact();
    }

    /**
     * Extracts the username from the given JWT.
     *
     * @param token The JWT from which the username is to be extracted.
     * @return The username contained in the JWT.
     */
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the JWT using the provided claims resolver function.
     *
     * @param token          The JWT from which the claim is to be extracted.
     * @param claimsResolver The function to resolve the claim from the Claims object.
     * @param <T>            The type of the claim.
     * @return The extracted claim.
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the given JWT.
     *
     * @param token The JWT from which all claims are to be extracted.
     * @return A Claims object containing all the claims.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(securityKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Validates the given JWT against the provided UserDetails.
     *
     * @param token        The JWT to validate.
     * @param userDetails  The UserDetails object containing the user information.
     * @return True if the token is valid, false otherwise.
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String userName = extractUserName(token);
        return userDetails.getUsername().equalsIgnoreCase(userName)
                && !isTokenExpired(token);
    }

    /**
     * Checks if the given JWT is expired.
     *
     * @param token The JWT to check.
     * @return True if the token is expired, false otherwise.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the given JWT.
     *
     * @param token The JWT from which the expiration date is to be extracted.
     * @return The expiration date of the JWT.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}