package com.creditcard.Creditcard.ui.model.request.user;

import lombok.Data;

@Data
public class UserBasicDetailsRequestModel {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
}
