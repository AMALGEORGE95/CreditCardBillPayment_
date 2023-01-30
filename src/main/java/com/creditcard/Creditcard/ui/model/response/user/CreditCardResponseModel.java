package com.creditcard.Creditcard.ui.model.response.user;

import lombok.Data;

@Data
public class CreditCardResponseModel {
    private Long cardLimit;
    private Long outstandingAmount;
    private Long remainingCredit;
}
