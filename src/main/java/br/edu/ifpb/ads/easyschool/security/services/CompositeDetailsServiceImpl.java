package br.edu.ifpb.ads.easyschool.security.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompositeDetailsServiceImpl implements UserDetailsService {

    private final List<UserDetailsService> userDetailsServiceList;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        for (UserDetailsService delegate : this.userDetailsServiceList) {
            try {
                return delegate.loadUserByUsername(username);
            } catch (UsernameNotFoundException ignore) {
            }
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
