package de.aittr.g_38_jp_shop.repository;

import de.aittr.g_38_jp_shop.domain.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
