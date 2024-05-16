package de.aittr.g_38_jp_shop.service;

import de.aittr.g_38_jp_shop.domain.entity.Role;
import de.aittr.g_38_jp_shop.repository.RoleRepository;
import de.aittr.g_38_jp_shop.service.interfaces.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;


    @Override
    public Role getRoleUser() {
        Role userRole = roleRepository.findByTitle("ROLE_USER");

        if (userRole == null) {
            throw new RuntimeException("Database does not contain ROLE_USER");
        }

        return userRole;
    }
}
