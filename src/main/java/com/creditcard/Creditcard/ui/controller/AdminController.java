package com.creditcard.Creditcard.ui.controller;

import com.creditcard.Creditcard.entity.BillGenerationEntity;
import com.creditcard.Creditcard.entity.UserEntity;
import com.creditcard.Creditcard.exception.ClientSideException;
import com.creditcard.Creditcard.repository.UserRepo;
import com.creditcard.Creditcard.service.BillGenerationService;
import com.creditcard.Creditcard.service.UserService;
import com.creditcard.Creditcard.shared.customer.UserDto;
import com.creditcard.Creditcard.ui.model.response.user.CreditCardResponseModel;
import com.creditcard.Creditcard.ui.model.response.user.Messages;
import com.creditcard.Creditcard.ui.model.response.user.UserResponseModel;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "admin")
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepo userRepo;
    @Autowired
    BillGenerationService billGenerationService;

    /**
     * Method for the admin to view all the users.
     * @param page
     * @param limit
     * @return List<UserResponseModel>
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @Secured("ROLE_ADMIN")
    @GetMapping(path="/users/fetch")
    public List<UserResponseModel> getUsers(@RequestParam(value = "page",defaultValue = "0") int page,
                                              @RequestParam(value = "limit",defaultValue = "2") int limit){
        List<UserResponseModel> returnValue = new ArrayList<>();
        List<UserDto> users = userService.getUsers(page,limit);

        for (UserDto userDto : users){
            UserResponseModel userRest = new ModelMapper().map(userDto,UserResponseModel.class);
            returnValue.add(userRest);
        }
        return returnValue;
    }

    /**
     * Method for the admin to generate bill for each user.
     * @param email
     * @param amount
     * @return BillGenerationEntity
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @Secured("ROLE_ADMIN")
    @PostMapping(path ="/user/billgeneration/{email}/{amount}")
    public BillGenerationEntity billGeneration(@PathVariable(value = "email") String email,@PathVariable(value = "amount") double amount){
        UserEntity user=userRepo.findByEmail(email);
        if (user==null){
            throw new ClientSideException(Messages.USER_DOES_NOT_EXIST);
        }
        BillGenerationEntity billGeneration=billGenerationService.generateBill(user,amount,email);
        return billGeneration;

    }

    /**
     * Method for the admin to give hike in the credit card limit if the user is eligible(done more than 10 payments)
     * @param email
     * @param hike
     * @return CreditCardResponseModel
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @Secured("ROLE_ADMIN")
    @PostMapping(path = "/user/limitIncrease/{email}/{hike}")
    public CreditCardResponseModel limitIncrease(@PathVariable(value = "email") String email, @PathVariable(value = "hike") Long hike){
        UserEntity user=userRepo.findByEmail(email);
        if (user==null){
            throw new ClientSideException(Messages.USER_DOES_NOT_EXIST);
        }
        return billGenerationService.increaseLimit(user,hike,email);
    }

}
