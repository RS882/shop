package de.aittr.g_38_jp_shop.service;

import de.aittr.g_38_jp_shop.domain.entity.Cart;
import de.aittr.g_38_jp_shop.repository.CartRepository;
import de.aittr.g_38_jp_shop.service.interfaces.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {
    private final CartRepository repository;

    @Override
    public Cart save(Cart cart) {
        if (cart == null) throw new RuntimeException("Cart data is wrong");
        return repository.save(cart);
    }
}
