package com.creditcard.Creditcard.shared.customer;

import com.creditcard.Creditcard.entity.UserEntity;
import lombok.Data;

@Data
public class AccountDto {
    private Long accountNumber;
    private String accountName;
    private double balance;
    private String type;
    private UserDto userDetails;
}
