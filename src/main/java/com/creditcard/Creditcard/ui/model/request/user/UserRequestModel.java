package com.creditcard.Creditcard.ui.model.request.user;

import com.creditcard.Creditcard.entity.RoleEntity;
import lombok.Data;

import java.util.Collection;
import java.util.List;

@Data
public class UserRequestModel {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private List<AddressRequestModel> address;
    private AccountRequestModel accountDetails;
    private CreditCardRequestModel cardDetails;
}
