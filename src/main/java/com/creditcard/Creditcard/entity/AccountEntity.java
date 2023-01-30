package com.creditcard.Creditcard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import javax.persistence.*;
@Entity
@Table(name = "account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity {
    @Id
    @SequenceGenerator(name = "accNum_seq", initialValue = 1000000000, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accNum_seq")
    @Column(name = "ac_number")
    private Long accountNumber;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "account_balance")
    private double balance;

    @Column(name = "account_type")
    private String type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity userDetails;

}
