package com.moveinsync.flightbooking.configuration;
import com.moveinsync.flightbooking.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final static String Secret = "ZBlt3w4fMYGh4pOzqxltO9XDPdSxITLQ2H8eOZUDdylZb6e5H6z5U6YyU9ukpveblODBCdBHm5bMkU5h5JmkY6iw";

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS512, Secret)
                .compact();
    }

    public boolean validateToken(String token, User user) {
        String username = extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(Secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isTokenExpired(String token) {
        return Jwts.parser().setSigningKey(Secret).parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

}
