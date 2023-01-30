package com.creditcard.Creditcard.shared.customer;

import com.creditcard.Creditcard.entity.UserEntity;
import lombok.Data;

import java.time.LocalDate;
@Data
public class CreditCardDto {
    private  Long cardId;
    private Long cardNumber;
    private String bankName;
    private String cardType;
    private String cardName;
    private Long cardLimit;
    private Long outStandingAmount;
    private Long remainingCredit;
    private LocalDate cardExpiry;
    private int cvv;
    private UserDto userDetails;
}
