package com.creditcard.Creditcard.shared.customer;

import lombok.Data;

import java.util.List;
@Data
public class UserDto {
    private String userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private List<AddressDto> address;
    private CreditCardDto creditCardDetails;
    private AccountDto accountDetails;
}
