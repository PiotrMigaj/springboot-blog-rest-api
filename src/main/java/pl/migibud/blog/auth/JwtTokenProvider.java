package pl.migibud.blog.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private Long jwtExpirationDate;

    String generateToken(Authentication authentication){
        String userName = authentication.getName();
        Date currentDate = new Date();
        Date expiredDate = new Date(currentDate.getTime()+jwtExpirationDate);
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .signWith(key())
                .compact();
    }

    private Key key(){
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }

    String getUserName(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    boolean validateToken(String token){

        try{
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        }catch (MalformedJwtException ex) {
            throw new RuntimeException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new RuntimeException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new RuntimeException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("JWT claims string is empty.");
        }


    }
}
