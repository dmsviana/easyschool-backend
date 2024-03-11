package br.edu.ifpb.ads.easyschool.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import br.edu.ifpb.ads.easyschool.controllers.dtos.response.DashboardDataDTO;
import br.edu.ifpb.ads.easyschool.model.Fee;
import br.edu.ifpb.ads.easyschool.model.types.PaymentStatus;
import br.edu.ifpb.ads.easyschool.repositories.CourseRepository;
import br.edu.ifpb.ads.easyschool.repositories.FeeRepository;
import br.edu.ifpb.ads.easyschool.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final StudentRepository studentRepository;
    private final FeeRepository feeRepository;
    private final CourseRepository courseRepository;


    public DashboardDataDTO getDashboardData() {
        Long totalStudents =  getTotalStudents();
        BigDecimal totalFees = findTotalFeePrice();
        Long pendingFeesCount = getPendingFeesCount();
        Long totalCourses = getTotalCourses();

        
        return new DashboardDataDTO(totalStudents, totalCourses, totalFees, pendingFeesCount);
    }


    private Long getTotalStudents() {
        return studentRepository.count();
    }

    public BigDecimal findTotalFeePrice() {
        return feeRepository.findAll().stream()
                .map(Fee::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Long getPendingFeesCount() {
        return feeRepository.countByPaymentStatus(PaymentStatus.PENDING);
    }

    public Long getTotalCourses(){
        return courseRepository.count();
    }
    

}
