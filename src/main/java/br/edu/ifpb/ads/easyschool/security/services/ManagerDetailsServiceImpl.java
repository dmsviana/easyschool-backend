package br.edu.ifpb.ads.easyschool.security.services;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ads.easyschool.repositories.ManagerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManagerDetailsServiceImpl implements UserDetailsService {

    private final ManagerRepository managerRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final var student = managerRepository.findByUsername(username)
                .orElseThrow( () -> new UsernameNotFoundException("Manager not found with username: " + username));

        final var authorityListAdmin = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");

        return UserDetailsImpl.build(student, authorityListAdmin);
    }
}
