package com.creditcard.Creditcard.service.serviceImpl;

import com.creditcard.Creditcard.entity.BillGenerationEntity;
import com.creditcard.Creditcard.entity.CreditCardEntity;
import com.creditcard.Creditcard.entity.PaymentEntity__;
import com.creditcard.Creditcard.entity.UserEntity;
import com.creditcard.Creditcard.exception.ClientSideException;
import com.creditcard.Creditcard.repository.BillGenerationRepo;
import com.creditcard.Creditcard.repository.CreditCardRepository;
import com.creditcard.Creditcard.repository.PaymentRepo;
import com.creditcard.Creditcard.service.BillGenerationService;
import com.creditcard.Creditcard.service.EmailService;
import com.creditcard.Creditcard.ui.model.response.user.CreditCardResponseModel;
import com.creditcard.Creditcard.ui.model.response.user.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BillGenerationServiceImpl implements BillGenerationService {
    @Autowired
    BillGenerationRepo billGenerationRepo;
    @Autowired
    EmailService emailService;
    @Autowired
    PaymentRepo paymentRepo;
    @Autowired
    CreditCardRepository cardRepository;
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

    @Override
    public CreditCardResponseModel increaseLimit(UserEntity user, Long hike, String email) throws ClientSideException{
        CreditCardEntity creditCard= cardRepository.findByUserId(user.getId());
        Long currentLimit=creditCard.getCardLimit();
        List<PaymentEntity__> payments=paymentRepo.fetchUserRecords(user.getId());
        if (payments.size()>10) {
            creditCard.setCardLimit(currentLimit + hike);
            creditCard.setRemainingCredit(creditCard.getCardLimit()-creditCard.getOutStandingAmount());
            cardRepository.save(creditCard);
        }
        else {
            throw new ClientSideException(Messages.NOT_ELGIBLE);
        }
        LimitIncreaseEmailBuilder emailBuilder = new LimitIncreaseEmailBuilder();
        emailService.send(email,emailBuilder.buildLimitIncreaseMail(creditCard.getCardLimit()));
        CreditCardResponseModel creditCardResponseModel=new CreditCardResponseModel();
        creditCardResponseModel.setCardLimit(creditCard.getCardLimit());
        creditCardResponseModel.setRemainingCredit(creditCard.getRemainingCredit());
        creditCardResponseModel.setOutstandingAmount(creditCard.getOutStandingAmount());
        return creditCardResponseModel;
    }

}
