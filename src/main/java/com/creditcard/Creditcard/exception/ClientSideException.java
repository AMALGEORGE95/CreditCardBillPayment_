package com.creditcard.Creditcard.exception;

import com.creditcard.Creditcard.ui.model.response.user.Messages;

public class ClientSideException extends RuntimeException{
    public ClientSideException(String message) {
        super(String.valueOf(message));
    }

    public ClientSideException(Messages noBillsFound) {
        super(String.valueOf(noBillsFound));
    }
}
