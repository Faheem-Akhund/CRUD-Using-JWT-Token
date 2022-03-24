package io.javabrains.springsecurityjpa.Product;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {


    @Query(value = "SELECT u FROM Product u WHERE u.category=:name")
    List<Product> findAllProducts(Sort sort,String name);

    @Query(value = "SELECT u FROM Product u WHERE u.category=:name")
    List<Product> findProductsbycategory(Sort sort,String name);




}
