package org.sid.securityservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// cette classe pr redéfinir la config de spring security
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       //ici on va spécifier qui sont l users qui ont le droit d y acceder
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // ici on va spécifier les droit d acces
        http.csrf().disable();
        //malgres qu'on desactive le csrf , il bloque la bd par des frams , pr ce fait on fait :
        //comme ca on dis , tu desactive la protection contre les frams
        http.headers().frameOptions().disable();
        // pr autoriser a tout les requetes
        http.authorizeRequests().anyRequest().permitAll();
    }
}
