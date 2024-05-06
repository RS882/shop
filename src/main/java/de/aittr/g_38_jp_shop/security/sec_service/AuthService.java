package de.aittr.g_38_jp_shop.security.sec_service;

import de.aittr.g_38_jp_shop.domain.entity.User;
import de.aittr.g_38_jp_shop.security.sec_dto.TokenResponseDto;
import de.aittr.g_38_jp_shop.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Nonnull;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final TokenService tokenService;
    public Map<String, String> refreshStorage = new HashMap<>();
    private final BCryptPasswordEncoder encoder;

    public TokenResponseDto login(@Nonnull User inboundUser) throws AuthException {
        String username = inboundUser.getUsername();
        User foundUser = (User) userService.loadUserByUsername(username);

        if (encoder.matches(inboundUser.getPassword(), foundUser.getPassword())) {
            String accessToken = tokenService.generateAcessToken(foundUser);
            String refreshToken = tokenService.generateRefreshToken(foundUser);
            refreshStorage.put(username, refreshToken);
            return new TokenResponseDto(accessToken, refreshToken);


        } else {
            throw new AuthException("Password is incorrect");
        }
    }

    public TokenResponseDto getAccessToken(@Nonnull String refreshToken)  {
        if(tokenService.validateRefreshToken(refreshToken)){
            Claims refreshClaims = tokenService.getRefreshCalims(refreshToken);
            String username = refreshClaims.getSubject();
            String savedRefreshToken = refreshStorage.get(username);

            if(refreshToken.equals(savedRefreshToken)){
                User user =(User) userService.loadUserByUsername(username);
                String accessToken = tokenService.generateAcessToken(user);
                return new TokenResponseDto(accessToken,null);
            }
        }
        throw new RuntimeException("Refresh token is incorrect");
    }
}
