package de.aittr.g_38_jp_shop.security.sec_service;

import de.aittr.g_38_jp_shop.domain.entity.Role;
import de.aittr.g_38_jp_shop.domain.entity.User;
import de.aittr.g_38_jp_shop.repository.RoleRepository;
import de.aittr.g_38_jp_shop.security.Authinfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service

public class TokenService {


    private SecretKey accessKey;
    private SecretKey refreshKey;
    private RoleRepository roleRepository;

    public TokenService(
            @Value("${key.access}") String accessKey,
            @Value("${key.refresh}") String refreshKey,
            RoleRepository roleRepository
    ) {
        this.accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessKey));
        this.refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshKey));
        this.roleRepository = roleRepository;
    }

    public String generateAcessToken(User user) {
        LocalDateTime currectDate = LocalDateTime.now();
        Instant expirationInstant = currectDate.plusDays(7)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        Date expirationDate = Date.from(expirationInstant);
        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(expirationDate)
                .signWith(accessKey)
                .claim("roles", user.getAuthorities())
                .claim("name", user.getUsername())
                .compact();

    }

    public String generateRefreshToken(User user) {
        LocalDateTime currectDate = LocalDateTime.now();
        Instant expirationInstant = currectDate.plusDays(30)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        Date expirationDate = Date.from(expirationInstant);
        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(expirationDate)
                .signWith(refreshKey)
                .compact();

    }

    public boolean validateAcessToken(String accessToken){
        return validateToken(accessToken, accessKey);
    }

    public boolean validateRefreshToken(String refreshToken){
        return validateToken(refreshToken, accessKey);
    }

    private boolean validateToken(String token, SecretKey key) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getAccessCalims(String accessToken){
        return getClaims(accessToken, accessKey);
    }

    public Claims getRefreshCalims(String refreshToken){
        return getClaims(refreshToken, refreshKey);
    }


    private Claims getClaims(String token, SecretKey key){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Authinfo mapClaimsToAuthInfo(Claims claims) {
        String username = claims.getSubject();

        List<LinkedHashMap<String, String>> rolesList = (List<LinkedHashMap<String, String>>) claims.get("roles");

        Set<Role> roles = new HashSet<>();

        for (LinkedHashMap<String, String> roleEntry : rolesList) {
            String roleTitle = roleEntry.get("authority");
            Role role = roleRepository.findByTitle(roleTitle);
            roles.add(role);
        }

        return new Authinfo(username, roles);
    }
}




