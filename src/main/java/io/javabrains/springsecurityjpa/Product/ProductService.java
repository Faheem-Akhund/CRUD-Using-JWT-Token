package io.javabrains.springsecurityjpa.Product;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public List<ProductDTO> findAllProducts(String category)
    {
        List<Product> products=productRepository.findAllProducts(Sort.unsorted(),category);
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


    public List<Product> findProductsWithPagination(Pageable pageable){

        Page<Product> products = productRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));

        System.out.println(pageable);
        System.out.println(pageable.getPageNumber());
        System.out.println(pageable.getOffset());


        return products.getContent();
    }

    }
