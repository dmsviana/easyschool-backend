package br.edu.ifpb.ads.easyschool.services;


import br.edu.ifpb.ads.easyschool.model.Fee;
import br.edu.ifpb.ads.easyschool.model.Student;
import br.edu.ifpb.ads.easyschool.model.types.PaymentStatus;
import br.edu.ifpb.ads.easyschool.repositories.FeeRepository;
import br.edu.ifpb.ads.easyschool.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FeeService {

    private final FeeRepository feeRepository;
    private final StudentRepository studentRepository;


    public FeeService(FeeRepository feeRepository, StudentRepository studentRepository) {
        this.feeRepository = feeRepository;
        this.studentRepository = studentRepository;
    }

    public void generateFees(Student student){

        List<Fee> fees = new ArrayList<>();
        for (int i = 1; i <= 5; i++){
            Fee fee = new Fee();
            fee.setPrice(new BigDecimal(125));
            fee.setDueDate(LocalDate.now().plusMonths(i));
            fee.setPaymentStatus(PaymentStatus.PENDING);
            fee.setStudent(student);
            fees.add(fee);
        }

        feeRepository.saveAll(fees);
    }

    public void updateStatus(Fee fee){
        fee.setPaymentStatus(PaymentStatus.OVERDUE);
    }

    public void verifyOverdueFees(){
        List<Fee> overdueFees = feeRepository.findOverdueFees(LocalDate.now());
        overdueFees.forEach(this::updateStatus);

        feeRepository.saveAll(overdueFees);
    }
}
