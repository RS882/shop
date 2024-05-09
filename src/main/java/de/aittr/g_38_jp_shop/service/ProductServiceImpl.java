package de.aittr.g_38_jp_shop.service;


import de.aittr.g_38_jp_shop.domain.dto.ProductDto;


import de.aittr.g_38_jp_shop.domain.entity.Customer;
import de.aittr.g_38_jp_shop.domain.entity.Product;

import de.aittr.g_38_jp_shop.exception_handler.exceptions.IncorrectIdException;
import de.aittr.g_38_jp_shop.exception_handler.exceptions.IncorrectTitleException;
import de.aittr.g_38_jp_shop.exception_handler.exceptions.ProductNotFoundException;
import de.aittr.g_38_jp_shop.exception_handler.exceptions.ProductServiceException;
import de.aittr.g_38_jp_shop.repository.ProductRepository;
import de.aittr.g_38_jp_shop.service.interfaces.ProductService;
import de.aittr.g_38_jp_shop.service.mapping.ProductMappingService;
import jakarta.transaction.Transactional;
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
        Product newProduct = mappingService.mapDtoToEntity(product);
        repository.save(newProduct);
        return mappingService.mapEntityToDto(newProduct);

    }

    @Override
    public List<ProductDto> getAll() {

        return repository.findAll().stream()
                .filter(Product::getIsActive)
                .map(mappingService::mapEntityToDto)
                .toList();
    }


    @Override
    public ProductDto getById(Long id) {
        return mappingService.mapEntityToDto(getProductById(id));
    }



    @Transactional
    @Override
    public void update(ProductDto product) {
        if(product==null) throw
                new ProductServiceException("Method of ProductServiceImpl worked incorrectly",
                        new IncorrectTitleException("Product is null"));
        Long id = product.getProductId();

        Product updateProduct = getProductById(id);

        updateProduct.setTitle(product.getTitle() == null ? updateProduct.getTitle() : product.getTitle());
        updateProduct.setPrice(product.getPrice() == null ? updateProduct.getPrice() : product.getPrice());

    }


    @Override
    @Transactional
    public void deleteById(Long id) {

        Product product = getProductById(id);

        product.setIsActive(false);
    }

    @Override
    @Transactional
    public void deleteByTitle(ProductDto productDto) {
        if(productDto==null) {
            new ProductServiceException("Method of ProductServiceImpl worked incorrectly",
                    new IncorrectTitleException("Product is null"));
        }

        String title = productDto.getTitle();

        if (title == null) {
            throw new ProductServiceException("Method of ProductServiceImpl worked incorrectly",
                    new IncorrectTitleException("Product title is wrong"));
        }
        Product product = repository.findByTitle(title)
                .orElseThrow(() -> new ProductServiceException("Method of ProductServiceImpl worked incorrectly",
                        new ProductNotFoundException("Product not found")));
        product.setIsActive(false);
    }

    @Override
    public void restoreById(Long id) {
        Product product = getProductById(id);
        product.setIsActive(true);
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

    private Product getProductById(Long id) {
        if (id == null || id < 1) {
            System.out.println();
            throw new ProductServiceException("Method of ProductServiceImpl worked incorrectly",
                    new IncorrectIdException("Product id is incorrect"));
        }

        return repository.findById(id)
                .orElseThrow(() -> new ProductServiceException("Method of ProductServiceImpl worked incorrectly",
                        new ProductNotFoundException("Product not found")));
    }
}