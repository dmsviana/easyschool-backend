package br.edu.ifpb.ads.easyschool.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.ads.easyschool.model.Lesson;
import br.edu.ifpb.ads.easyschool.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByUsername(String username);

    Optional<Teacher> findByEmail(String email);

    //List<Lesson> findLessonsByTeacherId(Long id);

    
    
}
