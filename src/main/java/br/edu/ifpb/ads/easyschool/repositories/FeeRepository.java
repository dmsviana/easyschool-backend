package br.edu.ifpb.ads.easyschool.repositories;

import br.edu.ifpb.ads.easyschool.model.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface FeeRepository extends JpaRepository<Fee, Long> {


    @Query("SELECT f FROM Fee f WHERE f.student.id = :studentId")
    List<Fee> findFeesByStudentId(Long studentId);

    @Query("SELECT f FROM Fee f WHERE f.dueDate < :dueDate")
    List<Fee> findOverdueFees(LocalDate dueDate);

}
