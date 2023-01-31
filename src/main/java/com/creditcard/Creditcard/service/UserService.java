package com.creditcard.Creditcard.service;

import com.creditcard.Creditcard.entity.BillGenerationEntity;
import com.creditcard.Creditcard.entity.PaymentEntity__;
import com.creditcard.Creditcard.exception.ClientSideException;
import com.creditcard.Creditcard.shared.customer.AddressDto;
import com.creditcard.Creditcard.shared.customer.UserDto;
import com.creditcard.Creditcard.ui.model.response.user.UserResponseModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService  extends UserDetailsService {
    public UserResponseModel createUser(UserDto user);
    UserDto getUser(String email);

    void hasAccess(String userId) throws ClientSideException;

    List<UserDto> getUsers(int page, int limit);

    PaymentEntity__ billPayment(String userId, Long billId);

    List<PaymentEntity__> fetchPaymentRecords(String userId);

    List<BillGenerationEntity> fetchBillsToPay(String userId);


    UserDto updateUser(String userId, UserDto user);

    AddressDto updateAddress(String userId, AddressDto addressDto, Long addressId);
}
