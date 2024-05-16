package de.aittr.g_38_jp_shop.service;

import de.aittr.g_38_jp_shop.domain.entity.ConfirmationCode;
import de.aittr.g_38_jp_shop.domain.entity.User;
import de.aittr.g_38_jp_shop.repository.ConfirmationCodeRepository;
import de.aittr.g_38_jp_shop.service.interfaces.ConfirmationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class ConfirmationServiceImpl implements ConfirmationService {

    private final ConfirmationCodeRepository repository;

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
}
