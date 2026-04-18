package org.atexplorer.TrainerClientManager.security;

import org.atexplorer.TrainerClientManager.entity.AppUser;
import org.atexplorer.TrainerClientManager.repository.AppUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public UserDetailsServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user with: " + username + " found!"));

        return User.withUsername(appUser.getUsername()).password(appUser.getPassword()).authorities(appUser.getAuthority()).build();
    }
}
