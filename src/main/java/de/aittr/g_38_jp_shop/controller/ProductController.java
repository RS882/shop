package de.aittr.g_38_jp_shop.controller;

import de.aittr.g_38_jp_shop.domain.dto.ProductDto;

import de.aittr.g_38_jp_shop.exception_handler.Response;
import de.aittr.g_38_jp_shop.exception_handler.exceptions.FirstTestException;
import de.aittr.g_38_jp_shop.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable Long id){

        if (id<1){
            throw new FirstTestException("ID is incorrect");
        }

        return service.getById(id);
    }

    @PostMapping
    public ProductDto save(@RequestBody ProductDto product){
        return service.save(product);
    }

    @GetMapping("/all")
    public List<ProductDto> getAll(){
        return service.getAll();
    }

    @PutMapping
    public String updateProduct( @RequestBody ProductDto product){
        service.update(product);
        return "Product updated successfully";
    }

    @ExceptionHandler(FirstTestException.class)
    public Response handleExcepton(FirstTestException e){
        return new Response(e.getMessage());
    }
}
