package com.example.products.utils;

import com.example.products.dto.ProductDto;
import com.example.products.entities.ProductEntity;
import com.example.products.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class InitiateUtil implements CommandLineRunner {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {

        List<ProductEntity> productEntityList = new ArrayList<>(
                Arrays.asList(
                        new ProductEntity()
                        .setName("Potato")
                        .setPurchasePrice(20),
                        new ProductEntity()
                        .setName("Carrot")
                        .setPurchasePrice(14)
                )
        );

        productService.addAll(productEntityList);
        List<ProductDto> productDtoList = productService.findAllDto();
        productService.addPackaging(productDtoList);
        productService.addSalePrice(productDtoList);

        System.out.println("\nAll products: ");
        for (ProductDto productDto : productDtoList)
            System.out.println(String.format("Buy: %s for %s.", productDto.getName(), productDto.getSalePrice()));


    }
}
