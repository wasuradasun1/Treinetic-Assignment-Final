package com.treinetic.taskmanager.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service class responsible for generating, validating, and parsing JWT tokens.
 * <p>
 * This class uses the io.jsonwebtoken (jjwt) library to perform operations on JWTs,
 * such as creating signed tokens and extracting claims like username and expiration.
 * </p>
 */
@Service
public class JwtService {
    /**
     * Secret key used to sign the JWT.
     * Loaded from application properties: {@code app.jwt.secret}.
     */
    @Value("${app.jwt.secret}")
    private String secretKey;

    /**
     * Token expiration time in milliseconds.
     * Loaded from application properties: {@code app.jwt.expiration}.
     */
    @Value("${app.jwt.expiration}")
    private long jwtExpiration;

    /**
     * Extracts the username (subject) from the given JWT token.
     *
     * @param token the JWT token
     * @return the username embedded in the token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the token using the given resolver function.
     *
     * @param token the JWT token
     * @param claimsResolver a function to extract a specific claim
     * @param <T> the type of the claim
     * @return the extracted claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a JWT token with no additional claims for the given user.
     *
     * @param userDetails the user information
     * @return the generated JWT token
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token with extra claims for the given user.
     *
     * @param extraClaims additional claims to include in the token
     * @param userDetails the user information
     * @return the generated JWT token
     */
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    /**
     * Builds and signs the JWT token using the provided data and expiration time.
     *
     * @param extraClaims any additional claims
     * @param userDetails the user data
     * @param expiration the expiration time in milliseconds
     * @return the signed JWT token
     */
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates the token by checking the username and expiration.
     *
     * @param token the JWT token
     * @param userDetails the user to validate against
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }


    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}