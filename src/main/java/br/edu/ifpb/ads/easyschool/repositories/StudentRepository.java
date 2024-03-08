package br.edu.ifpb.ads.easyschool.repositories;

import br.edu.ifpb.ads.easyschool.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    Optional<Student> findByUsername(String username);

    
}
