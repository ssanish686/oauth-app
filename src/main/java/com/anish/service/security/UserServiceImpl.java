package com.anish.service.security;

import com.anish.dao.AuthorityRepository;
import com.anish.dao.UserDetailsRepository;
import com.anish.entity.Authority;
import com.anish.entity.UserAuthorities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serial;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("userName : {}", userName);
        com.anish.entity.UserDetails userDetails = userDetailsRepository.findByUserName(userName.toLowerCase()).orElseThrow(() -> new UsernameNotFoundException("User Not Found."));
        List<SimpleGrantedAuthority> authorities = userDetails.getUserAuthorities().stream().map(ua ->
            new SimpleGrantedAuthority(ua.getAuthority().getName())).toList();
        return new CustomUser(userName, userDetails.getPassword(),
                authorities);
    }

    public static class CustomUser extends User {

        @Serial
        private static final long serialVersionUID = 1L;

        public CustomUser(String userName, String password, Collection<SimpleGrantedAuthority> grantedAuthoritiesList) {
            super(userName, password, grantedAuthoritiesList);
        }

        @Override
        public String toString() {
            return "CustomUser [getAuthorities()=" + getAuthorities() + ", getPassword()=" + getPassword()
                    + ", getUsername()=" + getUsername() + ", isEnabled()=" + isEnabled() + ", isAccountNonExpired()="
                    + isAccountNonExpired() + ", isAccountNonLocked()=" + isAccountNonLocked()
                    + ", isCredentialsNonExpired()=" + isCredentialsNonExpired() + ", hashCode()=" + hashCode()
                    + ", toString()=" + super.toString() + ", getClass()=" + getClass() + "]";
        }


    }
}
