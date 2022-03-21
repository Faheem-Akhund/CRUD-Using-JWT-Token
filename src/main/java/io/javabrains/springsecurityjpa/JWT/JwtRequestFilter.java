package io.javabrains.springsecurityjpa.JWT;

import com.sun.xml.internal.ws.handler.HandlerException;
import io.javabrains.springsecurityjpa.Beans.GlobalCache;
import io.javabrains.springsecurityjpa.Beans.GlobalToken;

//import io.javabrains.springsecurityjpa.Exceptions.CustomException;
import io.javabrains.springsecurityjpa.Exceptions.CustomException;
import io.javabrains.springsecurityjpa.User.MyUserDetailsService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.io.SerializablePermission;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {


        @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");


        String username = null;
        String jwt = null;

        try {


            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

                jwt = authorizationHeader.substring(7);



                for(GlobalToken globalToken: GlobalCache.token )
                {
                    if(LocalDateTime.now().isAfter(globalToken.getDate()))
                    {
                        GlobalCache.token.remove(globalToken);
                    }

                    if(jwt.equals(globalToken.getToken()))
                    {
                        throw new CustomException("Token Expired");

                    }

                }



                if(jwt.length()<100)
                {
                    throw new CustomException("Invalid token");
                }


                else
                {

                    Date date=jwtUtil.extractExpiration(jwt);

                    if(date==null)
                    {
                        throw new IOException("token expired by time");
                    }

                    else
                    {
                        username = jwtUtil.extractUsername(jwt);

                    }


                }

            }


            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
            {

                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);


                if (jwtUtil.validateToken(jwt, userDetails)) {

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }


            }

            chain.doFilter(request, response);


        }

        catch (CustomException e)
        {
            response.sendError(403,e.getMessage());
        }



        }




    }


