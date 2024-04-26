package de.aittr.g_38_jp_shop.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Product {

    private Long id;
    private String title;
    private BigDecimal price;
    private boolean isActive;

    @Override
    public String toString() {
        return String.format("Product: ID - %d, title - %s, price - %.2f, active - %s",
                id, title, price, isActive ? "yes" : "no");
    }
}
