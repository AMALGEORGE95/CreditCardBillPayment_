package com.creditcard.Creditcard.service.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BillGenerationMailBuilder {
    public String buildGenerationContent(Long  billId, LocalDateTime DueOn, double dueAmount){

        return "<b><h4>Dear Customer...</h5>"
                +"<br><b><h1>Credit Card Bill Generated</h1></b>"
                + "<br><h2><b>Amount to be payed:"+dueAmount+"</b></h2>"
                +"<br><h4><b>bill Id :"+billId+"</h4>"
                +"<h4></b>Due On:"+DueOn+"</h4>"
                +"<h3><b>Thank you</b></h3>";



    }
}
