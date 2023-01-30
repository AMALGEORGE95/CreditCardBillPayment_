package com.creditcard.Creditcard.ui.controller;

import com.creditcard.Creditcard.entity.BillGenerationEntity;
import com.creditcard.Creditcard.entity.UserEntity;
import com.creditcard.Creditcard.exception.ClientSideException;
import com.creditcard.Creditcard.repository.UserRepo;
import com.creditcard.Creditcard.service.BillGenerationService;
import com.creditcard.Creditcard.service.UserService;
import com.creditcard.Creditcard.shared.customer.UserDto;
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

}
