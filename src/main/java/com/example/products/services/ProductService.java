package com.example.products.services;

import com.example.products.dto.ProductDto;
import com.example.products.entities.ProductEntity;
import com.example.products.repositories.ProductRepository;
import com.example.products.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final int MARGIN = 5;

    private final String PACKAGING = "Packaged at its best!";

    private MappingUtils mappingUtils;

    private ProductRepository productRepository;

    @Autowired
    public void setMappingUtils(MappingUtils mappingUtils) {
        this.mappingUtils = mappingUtils;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void add(ProductEntity entity) {
        productRepository.save(entity);
    }

    public ProductEntity get(int id) {
        return productRepository.getOne(id);
    }

    public void addAll(List<ProductEntity> list) {
        productRepository.saveAll(list);
    }

    public List<ProductDto> findAllDto() {
        return productRepository.findAll().stream()
                .map(mappingUtils::toProductDto)
                .collect(Collectors.toList());
    }

    public Optional<ProductDto> findDtoById(int id) {
        Optional<ProductEntity> productDto = productRepository.findById(id);
        if (productDto.isEmpty()) return Optional.empty();
        else return Optional.ofNullable(mappingUtils.toProductDto(productDto.get()));
    }

    public void deleteById(int id) {
        productRepository.deleteById(id);
    }

    public void addSalePrice(List<ProductDto> productDtoList) {
        productDtoList.forEach(product -> product.setSalePrice(product.getPurchasePrice() * MARGIN));
    }

    public void addPackaging(List<ProductDto> productDtoList) {
        productDtoList.forEach(product -> product.setPackaging(PACKAGING));
    }
}
