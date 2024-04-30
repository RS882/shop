package de.aittr.g_38_jp_shop.service;

import de.aittr.g_38_jp_shop.domain.entity.Cart;
import de.aittr.g_38_jp_shop.domain.entity.Customer;
import de.aittr.g_38_jp_shop.repository.CartRepository;
import de.aittr.g_38_jp_shop.repository.CustomerRepository;
import de.aittr.g_38_jp_shop.service.interfaces.CartService;
import de.aittr.g_38_jp_shop.service.interfaces.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    private final CartService cartService;


    @Override
    public Customer save(Customer customer) {

        if (customer == null) throw new RuntimeException("Customer data is wrong");

        customer.setIsActive(customer.getIsActive() != null ? customer.getIsActive() : true);

        try {
            Customer createdCustomer = repository.save(customer);

            Cart ceratedCart = cartService.save(
                    Cart.builder()
                            .customer(createdCustomer)
                            .build());
            createdCustomer.setCart(ceratedCart);
            return repository.save(createdCustomer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Customer> findAll() {
        return repository.findAllByIsActiveTrue()
                .orElseThrow(() -> new RuntimeException("Customers not found"));
    }

    @Override
    public Customer findById(Long id) {
        if (id == null) throw new RuntimeException("Id is null");
        return repository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public void update(Customer customer) {
        if (customer == null) throw new RuntimeException("Customer data is wrong");
        if (!repository.existsById(customer.getId())) throw new RuntimeException("Customer not found");
        Customer currentCustomer = repository.findById(customer.getId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        try {
            repository.save(Customer.builder()
                    .id(customer.getId())
                    .name(customer.getName())
                    .isActive(customer.getIsActive() == null ?
                            currentCustomer.getIsActive() : customer.getIsActive())
                    .cart(customer.getCart()==null?currentCustomer.getCart():
                            customer.getCart() )
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) throw new RuntimeException("Customer id is wrong");

        try {
            Customer customer = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Customer not found"));

            customer.setIsActive(false);

            repository.save(customer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByName(String name) {
        if (name == null) throw new RuntimeException("Customer id is wrong");
        Customer customer = repository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setIsActive(false);

        repository.save(customer);
    }

    @Override
    public Customer recoverDeletedCustomerById(Long id) {
        if (id == null) throw new RuntimeException("Customer id is wrong");
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setIsActive(false);
        return repository.save(customer);
    }

    @Override
    public Long getCustomerCount() {
        return Long.valueOf(findAll().size());
    }

    @Override
    public BigDecimal getCartValueByCustomerId(Long id) {
        return null;
    }

    @Override
    public BigDecimal getAveregeCostOfProductInCartByCustomerId(Long id) {
        return null;
    }

    @Override
    public void addProductToCartByCustomerId(Long id, Long productId) {

    }

    @Override
    public void deleteProductFromCartByCustomerId(Long id, Long productId) {

    }

    @Override
    public void deleteAllProductsFromCartByCustomerId(Long id) {

    }
}
