package io.javabrains.springsecurityjpa.Cart;

import io.javabrains.springsecurityjpa.Cart.Cart;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Setter
@Getter
public class UserCartDetails {

    private int id;
    private String userName;
    private Set<Cart> cart;


}
