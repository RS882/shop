package de.aittr.g_38_jp_shop.service;


import de.aittr.g_38_jp_shop.domain.dto.ProductDto;


import de.aittr.g_38_jp_shop.domain.entity.Product;
import de.aittr.g_38_jp_shop.repository.ProductRepository;
import de.aittr.g_38_jp_shop.service.interfaces.ProductService;
import de.aittr.g_38_jp_shop.service.mapping.ProductMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMappingService mappingService;


    @Override
    public ProductDto save(ProductDto product) {
        return null;
    }

    @Override
    public List<ProductDto> getAll() {
        return null;
    }

    @Override
    public ProductDto getById(Long id) {
        if (id == null || id < 1) {
            throw new RuntimeException("Product id is incorrect");
        }

        Product product = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Product not found"));



        return mappingService.mapEntityToDto(product);
    }

    @Override
    public void update(ProductDto product) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void deleteByTitle(String title) {

    }

    @Override
    public void restoreById(Long id) {

    }

    @Override
    public int getTotalQuantity() {
        return 0;
    }

    @Override
    public BigDecimal getTotalPrice() {
        return null;
    }

    @Override
    public BigDecimal getAveragePrice() {
        return null;
    }
}