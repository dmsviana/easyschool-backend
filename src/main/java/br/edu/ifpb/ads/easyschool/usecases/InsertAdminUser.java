package br.edu.ifpb.ads.easyschool.usecases;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.edu.ifpb.ads.easyschool.model.Student;
import br.edu.ifpb.ads.easyschool.repositories.StudentRepository;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public record InsertAdminUser(StudentRepository studentRepository) {

    private static String ADMIN = "admin";

    public void insertAdminUser(){
        if(studentRepository.findByUsername(ADMIN).isEmpty()){
            log.info("Inserting admin user");
            var user = new Student();
            user.setName("admin");
            user.setEmail("admin@easyschool.com");
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            user.setUsername(ADMIN);
            user.setAdmin(true);
            user.setRegistration("12313213123");
            user.setPhoneNumber("83996586204");
            studentRepository.save(user);
        } else {
            log.info("Admin user already exists");
        }
    }
    
}
