package de.aittr.g_38_jp_shop.service;

import de.aittr.g_38_jp_shop.domain.entity.ConfirmationCode;
import de.aittr.g_38_jp_shop.domain.entity.User;
import de.aittr.g_38_jp_shop.repository.ConfirmationCodeRepository;
import de.aittr.g_38_jp_shop.repository.UserRepository;
import de.aittr.g_38_jp_shop.service.interfaces.ConfirmationService;
import de.aittr.g_38_jp_shop.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class ConfirmationServiceImpl implements ConfirmationService {

    private final ConfirmationCodeRepository repository;
    private final UserRepository userRepository;

    @Override
    public String generateConfirmationCode(User user) {
        LocalDateTime expired = LocalDateTime.now().plusMinutes(1);
        String code = UUID.randomUUID().toString();
        repository.save(ConfirmationCode.builder()
                .code(code)
                .expired(expired)
                .user(user)
                .build());
        return code;
    }

    @Override
    public boolean checkConfirmationCode(String code) {
        if (code==null || code.isEmpty()) throw new IllegalArgumentException("Code cannot be null or empty");
        ConfirmationCode confirmationCode = repository.findByCode(code);
        if(!code.equals(confirmationCode.getCode())) return false;
        if(LocalDateTime.now().isAfter(confirmationCode.getExpired())) return false;

       User user = confirmationCode.getUser();

        user.setIsActive(true);
        userRepository.save(user);
        return true;
    }
}


