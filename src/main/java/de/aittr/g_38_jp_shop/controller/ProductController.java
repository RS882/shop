package de.aittr.g_38_jp_shop.controller;

import de.aittr.g_38_jp_shop.domain.entity.Product;
import de.aittr.g_38_jp_shop.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping("/example/{id}")
    public Product getById(@PathVariable Long id){
        return service.getById(id);
    }
}
