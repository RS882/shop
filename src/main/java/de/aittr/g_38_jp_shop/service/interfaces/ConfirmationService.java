package de.aittr.g_38_jp_shop.service.interfaces;

import de.aittr.g_38_jp_shop.domain.entity.User;

import java.util.UUID;

public interface ConfirmationService {

    String generateConfirmationCode(User user);
}
