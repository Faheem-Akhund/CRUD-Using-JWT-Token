package io.javabrains.springsecurityjpa.Cart;

import io.javabrains.springsecurityjpa.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    Optional<List<Cart>> findByUserAndStatus(User user, String status);
    Optional<List<Cart>> findByUserAndStatusNotAndStatusNot(User user,String approved,String pending);
    Optional<Cart>findByIdAndUserAndStatus(Integer id,User user,String status);

}
