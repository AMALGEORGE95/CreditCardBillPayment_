package com.creditcard.Creditcard.ui.controller;
import com.creditcard.Creditcard.entity.BillGenerationEntity;
import com.creditcard.Creditcard.entity.PaymentEntity__;
import com.creditcard.Creditcard.exception.ClientSideException;
import com.creditcard.Creditcard.service.UserService;
import com.creditcard.Creditcard.shared.customer.AddressDto;
import com.creditcard.Creditcard.shared.customer.UserDto;
import com.creditcard.Creditcard.ui.model.request.user.AddressRequestModel;
import com.creditcard.Creditcard.ui.model.request.user.UserBasicDetailsRequestModel;
import com.creditcard.Creditcard.ui.model.request.user.UserRequestModel;
import com.creditcard.Creditcard.ui.model.response.user.AddressResponseModel;
import com.creditcard.Creditcard.ui.model.response.user.Messages;
import com.creditcard.Creditcard.ui.model.response.user.UserBasicDetailsResponseModel;
import com.creditcard.Creditcard.ui.model.response.user.UserResponseModel;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "home")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Method to create User with account and credit card details.
     * @param userDetails
     * @return userDto
     * @throws ClientSideException
     */
    @PostMapping(path="/user/create")
    public UserResponseModel createUser(@RequestBody UserRequestModel userDetails) throws ClientSideException {
        UserDto userDto = new ModelMapper().map(userDetails,UserDto.class);
        return userService.createUser(userDto);
    }

    /**
     * Method for the user to pay the bill which is pending
     * @param billId
     * @return PaymentEntity
     * @throws ClientSideException
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @Secured("ROLE_USER")
    @PostMapping(path = "/user/payment/{billId}")
    public PaymentEntity__ payment(@PathVariable(value = "billId") Long billId) throws ClientSideException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
            PaymentEntity__ payment = userService.billPayment(user.getUserId(),billId);
        return payment;

    }

    /**
     * method for the user to fetch all the payment records.
     * @return List<PaymentEntity>
     * @throws ClientSideException
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @Secured("ROLE_USER")
    @GetMapping(path = "/user/payment/fetchPaymentRecords")
    public List<PaymentEntity__> fetchRecords() throws ClientSideException{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        List<PaymentEntity__> records = userService.fetchPaymentRecords(user.getUserId());
        return records;
    }

    /**
     * Method for the user to fetch the payment records which is pending
     * @return List<BillGenerationEntity>
     * @throws ClientSideException
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @Secured("ROLE_USER")
    @GetMapping(path = "/user/bill/fetchBillsToPay")
    public  List<BillGenerationEntity> fetchBillsToPay() throws ClientSideException{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        List<BillGenerationEntity> billsToPay=userService.fetchBillsToPay(user.getUserId());
        if (billsToPay.isEmpty()){
            throw new ClientSideException(Messages.NO_BILLS_FOUND);
        }
        return billsToPay;
    }

    /**
     * Method for the user to update the basic details.
     * @param userDetails
     * @return UserBasicDetailsResponseModel
     * @throws ClientSideException
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @Secured("ROLE_USER")
    @PutMapping(path="/user/updateBasicDetails")
    public UserBasicDetailsResponseModel updateUser(@RequestBody UserBasicDetailsRequestModel userDetails) throws ClientSideException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        UserDto updatedUser = userService.updateUser(user.getUserId(),new ModelMapper().map(userDetails,UserDto.class));
        return new ModelMapper().map(updatedUser,UserBasicDetailsResponseModel.class);
    }

    /**
     * Method for the user to update the address
     * @param addressId
     * @param addressDetails
     * @return AddressResponseModel
     * @throws ClientSideException
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @Secured("ROLE_USER")
    @PutMapping(path="/user/address/update/{addressId}")
    public AddressResponseModel updateAddress(@PathVariable(value = "addressId") Long addressId,@RequestBody AddressRequestModel addressDetails) throws ClientSideException{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        AddressDto addressDto = new ModelMapper().map(addressDetails,AddressDto.class);
        AddressDto updatedAddress = userService.updateAddress(user.getUserId(),addressDto,addressId);
        return new ModelMapper().map(updatedAddress,AddressResponseModel.class);
    }
}
