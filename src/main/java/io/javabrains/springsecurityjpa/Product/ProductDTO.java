package io.javabrains.springsecurityjpa.Product;


import lombok.Data;

@Data
public class ProductDTO {

    private Integer id;
    private String category;
    private String productName;
    private Integer price;
}
