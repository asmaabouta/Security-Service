package org.sid.securityservice.configuration;

import org.sid.securityservice.entities.AppUser;
import org.sid.securityservice.filters.JwtAuthenticationFilter;
import org.sid.securityservice.service.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;

// cette classe pr redéfinir la config de spring security
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private AccountService accountService;
    //injection de dep
    public SecurityConfig(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       //ici on va spécifier qui sont l users qui ont le droit d y acceder
        //cette methode implique quand un user va s'authetifier , use this methodepr chercher l 'user a partir de la methode loadUserByUsername
        auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                AppUser appUser =accountService.loadUserByUserName(username);
                Collection<GrantedAuthority> authorities=new ArrayList<>();
                appUser.getAppRoles().forEach(r->{
                    authorities.add(new SimpleGrantedAuthority(r.getRolename()));
                });
                return new User(appUser.getUsername(),appUser.getPassword(),authorities);
            }
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // ici on va spécifier les droit d acces
         http.csrf().disable();
         http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //malgres qu'on desactive le csrf , il bloque la bd par des frams , pr ce fait on fait :
        //comme ca on dis , tu desactive la protection contre les frams
        http.headers().frameOptions().disable();
        http.authorizeRequests().antMatchers("/h2-console/**").permitAll();

        //cette cmd permet a dire s'il ya un user qui tente d'acceder a une ressource dont laquel n'a pas le droit , on affiche la page login
        //http.formLogin();
        // pr autoriser a tout les requetes
       // http.authorizeRequests().anyRequest().permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        //integrer le filter (l'installer)
        http.addFilter(new JwtAuthenticationFilter(authenticationManagerBean()));
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws  Exception{
        return super.authenticationManagerBean();
    }
}
