package com.creditcard.Creditcard.service.serviceImpl;

public class InsufficientBalanceEmailBuilder {
    public String buildInsufficientBalanceMail(double account_balance,Long outStandingAmount,Long remaining_credit){

        return "<b><h4>Dear Customer...</h5>"
                +"<br><b><h1>Credit Card Transaction Failed </h1></b>"
                + "<br><h2><b>Reason: Insufficient Balance</b></h2>"
                +"<br><h4><b>Account balance: "+account_balance+"</h4>"
                +"<h4></b>Credit card OutStandingAmount:"+outStandingAmount+"</h4>"
                +"<h4><b>Credit card remaining credit:</b>"+remaining_credit+"</h4>"
                +"<h3><b>Thank you for using our CreditCard services</b></h3>";



    }
}
