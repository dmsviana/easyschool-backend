package br.edu.ifpb.ads.easyschool.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.ifpb.ads.easyschool.model.Fee;
import br.edu.ifpb.ads.easyschool.model.types.PaymentStatus;

public interface FeeRepository extends JpaRepository<Fee, Long> {


    @Query("SELECT f FROM Fee f WHERE f.student.id = :studentId")
    List<Fee> findFeesByStudentId(Long studentId);

    @Query("SELECT f FROM Fee f WHERE f.dueDate < :dueDate")
    List<Fee> findOverdueFees(LocalDate dueDate);

    @Query("SELECT COUNT(f) FROM Fee f WHERE f.paymentStatus = 'PENDING'")
    Long countByPaymentStatus(PaymentStatus paymentStatus);

}
