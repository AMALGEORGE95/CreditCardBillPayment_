package com.creditcard.Creditcard.exception;


import com.creditcard.Creditcard.ui.model.request.user.AddressRequestModel;
import com.creditcard.Creditcard.ui.model.request.user.UserRequestModel;

/**
 * Class is used to validate incoming json.
 */
public class Validation {

    /**
     * Method is used to check if the fields are empty.
     * @param userDetails UserDetailsRequestModel
     * @return Boolean
     */
    public Boolean checkFields(UserRequestModel userDetails) {

        if (userDetails.getFirstName().isEmpty()) return false;
        if (userDetails.getLastName().isEmpty()) return false;
        if (userDetails.getEmail().isEmpty()) return false;
        if (userDetails.getPassword().isEmpty()) return false;
        if (userDetails.getPhoneNumber().isEmpty()) return false;
        if (userDetails.getAddress().isEmpty()) return false;

        return true;
    }
}
