package com.example.products.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductDto {

    private int id;

    private String name;

    private int purchasePrice;

    private String packaging;

    private int salePrice;
}
