package de.aittr.g_38_jp_shop.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "role")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")

    private Long id;

    @Column(name = "title")

    private String title;

    @Override
    public String getAuthority() {
        return title;
    }
    @Override
    public String toString() {
        return String.format("Role: ID - %d, title - %s", id, title);
    }
}
