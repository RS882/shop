package de.aittr.g_38_jp_shop.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "confirm_code")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ConfirmationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "expired")
    private LocalDateTime expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
