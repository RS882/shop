package de.aittr.g_38_jp_shop.controller;

import de.aittr.g_38_jp_shop.domain.entity.User;
import de.aittr.g_38_jp_shop.service.interfaces.ConfirmationService;
import de.aittr.g_38_jp_shop.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService service;

    private final ConfirmationService confirmationService;

    //TODO надо использовать дто!
    @PostMapping
    public User register(@RequestBody User user) {

        return service.register(user);
    }

    @GetMapping
    public ResponseEntity<?> confirmation(@RequestParam("code") String code) {
        boolean isConfirmated = confirmationService.checkConfirmationCode(code);

        return isConfirmated? new ResponseEntity( "Confirmation is successful!",HttpStatus.OK):
                new ResponseEntity("Confirmation code is incorrect!",HttpStatus.BAD_REQUEST);

  }
}