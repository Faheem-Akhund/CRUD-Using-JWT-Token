package io.javabrains.springsecurityjpa.Product;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    ProductRepository productRepository;

    public ProductDTO create(ProductDTO productDTO)
    {
        Product product=modelMapper.map(productDTO,Product.class);
        product=productRepository.save(product);
        productDTO=modelMapper.map(product,ProductDTO.class);
        return productDTO;
    }

    public List<ProductDTO> allProducts()
    {
        List<Product> products=productRepository.findAll();
        return   Arrays.asList(modelMapper.map(products,ProductDTO[].class));
    }

    public ProductDTO getProductById(Integer id) {
        Optional<Product> product=productRepository.findById(id);
       if(product.isPresent())
       {
           return modelMapper.map(product.get(),ProductDTO.class);
       }

       return null;
    }

    }
