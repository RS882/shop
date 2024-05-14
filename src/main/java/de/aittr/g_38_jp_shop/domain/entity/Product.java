package de.aittr.g_38_jp_shop.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    @NotNull(message = "Product title cannot be null")
    @NotBlank(message = "Product title cannot be empty")
    @Pattern(
            regexp = "[A-Z][a-z]{2,}",
            message = "Product title should be at list 3 character length" +
                    " start with capital letter and contain only latin symbols")
    private String title;

    @Column(name = "price")
    @NotNull(message = "Product price cannot be null")
    @DecimalMin(
            value = "5.00",
            message = "Product price should be greater or equal  than 5")
    @DecimalMax(
            value = "100000.00",
            inclusive = false,//не включительно
            message = "Product price should be lesser  than 100 000")
    private BigDecimal price;

    @Column(name = "is_active")
    private Boolean isActive;


    @Override
    public String toString() {
        return String.format("Product: ID - %d, title - %s, price - %.2f, active - %s",
                id, title, price, isActive ? "yes" : "no");
    }
}
