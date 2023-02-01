package com.creditcard.Creditcard.ui.model.response.user;

/**
 * This Enum is used to store all the error messages used in the project.
 */
public enum Messages {

    NOT_ELGIBLE("Account not elgible for a hike"),
    RECORD_ALREADY_EXISTS("Verified account already exists for this email, please use a different email."),
    NO_RECORD_FOUND("Account with provided id not found"),
    NO_BILLS_FOUND("No pending Bills"),
    EMAIL_NOT_FOUND("Account with given Email not found,Please check email again!"),
    NO_ACCESS("You have no access to this account."),
    FAILED_DB_SAVE("Saving to Database failed! please try again."),
    INSUFFICIENT_BALANCE("Insufficient balance"),
    USER_DOES_NOT_EXIST("No user found");



    private String Message;

    /**
     * This constructor is used to call an enum with particular error name requested.
     * @param Message Name of the error requested.
     */
    Messages(String Message) {
        this.Message = Message;
    }

    /**
     * This method is used to get the enum with the error name.
     * @return String.
     */
    public String getMessage() {
        return Message;
    }

    /**
     * This method is used to set the enum with the error name.
     * @param Message New name for the enum.
     */
    public void setMessage(String Message) {
        this.Message = Message;
    }
}
