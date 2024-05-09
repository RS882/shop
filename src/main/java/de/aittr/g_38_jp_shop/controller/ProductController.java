package de.aittr.g_38_jp_shop.controller;

import de.aittr.g_38_jp_shop.domain.dto.ProductDto;

import de.aittr.g_38_jp_shop.exception_handler.Response;
import de.aittr.g_38_jp_shop.exception_handler.exceptions.FirstTestException;
import de.aittr.g_38_jp_shop.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    // 1 СПОСОБ обработки ошибок
    // ПЛЮС - у нас есть обработчик ошибок для контроллера (всех его методов)
    // Мы можем точечно настраивать обработчик для данного конкретного контроллера,
    // если нам требуется разная логика обработки того же самого исключения
    // в разных контроллерах.
    // МИНУС - если нам не требуется разной логики обработки ошибок,
    // нам придётся писать такой обработчик в каждом контроллере.
    @ExceptionHandler(FirstTestException.class)
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public Response handleException(FirstTestException e) {
        return new Response(e.getMessage());
    }
}
