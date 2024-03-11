package br.edu.ifpb.ads.easyschool.services;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifpb.ads.easyschool.model.Fee;
import br.edu.ifpb.ads.easyschool.model.Student;
import br.edu.ifpb.ads.easyschool.model.types.PaymentStatus;
import br.edu.ifpb.ads.easyschool.repositories.FeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FeeService {

    private final FeeRepository feeRepository;

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

    public List<Fee> findAll(){
        return feeRepository.findAll();
    }

    public void updateStatus(Fee fee){
        fee.setPaymentStatus(PaymentStatus.OVERDUE);
    }

    public void verifyOverdueFees(){
        List<Fee> overdueFees = feeRepository.findOverdueFees(LocalDate.now());
        overdueFees.forEach(this::updateStatus);

        feeRepository.saveAll(overdueFees);
    }

    public BigDecimal findTotalFeePrice() {
        return feeRepository.findAll().stream()
                .map(Fee::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Long countByPaymentStatus(PaymentStatus pending) {
        return feeRepository.findAll().stream()
                .filter(fee -> fee.getPaymentStatus().equals(pending))
                .count();
    }
}
