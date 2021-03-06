package org.sid.securityservice.repositories;

import org.sid.securityservice.entities.AppRole;
import org.sid.securityservice.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {
    AppRole findByRolename(String roleName);
}
