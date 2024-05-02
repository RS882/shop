package de.aittr.g_38_jp_shop.controller;


import de.aittr.g_38_jp_shop.domain.entity.Customer;
import de.aittr.g_38_jp_shop.service.interfaces.CustomerService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor

public class CustomerController {

    private final CustomerService service;


    @GetMapping("/all")
    public List<Customer> getAllCustomers() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Customer createNewCustomer(@RequestBody Customer customer) {
        return service.save(customer);
    }

    @PutMapping
    public String updateCustomer(@RequestBody Customer customer) {
        service.update(customer);
        return "Customer updated";
    }

    @DeleteMapping("/{id}")
    public String removeCustomer(@PathVariable Long id) {
        service.deleteById(id);
        return "Customer deleted";
    }

    @GetMapping("/recover/{id}")
    public Customer recoverCustomerById(@PathVariable Long id) {
        return service.recoverDeletedCustomerById(id);
    }

    @GetMapping("/count")
    public Long getCountOfCustomers() {
        return service.getCustomerCount();
    }
}
