package com.creditcard.Creditcard.ui.model.response.user;

import lombok.Data;

@Data
public class AddressResponseModel {
    private String city;
    private String country;
    private String streetName;
    private String postalCode;
    private String type;
}
