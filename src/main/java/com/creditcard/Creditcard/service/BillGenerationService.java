package com.creditcard.Creditcard.service;

import com.creditcard.Creditcard.entity.BillGenerationEntity;
import com.creditcard.Creditcard.entity.UserEntity;

public interface BillGenerationService {
    BillGenerationEntity generateBill(UserEntity user, double amount, String email);
}
