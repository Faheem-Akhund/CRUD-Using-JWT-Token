package io.javabrains.springsecurityjpa.Cart;


import io.javabrains.springsecurityjpa.Beans.ApproveBean;
import io.javabrains.springsecurityjpa.Beans.ProductCartBean;
import io.javabrains.springsecurityjpa.Product.ProductDTO;
import io.javabrains.springsecurityjpa.Product.Product;
import io.javabrains.springsecurityjpa.Product.ProductService;
import io.javabrains.springsecurityjpa.User.MyUserDetailsService;
import io.javabrains.springsecurityjpa.User.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;



@Service
public class CartService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartRepository repository;

    @Autowired
    private ProductService productService;

    @Autowired
    private MyUserDetailsService userDetailsService;


    public List<ProductDTO> addProducts(List<ProductCartBean> productCartBeans,String userName) {


        List<Product> products=new ArrayList<>();
        User user1=userDetailsService.getDetails(userName);
        Set<Cart> carts=new HashSet<>();

        for(ProductCartBean productCartBean: productCartBeans )
        {
            ProductDTO productDTO=productService.getProductById(productCartBean.getId());
            Product product=modelMapper.map(productDTO,Product.class);

            if(product!=null)
            {
                Cart cart=new Cart();
                cart.setUser(user1);
                cart.setProduct(product);
                products.add(product);
                carts.add(cart);
            }
        }
        repository.saveAll(carts);

        if(products.isEmpty())
        {
            return null;

        }

        return  Arrays.asList(modelMapper.map(products,ProductDTO[].class));


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


    public void Approved(List<ApproveBean> approveBean,Integer id) {

        User user=new User();
        user.setId(id);
        Optional<List<Cart>> carts=repository.findByUserAndStatus(user,"PENDING");
        List<Cart> carts1=carts.get();

        for(Cart cart: carts1 )
        {
            for(ApproveBean approveBean1:approveBean)
            {
                if(cart.getId()==approveBean1.getId())
                {
                    cart.setStatus(approveBean1.getStatus());
                    break;
                }

            }

        }



        repository.saveAll(carts1);


    }

    public List<Cart> getByStatus(String name,String status) {
        User user=userDetailsService.getDetails(name);
        try {
            if(status.equals("DISCARDED"))
            {
                Optional<List<Cart>> carts=repository.findByUserAndStatusNotAndStatusNot(user,"APPROVED","PENDING");

                return carts.get();


            }
            Optional<List<Cart>> carts=repository.findByUserAndStatus(user,status);

            return carts.get();

            }

        catch (Exception e)
        {
            return null;
        }

    }


}
