package com.ingress.bookstore.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider
{
    @Value("${bookstore.app.secret}")
    private String APP_SECRET;
    @Value("${bookstore.expires.in}")
    private Long EXPIRES_IN;

    private SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    public String generateJwtToken(Authentication authentication)
    {
        JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
        Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);

        return Jwts.builder().setSubject(Long.toString(userDetails.getId()))
                .setIssuedAt(new Date()).setExpiration(expireDate)
                .signWith(secretKey, SignatureAlgorithm.HS512).compact();
    }

    public Long getUserIdFromJWT(String token)
    {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String token)
    {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !isTokenExpired(token);
        } catch (SignatureException ex) {
            return false;
        } catch (MalformedJwtException ex) {
            return false;
        } catch (ExpiredJwtException ex) {
            return false;
        } catch (UnsupportedJwtException ex) {
            return false;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }
}
