package io.javabrains.springsecurityjpa.Cart;

import io.javabrains.springsecurityjpa.Cart.Cart;
import lombok.Getter;
import lombok.Setter;
import org.omg.CORBA.Object;

import java.util.List;
import java.util.Set;


@Setter
@Getter
public class UserCartDetails {

    private int id;
    private String userName;
    private List<CartDTO> cart;
//    private Object cart;

}
