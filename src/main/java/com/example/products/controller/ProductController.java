package com.example.products.controller;

import com.example.products.dto.ProductDto;
import com.example.products.entities.ProductEntity;
import com.example.products.services.ProductService;
import com.example.products.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/speculation")
public class ProductController {

    private ProductService productService;

    private MappingUtils mappingUtils;

    @Autowired
    public void setMappingUtils(MappingUtils mappingUtils) {
        this.mappingUtils = mappingUtils;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/simple")
    public String simple() {
        return "Simple GET request";
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable("id") int id) {
        Optional<ProductDto> optionalProductDto = productService.findDtoById(id);
        if(optionalProductDto.isPresent()) {
            ProductDto productDto = optionalProductDto.get();
            productDto.setPackaging("Packaged at its best!");
            productDto.setSalePrice(productDto.getPurchasePrice() * 5);
            return new ResponseEntity<>(productDto, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductDto>> findAllProduct() {
        List<ProductDto> productDtoList = productService.findAllDto();
        productService.addSalePrice(productDtoList);
        productService.addPackaging(productDtoList);
        return new ResponseEntity<>(productDtoList, HttpStatus.OK);
    }

    @PostMapping("/product/add")
    public ResponseEntity<?> addProduct(@RequestBody ProductEntity productEntity) {
        productService.add(productEntity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable(name = "id") int id) {
        productService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/product/update/{id}")
    public ResponseEntity<?> updateProductById(@PathVariable("id") int id,
                                               @RequestBody ProductEntity productEntity) {

        Optional<ProductDto> optional = productService.findDtoById(id);
        if (optional.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            ProductDto productDto = optional.get();
            productDto.setPurchasePrice(productEntity.getPurchasePrice());
            productDto.setName(productEntity.getName());
            productService.add(mappingUtils.toProductEntity(productDto));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }



    //Testing endpoint logic with GET request and without Postman, only use browser
    @GetMapping("/product/update/{id}")
    public ResponseEntity<?> updateProductById(@PathVariable("id") int id,
                                               @RequestParam("name") String name,
                                               @RequestParam("price") int price) {

        Optional<ProductDto> optional = productService.findDtoById(id);
        if (optional.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            ProductDto productDto = optional.get();
            productDto.setPurchasePrice(price);
            productDto.setName(name);
            productService.add(mappingUtils.toProductEntity(productDto));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    //Testing endpoint logic with GET request and without Postman, only use browser
    @GetMapping("/product/add")
    public ResponseEntity<?> addProduct(@RequestParam("name") String name,
                                        @RequestParam("price") int purchasePrice) {

        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(name);
        productEntity.setPurchasePrice(purchasePrice);
        productService.add(productEntity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
