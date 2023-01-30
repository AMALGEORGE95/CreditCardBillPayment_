package com.creditcard.Creditcard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment_records")
public class PaymentEntity__ {
    @Id
    @SequenceGenerator(name = "payment_seq", initialValue =7000, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_seq")
    @Column(name = "payment_id")
    private Long paymentId;
    private String Message;
    private double amount;
    private LocalDate OnDate;
    private Long outStandingAmount;
    private Long remaining_credit;
    private Long account_balance;
    private Long userId;
}
