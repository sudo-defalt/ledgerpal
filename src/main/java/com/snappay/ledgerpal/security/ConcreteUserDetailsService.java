package com.snappay.ledgerpal.security;

import com.snappay.ledgerpal.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConcreteUserDetailsService implements UserDetailsService {
    private UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findByUsername(username)
                .map(ConcreteUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("no user found with given username"));
    }
}
