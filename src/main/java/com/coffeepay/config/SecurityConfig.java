package com.coffeepay.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static util.DataGeneral.ADMIN;
import static util.DataGeneral.CUSTOMER;
import static util.DataGeneral.LENGTH_ENCODER;
import static util.DataGeneral.MANAGER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    protected BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(LENGTH_ENCODER);
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                //Доступ только для пользователей с ролью Администратор
                .antMatchers("/orders/**").hasAnyAuthority(CUSTOMER)
                .antMatchers("/addresses/**").hasAnyAuthority(ADMIN,MANAGER)
                .antMatchers("/discounts/**").hasAnyAuthority(ADMIN,MANAGER)
                .antMatchers("/machines/**").hasAnyAuthority(ADMIN,MANAGER)
                .antMatchers("/modelsMachine/**").hasAnyAuthority(ADMIN,MANAGER)
                .antMatchers("/products/**").hasAnyAuthority(ADMIN,MANAGER)
                .antMatchers("/roles/**").hasAuthority(ADMIN)
                .antMatchers("/typePayments/**").hasAuthority(ADMIN)
                .antMatchers("/purchases").hasAnyAuthority(MANAGER)
                .antMatchers("/purchases/**").hasAuthority(ADMIN)
                //Доступ разрешен всем пользователей
                .antMatchers("/css/**").permitAll()
                .antMatchers("/language/**").permitAll()
                .antMatchers("/", "/customer/new", "/customer").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .defaultSuccessUrl("/api")
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll();
    }
}
