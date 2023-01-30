package com.creditcard.Creditcard.shared.customer;

import com.creditcard.Creditcard.entity.UserEntity;
import lombok.Data;

@Data
public class AddressDto {
    private long id;
    private String city;
    private String country;
    private String streetName;
    private String postalCode;
    private String type;
    private UserDto userDetails;
}
