package com.maids.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String
            SECRATE_KEY=
            "9300dcf833cbcb224c37fa9ebe7f7b17fe4aa7708934eca2066cb7ca91442e07";




    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }
    public String generateToken(Map<String, Object>extractClaims, UserDetails userDetails){

        return Jwts.builder()
                .setClaims(extractClaims)
                .setIssuer("https://services.maids.com")
                .setSubject(userDetails.getUsername())
                .setAudience("https://maids.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000 *60 *24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }



    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUserEmail(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    public String extractUserEmail(String token){
        return extractClaim(token, Claims::getSubject);

    }
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public Date extractIssuedAt(String token) {
        return extractClaim(token, Claims::getIssuedAt);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }



    private Key getSigningKey() {
        byte [] keybytes = Decoders.BASE64.decode(SECRATE_KEY);
        return Keys.hmacShaKeyFor(keybytes);
    }

}
