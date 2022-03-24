package io.javabrains.springsecurityjpa.Product;

import io.javabrains.springsecurityjpa.Beans.StatusBean;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/product")
    public StatusBean createProduct(@RequestBody ProductDTO productDTO) {

        try
        {
            productDTO.setId(null);
            return new StatusBean(1,"success",productService.create(productDTO));
        }

        catch (Exception e)
        {
            return new StatusBean(0,"failed to create product",e.getMessage());
        }



    }

    @GetMapping("/products")
    public StatusBean allProducts() {

        try {

            List<ProductDTO> productDTOS=productService.allProducts();
            if(productDTOS.isEmpty())
            {
                return new StatusBean(0,"Failed no products found",null);

            }

            else
            {
                return new StatusBean(1,"Success",productDTOS);
            }


        }
        catch (Exception e)
        {
            return new StatusBean(0,e.toString(), null);
        }



    }

    @GetMapping("/productss/")
    public StatusBean getProductsWithPagination(Pageable pageable) {

        List<Product> products = productService.findProductsWithPagination(pageable);

        return new StatusBean(1,"success pagination",products);
    }

    @GetMapping("/Findproducts/{category}")
    public StatusBean findAllProducts(@PathVariable String category) {

        List<ProductDTO> products = productService.findAllProducts(category);

        return new StatusBean(1,"success pagination",products);
    }



}
