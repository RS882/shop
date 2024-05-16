package de.aittr.g_38_jp_shop.service.interfaces;

import de.aittr.g_38_jp_shop.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User register(User user);


}
