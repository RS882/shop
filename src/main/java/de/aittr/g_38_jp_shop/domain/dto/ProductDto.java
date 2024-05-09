package de.aittr.g_38_jp_shop.domain.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

        private Long productId;
        private String title;
        private BigDecimal price;

    @Override
    public String toString() {
        return String.format("Product: ID - %d, title - %s, price - %.2f",
                productId, title, price);
    }
}
