package br.edu.ifpb.ads.easyschool.services.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ads.easyschool.repositories.StudentRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private final StudentRepository studentRepository;

  public UserDetailsServiceImpl(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return studentRepository.findByUsername(username)
        .map(user -> new UserAuthenticated(user))
        .orElseThrow(
            () -> new UsernameNotFoundException("User Not Found with username: " + username));
  }

}
