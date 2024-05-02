package de.aittr.g_38_jp_shop.service.mapping;

import de.aittr.g_38_jp_shop.domain.dto.ProductDto;
import de.aittr.g_38_jp_shop.domain.entity.Product;

import org.springframework.stereotype.Service;

@Service
//@Mapper(componentModel = "spring")
public class ProductMappingService {

    // @Mapping(target = "productId", source = "id")
    // ProductDto mapEntityToDto(Product product);

    // @Mapping(target = "id", source = "productId")
    public Product mapDtoToEntity(ProductDto productDto) {
        return Product.builder()
                .title(productDto.getTitle())
                .price(productDto.getPrice())
                .isActive(true)
                .build();
    }

    public ProductDto mapEntityToDto(Product product) {
        return ProductDto.builder()
                .productId(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
                .build();
    }
}
