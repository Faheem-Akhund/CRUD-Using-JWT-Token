package io.javabrains.springsecurityjpa.Product;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {


    @Autowired(required = true)

    ProductRepository productRepository;

    public Product Create(Product product)
    {
        return productRepository.save(product);
    }

    public List<Product> allProducts()
    {
        return productRepository.findAll();
    }

    public Product getProductbyId(Integer id) {
        Optional<Product> product=productRepository.findById(id);
       if(product.isPresent())
       {
           return product.get();
       }
       return null;
    }

    }
