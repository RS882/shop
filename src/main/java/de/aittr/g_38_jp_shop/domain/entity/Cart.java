package de.aittr.g_38_jp_shop.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;



@Entity
@Table(name = "cart")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "customer")
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    private Long id;


    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @Getter
    @Setter
    @JsonIgnore
    private Customer customer;


}
