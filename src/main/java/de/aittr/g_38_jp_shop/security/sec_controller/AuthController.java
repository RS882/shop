package de.aittr.g_38_jp_shop.security.sec_controller;

import de.aittr.g_38_jp_shop.domain.entity.User;
import de.aittr.g_38_jp_shop.security.sec_dto.RefrechRequestDto;
import de.aittr.g_38_jp_shop.security.sec_dto.TokenResponseDto;
import de.aittr.g_38_jp_shop.security.sec_service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user){
        try{
            return ResponseEntity.ok(service.login(user));
        }catch(Exception e){
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
    @PostMapping("/access")
    public ResponseEntity<Object> getNewAccessToken(@RequestBody RefrechRequestDto request){
        try{
            return ResponseEntity.ok(service.getAccessToken(request.getRefreshToken()));
        }catch(Exception e){
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
