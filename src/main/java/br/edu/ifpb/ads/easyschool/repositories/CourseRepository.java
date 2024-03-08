package br.edu.ifpb.ads.easyschool.repositories;

import br.edu.ifpb.ads.easyschool.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByName(String name);

}
