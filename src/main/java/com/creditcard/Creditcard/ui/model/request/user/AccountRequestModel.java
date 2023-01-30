package com.creditcard.Creditcard.ui.model.request.user;

import lombok.Data;


@Data
public class AccountRequestModel {
    private String accountName;
    private double balance;
    private String type;
}
