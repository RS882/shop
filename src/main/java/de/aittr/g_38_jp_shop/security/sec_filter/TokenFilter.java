package de.aittr.g_38_jp_shop.security.sec_filter;

import de.aittr.g_38_jp_shop.security.Authinfo;
import de.aittr.g_38_jp_shop.security.sec_service.TokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenFilter extends GenericFilterBean {


    private final TokenService service;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = getTorkenFromRequest((HttpServletRequest) request);

        if (token != null && service.validateAcessToken(token)) {

            Claims claims = service.getAccessCalims(token);

            Authinfo authinfo = service.mapClaimsToAuthInfo(claims);
            authinfo.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(authinfo);
        }
        filterChain.doFilter(request, response);
    }

    private String getTorkenFromRequest(HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}
