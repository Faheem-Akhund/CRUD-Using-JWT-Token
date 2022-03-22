package io.javabrains.springsecurityjpa.Cart;


import io.javabrains.springsecurityjpa.Beans.ApproveBean;
import io.javabrains.springsecurityjpa.Beans.ProductCartBean;
import io.javabrains.springsecurityjpa.Product.ProductDTO;
import io.javabrains.springsecurityjpa.Beans.StatusBean;
import io.javabrains.springsecurityjpa.User.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @PostMapping("/cart")
    public StatusBean createProduct(@RequestBody List<ProductCartBean> list, Principal principal) {

        try
        {
            List<ProductDTO> product=cartService.addProducts(list,principal.getName());

            if(product.isEmpty())
            {
                return new StatusBean(0,"failed",null);
            }

            return new StatusBean(1,"success",product);

        }
        catch (Exception e)
        {
            return new StatusBean(0,"failed",null);
        }



    }

    @PostMapping("/user/{id}/product")
    public StatusBean cartProductStatusUpdate(@PathVariable Integer id,@RequestBody ApproveBean approveBean) {

        if(!myUserDetailsService.checkUserExists(id))
        {
            return new StatusBean(0,"user doesnt exists",null);

        }
        else
        {
            if(approveBean.getStatus().isEmpty())
            {
                approveBean.setStatus("APPROVED");
            }


            ApproveBean approveBean1=cartService.Approve(approveBean,id);



            if(approveBean1==null)
            {
                return new StatusBean(0,"user Doesnt have this product in request", myUserDetailsService.userDetailsWithCart(id));

            }

            return new StatusBean(1,"success",myUserDetailsService.userDetailsWithCart(id));

        }




    }

    @GetMapping("cart/approved")
    public StatusBean approved(Principal principal) {

        try
        {
            List<Cart> approved=cartService.getByStatus(principal.getName(),"APPROVED");
            if(approved==null)
            {
                return new StatusBean(0,"failed","No pending items found");
            }
            return new StatusBean(1,"success",approved);
        }

        catch (Exception e)
        {
            return new StatusBean(1,"failed",e.getMessage());
        }

        }


    @GetMapping("cart/pending")
    public StatusBean pending(Principal principal) {

        try
        {
            List<Cart> approved=cartService.getByStatus(principal.getName(),"PENDING");
            if(approved==null)
            {
                return new StatusBean(0,"failed","No pending items found");
            }
            return new StatusBean(1,"success",approved);
        }

        catch (Exception e)
        {
            return new StatusBean(1,"failed",e.getMessage());
        }

    }

    @GetMapping("cart/discarded")
    public StatusBean discarded(Principal principal) {

        try
        {
            List<Cart> approved=cartService.getByStatus(principal.getName(),"DISCARDED");
            if(approved==null)
            {
                return new StatusBean(0,"failed","No pending items found");
            }
            return new StatusBean(1,"success",approved);
        }

        catch (Exception e)
        {
            return new StatusBean(1,"failed",e.getMessage());
        }

    }


    @PostMapping("/user/{id}/products")
    public StatusBean cartProductStatusUpdate(@PathVariable Integer id,@RequestBody List<ApproveBean> approveBean) {

        if(!myUserDetailsService.checkUserExists(id))
        {
            return new StatusBean(0,"user doesnt exists",null);

        }
        else
        {


           cartService.Approved(approveBean,id);


            return new StatusBean(1,"success",myUserDetailsService.userDetailsWithCart(id));

        }




    }










}
