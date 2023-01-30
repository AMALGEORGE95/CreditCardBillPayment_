package com.creditcard.Creditcard.ui.model.request.user;

import com.creditcard.Creditcard.entity.UserEntity;
import lombok.Data;

import java.time.LocalDate;
@Data
public class CreditCardRequestModel {
    private Long cardNumber;
    private String bankName;
    private String cardType;
    private String cardName;
    private int cvv;
    private LocalDate cardExpiry;
    private Long cardLimit;
    private Long outstandingAmount;
    private Long remainingCredit;

}
