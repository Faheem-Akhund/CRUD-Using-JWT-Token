package io.javabrains.springsecurityjpa;


import io.javabrains.springsecurityjpa.JWT.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
    {
        try {
            auth.userDetailsService(userDetailsService);
        }
        catch (Exception e)
        {

        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http
                .csrf().disable().authorizeRequests()
                .antMatchers("/user/{id}").hasRole("ADMIN")
                .antMatchers("/user/{id}/product").hasRole("ADMIN")

                .antMatchers("/product").hasRole("ADMIN")
                .antMatchers("/products").hasAnyRole("USER","ADMIN")
                .antMatchers("/logout").hasAnyRole("USER","ADMIN")
                .antMatchers("/signoff").permitAll()


                .antMatchers("/cart").hasRole("USER")
                .antMatchers("/cartproducts").hasRole("USER")
                .antMatchers("/cartdiscarded").hasRole("USER")
                .antMatchers("/cartapproved").hasRole("USER")



                .antMatchers("/").permitAll()
                .antMatchers("/signin").permitAll()
                .antMatchers("/create").permitAll().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);


    }


    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
