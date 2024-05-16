package de.aittr.g_38_jp_shop.service;

import de.aittr.g_38_jp_shop.domain.entity.User;
import de.aittr.g_38_jp_shop.repository.UserRepository;
import de.aittr.g_38_jp_shop.service.interfaces.EmailService;
import de.aittr.g_38_jp_shop.service.interfaces.RoleService;
import de.aittr.g_38_jp_shop.service.interfaces.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements  UserService {

    private final UserRepository repository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder encoder;
    private final EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
               .orElseThrow(()-> new UsernameNotFoundException(username));

    }

    @Override
    public User register(User user) {

        user.setId(null);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setIsActive(false);
        user.setRoles(Set.of(roleService.getRoleUser()));

        repository.save(user);

        emailService.sendConfirmationEmail(user);
//TODO надо использовать дто!
        return user;
    }




}
