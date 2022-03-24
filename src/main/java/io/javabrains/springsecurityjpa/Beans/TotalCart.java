package io.javabrains.springsecurityjpa.Beans;

import lombok.Data;

@Data
public class TotalCart {

    Integer total;
    Object cart;

    public TotalCart(Integer total, Object cart) {
        this.total = total;
        this.cart = cart;
    }
}
