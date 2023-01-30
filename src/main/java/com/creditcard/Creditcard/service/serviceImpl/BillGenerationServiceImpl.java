package com.creditcard.Creditcard.service.serviceImpl;

import com.creditcard.Creditcard.entity.BillGenerationEntity;
import com.creditcard.Creditcard.entity.UserEntity;
import com.creditcard.Creditcard.repository.BillGenerationRepo;
import com.creditcard.Creditcard.service.BillGenerationService;
import com.creditcard.Creditcard.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class BillGenerationServiceImpl implements BillGenerationService {
    @Autowired
    BillGenerationRepo billGenerationRepo;
    @Autowired
    EmailService emailService;
    public BillGenerationEntity generateBill(UserEntity user, double amount, String email){
        BillGenerationEntity billGeneration = new BillGenerationEntity();
        billGeneration.setDueAmount(amount);
        billGeneration.setUserId(user.getId());
        billGeneration.setDueOn(LocalDateTime.now().plusDays(20));
        billGeneration.setStatus("pending");
        billGenerationRepo.save(billGeneration);
        BillGenerationMailBuilder emailBuilder = new BillGenerationMailBuilder();
        emailService.send(email, emailBuilder.buildGenerationContent
                ( billGeneration.getBillId(),
                        billGeneration.getDueOn(), billGeneration.getDueAmount()));
        return billGeneration;

    }

}
