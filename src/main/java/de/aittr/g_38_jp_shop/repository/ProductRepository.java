package de.aittr.g_38_jp_shop.repository;

import de.aittr.g_38_jp_shop.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // В данном случае тело метода и SQL запрос
    // будут сгенерированы фреймворком
//    Product findByTitle(String title);

    // В данном случае тело метода будет сгенерировано фреймворком,
    // но сам SQL запрос он применит тот, который мы ему дали
//    @Query(value = "SELECT * FROM product WHERE title = :title", nativeQuery = true)
    Optional<Product> findByTitle(@Param("title") String productTitle);
}
