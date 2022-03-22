package io.javabrains.springsecurityjpa.User;


import io.javabrains.springsecurityjpa.Cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName);



}
