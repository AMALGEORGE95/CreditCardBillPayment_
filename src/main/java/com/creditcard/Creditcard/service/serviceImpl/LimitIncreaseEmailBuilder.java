package com.creditcard.Creditcard.service.serviceImpl;

public class LimitIncreaseEmailBuilder {
    public String buildLimitIncreaseMail(Long limit){

        return "<b><h4>Dear Customer...</h5>"
                +"<br><b><h1>Congratulations </h1></b>"
                + "<br><h2><b>Your are eligible for credit card limit hike </b></h2>"
                +"<br><h4><b>Your new Limit: "+limit+"</h4>";
    }
}
