package br.edu.ifpb.ads.easyschool.usecases;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.edu.ifpb.ads.easyschool.model.Student;
import br.edu.ifpb.ads.easyschool.repositories.StudentRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public record InsertAdminUser(StudentRepository studentRepository) {
    

    private static final String ADMIN = "admin";

    public void insertAdminUser(){
        if (studentRepository.findByUsername(ADMIN).isEmpty()){
            log.debug("Administrator user not found, creating...");
            final var user = new Student();
            user.setName("Administrator");
            user.setUsername(ADMIN);
            user.setEmail("admin@admin.com");
            user.setRegistration("20240309");
            user.setPhoneNumber("83996586200");
            user.setAdmin(true);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            studentRepository.save(user);
        } else {
            log.info("insertAdminUser: admin user already exists.");
        }
    }
}
