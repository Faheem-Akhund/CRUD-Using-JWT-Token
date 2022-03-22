package io.javabrains.springsecurityjpa.User;

import io.javabrains.springsecurityjpa.Beans.*;
import io.javabrains.springsecurityjpa.Cart.Cart;
import io.javabrains.springsecurityjpa.Cart.UserCartDetails;
import io.javabrains.springsecurityjpa.JWT.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Date;

import java.util.Optional;
import java.util.Set;


@RestController
public class UserController {


    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService service;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @PostMapping("/authenticate")
    public StatusBean authenticate(@RequestBody AuthenticationRequest authenticationRequest){

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {

          return new StatusBean(0,e.getMessage(),"User not found");
        }


        final UserDetails user =service.loadUserByUsername(authenticationRequest.getUsername());


        RefreshToken refreshToken=refreshTokenService.createRefreshToken(service.getDetails(user.getUsername()).getId());

        final String jwt = jwtTokenUtil.generateToken(user);

        UserToken userToken=new UserToken(user.getUsername(),jwt,refreshToken.getToken());

        return new StatusBean(0,"success",userToken);

    }


    @PostMapping("/refreshtoken")
    public StatusBean refreshtoken(@RequestBody RefreshTokenBean refreshToken)
    {


        try
        {
            if( refreshTokenService.findByToken(refreshToken.getRefreshToken()).isPresent())
            {


                RefreshToken refreshToken1=refreshTokenService.findByToken(refreshToken.getRefreshToken()).get();

                if(refreshTokenService.verifyExpiration(refreshToken1)!=null)
                {

                    User user=refreshToken1.getUser();

                    UserDetails userDetails=service.loadUserByUsername(user.getUserName());

                    final String jwt = jwtTokenUtil.generateToken(userDetails);
                    UserToken userToken=new UserToken(userDetails.getUsername(),jwt,refreshToken.getRefreshToken());

                    return new StatusBean(1,"success",userToken);

                }

                else
                {
                    return new StatusBean(0,"This RefreshToken Has Expired",refreshToken);
                }


            }

            else
            {
                return new StatusBean(0,"Failed RefreshToken Not Found", null);
            }


        }
        catch (Exception e)
        {
            return new StatusBean(0,"failed", e.getMessage());
        }


    }

    @PostMapping("/signoff")
    public StatusBean signoff(@RequestHeader ("Authorization") String token,@RequestHeader ("RefreshToken") String refreshtoken){


           token=token.substring(7);
           Date date=jwtTokenUtil.extractExpiration(token);

               if(date==null)
               { return new StatusBean(0,"access token already Expired",null); }

               else
               {
                   Optional<RefreshToken> refreshToken=refreshTokenService.findByToken(refreshtoken);
                   if(refreshToken.isPresent())
                   {   refreshTokenService.delete(refreshToken.get().getId());
                       GlobalToken globalToken=new GlobalToken();
                       globalToken.setToken(token);
                       LocalDateTime now = LocalDateTime.now().plusMinutes(16);
                       globalToken.setDate(now);
                       GlobalCache.add(globalToken);


                       return new StatusBean(1,"Logged out Successfully",null);

                   }
                   else
                   {

                       return new StatusBean(0,"Refresh token invalid",null);
                   }
//
               }
           }

    @GetMapping("/blacklisted")
    public Object blacklisted()
    {

        return GlobalCache.token;

    }


    @PostMapping("/create")
    public StatusBean createUser(@RequestBody UserCreateBean user) {


        try {

            if(user.getUserName()==null ||user.getUserName().isEmpty())
            {
                return new StatusBean(0,"provide username",null);
            }
            if(user.getPassword()==null || user.getPassword().isEmpty())
            {
                return new StatusBean(0,"provide password",null);
            }
            if(user.getRoles()==null || user.getRoles().isEmpty())
            {
                return new StatusBean(0,"provide a Role to the for user",null);
            }

            User user1=new User();
            user1.setUserName(user.getUserName());
            user1.setActive(true);
            user1.setRoles(user.getRoles());
            user1.setPassword(user.getPassword());
            service.create(user1);

            return new StatusBean(1,"User Created",user.getUserName());

        }

        catch (Exception e)
        {
            return new StatusBean(0,"Unable to create",user.getUserName());
        }


    }


    @GetMapping("/")
    public StatusBean hello(Principal principal) {

        if(principal.getName().isEmpty())
        {
            return new StatusBean(0,"Please Login in to Continue",null);

        }

            return new StatusBean(0,"Welcome "+principal.getName(),null);

    }

    @GetMapping("/cartproducts")
    public StatusBean cart(Principal principal) {

        if(principal.getName().isEmpty())
        {
            return new StatusBean(0,"Please Login in to Continue",null);

        }
        Set<Cart> pending=service.userCart(principal.getName());
        if(pending.isEmpty())
        {
            return new StatusBean(1,"success","Cart is Empty");
        }

        return new StatusBean(1,"success",pending);

    }

    @GetMapping("/cartapproved")
    public StatusBean cartApproved(Principal principal) {

        if(principal.getName().isEmpty())
        {
            return new StatusBean(0,"Please Login in to Continue",null);

        }

        Set<Cart> approved=service.approved(principal.getName());
            if(approved.isEmpty())
            {
                return new StatusBean(1,"success","No permitted items found");
            }
        return new StatusBean(1,"success",approved);

    }

    @GetMapping("/cartdiscarded")
    public StatusBean cartDiscarded(Principal principal) {

        if(principal.getName().isEmpty())
        {
            return new StatusBean(0,"Please Login in to Continue",null);

        }
        Set<Cart> discarded=service.discarded(principal.getName());

        if(discarded.isEmpty())
        {
            return new StatusBean(1,"success","No discarded items found");
        }


        return new StatusBean(1,"success",discarded);

    }

    @GetMapping("/user/{id}")
    public StatusBean cart(@PathVariable Integer id) {

        try
        {
           Boolean b=service.checkUserExists(id);

            if(Boolean.TRUE.equals(b))
           {
               UserCartDetails user= service.userDetailsWithCart(id);

               if(user.getCart().isEmpty())
               {
                   return new StatusBean(0,"user doesnt have any requested products cart",null);
               }

               return new StatusBean(1,"success",user);

           }

           else
           {
               return new StatusBean(0,"Customer not found",null);

           }


        }
        catch (Exception e)
        {
            return new StatusBean(0,e.toString(), null);
        }



    }





}

