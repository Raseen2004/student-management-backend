package dev.raseen.studentmanagement.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtil {
	@Value("${jwt.secret}")
	private String SECRET;
	
	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET.getBytes());
	}
	
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String extractUsername(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

	public boolean validateToken(String token, UserDetails userDetails) {
	    final String username = extractUsername(token);
	    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
	    Date expiration = Jwts.parserBuilder()
	            .setSigningKey(getSigningKey())
	            .build()
	            .parseClaimsJws(token)
	            .getBody()
	            .getExpiration();
	    return expiration.before(new Date());
	}
	
	private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

// 1. Entity
// 2. Repository
// 3. Service (UserDetailsService)
// 4. PasswordEncoder
// 5. JWT Utility
// 6. JWT Filter
// 7. SecurityConfig
// 8. Controller