package org.sid.securityservice.service;

import org.sid.securityservice.entities.AppRole;
import org.sid.securityservice.entities.AppUser;
import org.sid.securityservice.repositories.AppRoleRepository;
import org.sid.securityservice.repositories.AppUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;

    public AccountServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
    }

    @Override
    public AppUser addNewUser(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    @Override
    public AppRole addNewRole(AppRole appRole) {
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
    AppUser appUser=appUserRepository.findByUsername(userName);
    AppRole appRole=appRoleRepository.findByRolename(roleName);
    appUser.getAppRoles().add(appRole);
    }

    @Override
    public AppUser loadUserByUserName(String userName)
    {
        return appUserRepository.findByUsername(userName);
    }

    @Override
    public List<AppUser> listUsers() {
        return appUserRepository.findAll();
    }
}
