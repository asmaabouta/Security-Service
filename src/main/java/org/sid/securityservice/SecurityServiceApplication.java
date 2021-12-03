package org.sid.securityservice;

import org.sid.securityservice.entities.AppRole;
import org.sid.securityservice.entities.AppUser;
import org.sid.securityservice.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class SecurityServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityServiceApplication.class, args);
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner start(AccountService accountService){
        return args -> {
        accountService.addNewRole(new AppRole(null,"USER"));
        accountService.addNewRole(new AppRole(null,"ADMIN"));
        accountService.addNewRole(new AppRole(null,"CUSTOMER-MANAGER"));
        accountService.addNewRole(new AppRole(null,"PRODUCT-MANAGER"));
        accountService.addNewRole(new AppRole(null,"BILLS-MANAGER"));


        accountService.addNewUser((new AppUser(null,"user1","1234",new ArrayList<>())));
        accountService.addNewUser((new AppUser(null,"admin","1234",new ArrayList<>())));
        accountService.addNewUser((new AppUser(null,"user2","1234",new ArrayList<>())));
        accountService.addNewUser((new AppUser(null,"user3","1234",new ArrayList<>())));
        accountService.addNewUser((new AppUser(null,"user4","1234",new ArrayList<>())));

        accountService.addRoleToUser("user1","USER");
        accountService.addRoleToUser("user2","CUSTOMER-MANAGER");
        accountService.addRoleToUser("user2","USER");
        accountService.addRoleToUser("admin","USER");
        accountService.addRoleToUser("admin","ADMIN");
        accountService.addRoleToUser("user3","USER");
        accountService.addRoleToUser("user3","PRODUCT-MANAGER");
        accountService.addRoleToUser("user4","USER");
        accountService.addRoleToUser("user4","BILLS-MANAGER");


        };
    }

}
