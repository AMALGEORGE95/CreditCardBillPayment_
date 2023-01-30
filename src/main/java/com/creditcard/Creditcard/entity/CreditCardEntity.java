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
@Table(name = "credit_card")
public class CreditCardEntity {
    @Id
    @SequenceGenerator(name = "cardIdGen", allocationSize = 1, initialValue = 1000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cardIdGen")
    private Long cardId;

    @Column(name = "cardNumber",nullable = false)
    private Long cardNumber;

    @Column(name = "bankName",nullable = false)
    private String bankName;

    @Column(name = "cardType",nullable = false)
    private String cardType;

    @Column(name = "cardName",nullable = false)
    private String cardName;

    @Column(name = "cardLimit",nullable = false)
    private Long cardLimit;

    @Column(name = "outStandingAmount",nullable = false)
    private Long outStandingAmount;

    @Column(name = "remainingCredit",nullable = false)
    private Long remainingCredit;

    @Column(name = "card_expiry",nullable = false)
    private LocalDate cardExpiry;

    @Column(name = "cvv",nullable = false)
    private int cvv;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity userDetails;
}
