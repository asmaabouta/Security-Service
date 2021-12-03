package org.sid.securityservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    //on met cette annot pr ne pas serialiser le mdp en format json c comme ignore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY )
    private String password;
    //des que je charge ce utilisateur , j'ai les roles de ce user , donc il faut initialiser la collections avec new arrayList
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<AppRole> appRoles=new ArrayList<>();

}
