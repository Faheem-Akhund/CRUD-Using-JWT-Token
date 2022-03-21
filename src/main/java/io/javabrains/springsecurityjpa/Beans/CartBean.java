package io.javabrains.springsecurityjpa.Beans;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartBean {

    private String userName;
    private Integer productId;
}
