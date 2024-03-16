package br.edu.ifpb.ads.easyschool.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.ads.easyschool.model.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    Optional<Lesson> findBySubject(String subject);
    
    
}
