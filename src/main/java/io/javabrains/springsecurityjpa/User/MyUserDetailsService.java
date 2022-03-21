package io.javabrains.springsecurityjpa.User;

import io.javabrains.springsecurityjpa.Cart.UserCartDetails;
import io.javabrains.springsecurityjpa.Cart.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;




    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUserName(userName);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));

        return user.map(MyUserDetails::new).get();
    }

    public void create(User user)
    {
        userRepository.save(user);
    }


    public User getdeatils(String name)
    {
        User user=userRepository.findByUserName(name).get();
       return user;

    }

    public User getdeatils(Integer id)
    {
        User user=userRepository.findById(id).get();
        return user;
    }

    public Boolean checkUserExists(Integer id)
    {

        Optional<User> user=userRepository.findById(id);
        if(user.isPresent())
        {
            User user1=user.get();

            if(user1.getRoles()=="ROLE_ADMIN" || user1.getRoles().equals("ROLE_ADMIN"))
            {
                return false;
            }


                return true;


        }
        else
        {
            return false;
        }
    }


    public UserCartDetails userDetailsWithCart (Integer id)
    {
        User user=getdeatils(id);
        UserCartDetails userCartDetails=new UserCartDetails();
        userCartDetails.setId(user.getId());
        userCartDetails.setUserName(user.getUserName());

        userCartDetails.setCart(userCart(user.getUserName()));

        return userCartDetails;
    }


    public Set<Cart> userCart(String name)
    {
        Set<Cart> carts=getdeatils(name).getCart();
        Set<Cart> carts1=new HashSet<>();
        for(Cart cart:carts)
        {
            if(cart.getStatus()=="PENDING" || cart.getStatus().equals("PENDING"))
            {
                carts1.add(cart);

            }

        }

        return carts1;
    }

    public Set<Cart> approved(String name)
    {
        Set<Cart> carts=getdeatils(name).getCart();
        Set<Cart> carts1=new HashSet<>();
        for(Cart cart:carts)
        {
            if(cart.getStatus()=="APPROVED" || cart.getStatus().equals("APPROVED"))
            {
                carts1.add(cart);

            }

        }

        return carts1;
    }

    public Set<Cart> dicarded(String name)
    {
        Set<Cart> carts=getdeatils(name).getCart();
        Set<Cart> carts1=new HashSet<>();
        for(Cart cart:carts)
        {
            if(cart.getStatus().equals("APPROVED") || cart.getStatus().equals("PENDING"))
            {


            }
            else
            {
                carts1.add(cart);
            }

        }

        return carts1;
    }
}
