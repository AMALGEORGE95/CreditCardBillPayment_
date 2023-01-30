package com.creditcard.Creditcard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private long Id;
    @Column(nullable = false)
    private String userId;
    @Column(name = "first_name",nullable = false,length = 50)
    private String firstName;
    @Column(name = "last_name",nullable = false,length = 50)
    private String lastName;
    @Column(name = "phone_number",nullable = false,length = 15)
    private String phoneNumber;
    @Column(name = "email_Id",nullable = false)
    private String email;
    @Column(name = "encrypted_Password",nullable = false)
    private String password;
    @Column(nullable = false)
    private Boolean emailVerificationStatus = false;
    @OneToMany(mappedBy = "userDetails",cascade = CascadeType.ALL)
    private List<AddressEntity> address;

    @OneToOne(mappedBy = "userDetails",cascade = CascadeType.ALL)
    private CreditCardEntity  creditCardDetails;

    @OneToOne(mappedBy = "userDetails",cascade = CascadeType.ALL)
    private AccountEntity  accountDetails;

    @ManyToMany(cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    @JoinTable(name="user_roles",
            joinColumns = @JoinColumn(name="user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    private Collection<RoleEntity> roles;
//    private String emailVerificationToken;

}
