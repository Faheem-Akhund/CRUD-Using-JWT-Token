package io.javabrains.springsecurityjpa.User;

import io.javabrains.springsecurityjpa.Cart.CartService;
import io.javabrains.springsecurityjpa.Cart.UserCartDetails;
import io.javabrains.springsecurityjpa.Cart.Cart;
import org.modelmapper.ModelMapper;
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
    private ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartService cartService;




    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUserName(userName);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));

        return user.map(MyUserDetails::new).get();
    }

    public void create(UserCreateDTO userCreateDTO)
    {
        User user=modelMapper.map(userCreateDTO,User.class);
        user.setActive(true);
        userRepository.save(user);
    }


    public User getDetails(String name)
    {
        Optional<User> user=userRepository.findByUserName(name);
        if(user.isPresent())
        {
            User user1=user.get();
            return user1;
        }
      else
        {
            return null;
        }


    }

    public User getDetails(Integer id)
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
        User user=getDetails(id);
        UserCartDetails userCartDetails=new UserCartDetails();
        userCartDetails.setId(user.getId());
        userCartDetails.setUserName(user.getUserName());

        userCartDetails.setCart(cartService.getByStatus(user.getUserName(),"PENDING"));

        return userCartDetails;
    }








}
