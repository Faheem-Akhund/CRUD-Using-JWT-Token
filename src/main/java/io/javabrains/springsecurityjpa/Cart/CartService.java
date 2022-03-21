package io.javabrains.springsecurityjpa.Cart;


import io.javabrains.springsecurityjpa.Beans.ApproveBean;
import io.javabrains.springsecurityjpa.Beans.ProductCartBean;
import io.javabrains.springsecurityjpa.Product.Product;
import io.javabrains.springsecurityjpa.Product.ProductService;
import io.javabrains.springsecurityjpa.User.MyUserDetailsService;
import io.javabrains.springsecurityjpa.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;



@Service
public class CartService {

    @Autowired
    private CartRepository repository;

    @Autowired
    private ProductService productService;

    @Autowired
    private MyUserDetailsService userDetailsService;


    public List<Product> addProducts(List<ProductCartBean> productCartBeans,String userName) {


        List<Product> products=new ArrayList<>();
        User user1=userDetailsService.getdeatils(userName);

        for(ProductCartBean productCartBean: productCartBeans )
        {
            Product product=productService.getProductbyId(productCartBean.getId());

            if(product!=null)
            {
                Cart cart=new Cart();
                cart.setUser(user1);
                cart.setProduct(product);
                products.add(product);
                repository.save(cart);
            }


        }

        if(products.isEmpty())
        {
            return null;

        }

        return products;


    }

    public ApproveBean Approve(ApproveBean approveBean,Integer id) {
        Optional<Cart> cart =repository.findById(approveBean.getId());

        if(cart.isPresent())
        {
            Cart cart1=cart.get();

            User user=cart1.getUser();

            if(user.getId()!=id)
            {
                return null;

            }


            if(cart1.getStatus()=="PENDING" || cart1.getStatus().equals("PENDING"))
            {
                cart1.setStatus(approveBean.getStatus());
                repository.save(cart1);
                return approveBean;
            }
            else
            {
                return null;
            }


        }
        else
        {
            return null;
        }

    }


}
