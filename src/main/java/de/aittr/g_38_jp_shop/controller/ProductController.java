package de.aittr.g_38_jp_shop.controller;

import de.aittr.g_38_jp_shop.domain.dto.ProductDto;

import de.aittr.g_38_jp_shop.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping("/example/{id}")
    public ProductDto getById(@PathVariable Long id){
        return service.getById(id);
    }

    @PostMapping("/example")
    public ProductDto save(@RequestBody ProductDto product){
        return service.save(product);
    }
}
