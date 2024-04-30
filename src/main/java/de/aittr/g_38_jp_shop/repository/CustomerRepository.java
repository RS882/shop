package de.aittr.g_38_jp_shop.repository;

import de.aittr.g_38_jp_shop.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<List<Customer>> findAllByIsActiveTrue();

    Optional<Customer> findByIdAndIsActiveTrue(Long id);

    Optional<Customer> findByName(String name);
}
