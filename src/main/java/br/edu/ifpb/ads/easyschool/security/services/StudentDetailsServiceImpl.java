package br.edu.ifpb.ads.easyschool.security.services;


import br.edu.ifpb.ads.easyschool.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentDetailsServiceImpl implements UserDetailsService {

    private final StudentRepository studentRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final var student = studentRepository.findByUsername(username)
                .orElseThrow( () -> new UsernameNotFoundException("Student not found with username: " + username));

        final var authorityListUser = AuthorityUtils.createAuthorityList("ROLE_USER");
        return UserDetailsImpl.build(student, authorityListUser);
    }
}
