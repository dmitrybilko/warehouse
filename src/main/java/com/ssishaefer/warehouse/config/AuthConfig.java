package com.ssishaefer.warehouse.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class AuthConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity security) throws Exception {
        security
            .authorizeRequests()
            .antMatchers("/")
            .permitAll()
            .anyRequest()
            .fullyAuthenticated()
            .and()
            .httpBasic()
            .and()
            .csrf()
            .disable();
    }
}
