package de.aittr.g_38_jp_shop.service.interfaces;

import de.aittr.g_38_jp_shop.domain.entity.Customer;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerService {
    Customer save(Customer customer);

    List<Customer> findAll();

    Customer findById(Long id);

    void update(Customer customer);

    void deleteById(Long id);

    void deleteByName(String name);

    Customer recoverDeletedCustomerById(Long id);

    Long getCustomerCount();

    BigDecimal getCartValueByCustomerId(Long id);

    BigDecimal getAveregeCostOfProductInCartByCustomerId(Long id);

    void addProductToCartByCustomerId(Long id, Long productId);

    void deleteProductFromCartByCustomerId(Long id, Long productId);

    void deleteAllProductsFromCartByCustomerId(Long id);

}
