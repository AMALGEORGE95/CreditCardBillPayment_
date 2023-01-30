package com.creditcard.Creditcard.service;

import com.creditcard.Creditcard.entity.BillGenerationEntity;
import com.creditcard.Creditcard.entity.UserEntity;
import com.creditcard.Creditcard.ui.model.response.user.CreditCardResponseModel;

public interface BillGenerationService {
    BillGenerationEntity generateBill(UserEntity user, double amount, String email);

    CreditCardResponseModel increaseLimit(UserEntity user, Long hike, String email);
}
