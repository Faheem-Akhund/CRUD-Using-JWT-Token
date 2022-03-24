package io.javabrains.springsecurityjpa.Cart;

import io.javabrains.springsecurityjpa.Product.Product;
import io.javabrains.springsecurityjpa.User.User;
import lombok.Data;

@Data
public class CartDTO {

    private int id;
    private String status;
    private Product product;


}
