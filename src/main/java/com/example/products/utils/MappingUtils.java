package com.example.products.utils;

import com.example.products.dto.ProductDto;
import com.example.products.entities.ProductEntity;
import org.springframework.stereotype.Service;

@Service
public class MappingUtils {

    public ProductDto toProductDto(ProductEntity entity) {
        return new ProductDto()
                .setId(entity.getId())
                .setName(entity.getName())
                .setPurchasePrice(entity.getPurchasePrice());
    }

    public ProductEntity toProductEntity(ProductDto dto) {
        return new ProductEntity()
                .setId(dto.getId())
                .setName(dto.getName())
                .setPurchasePrice(dto.getPurchasePrice());
    }
}
