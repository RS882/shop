package de.aittr.g_38_jp_shop.service;


import de.aittr.g_38_jp_shop.domain.dto.ProductDto;


import de.aittr.g_38_jp_shop.domain.entity.Product;
import de.aittr.g_38_jp_shop.domain.entity.User;
import de.aittr.g_38_jp_shop.repository.ProductRepository;
import de.aittr.g_38_jp_shop.service.interfaces.ProductService;
import de.aittr.g_38_jp_shop.service.mapping.ProductMappingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import org.slf4j.Logger;


@RequiredArgsConstructor
@Service
//@Slf4j
public class ProductServiceImpl implements ProductService {

//    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class.getName() );

    private final ProductRepository repository;
    private final ProductMappingService mappingService;


    @Override
    public ProductDto save(ProductDto product) {
        Product newProduct = mappingService.mapDtoToEntity(product);
        repository.save(newProduct);
        return mappingService.mapEntityToDto(newProduct);

    }

    @Override
    public List<ProductDto> getAll() {

        return repository.findAll().stream()
                .filter(Product::isActive)
                .map(mappingService::mapEntityToDto)
                .toList();
    }

    @Override
    public ProductDto getById(Long id) {

//        log.info("DA");
        if (id == null || id < 1) {
            throw new RuntimeException("Product id is incorrect");
        }
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return mappingService.mapEntityToDto(product);
    }

    @Transactional
    @Override
    public void update(ProductDto product) {
        Long id = product.getProductId();
        if (id == null || id < 1) throw new RuntimeException("Product id is incorrect");

        try {
            Product updateProduct = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            updateProduct.setTitle(product.getTitle() == null ? updateProduct.getTitle() : product.getTitle());
            updateProduct.setPrice(product.getPrice()  == null ? updateProduct.getPrice() : product.getPrice());


        } catch (Exception ex) {
            throw new RuntimeException("Product not found");
        }


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