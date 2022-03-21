package io.javabrains.springsecurityjpa.Cart;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.javabrains.springsecurityjpa.Product.Product;
import io.javabrains.springsecurityjpa.User.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String status="PENDING";

    @ManyToOne
    @JsonIgnore
    private User user;

    @ManyToOne
   private Product product;


}
