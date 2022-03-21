package io.javabrains.springsecurityjpa.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.javabrains.springsecurityjpa.Cart.Cart;
import jdk.nashorn.internal.objects.annotations.Property;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique=true)
    private String userName;

    private String password;

    private boolean active;

    private String roles;


    @OneToMany(mappedBy="user")
    @JsonIgnoreProperties("user")
    private Set<Cart> cart;



}
