package io.javabrains.springsecurityjpa.Product;

import io.javabrains.springsecurityjpa.Beans.StatusBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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


}
