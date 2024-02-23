package br.edu.ifpb.ads.easyschool.repositories;

import java.util.Optional;

import br.edu.ifpb.ads.easyschool.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByName(String name);

}
