package com.creditcard.Creditcard.service.serviceImpl;

import com.creditcard.Creditcard.entity.*;
import com.creditcard.Creditcard.exception.ClientSideException;
import com.creditcard.Creditcard.repository.*;
import com.creditcard.Creditcard.service.EmailService;
import com.creditcard.Creditcard.service.UserService;
import com.creditcard.Creditcard.shared.customer.*;
import com.creditcard.Creditcard.ui.model.response.user.Messages;
import com.creditcard.Creditcard.ui.model.response.user.UserResponseModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    Utils utils;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    BillGenerationRepo billGenerationRepo;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CreditCardRepository creditCardRepository;
    @Autowired
    PaymentRepo paymentRepo;
    @Autowired
    EmailService emailService;
    @Autowired
    AddressRepository addressRepository;

    public UserResponseModel createUser(UserDto user) throws ClientSideException {
        UserEntity alreadyExsistingUser = userRepo.findByEmail(user.getEmail());
        if (alreadyExsistingUser != null) {
            throw new ClientSideException(Messages.RECORD_ALREADY_EXISTS);
        } else {
            for (int i = 0; i < user.getAddress().size(); i++) {
                AddressDto addressdto = user.getAddress().get(i);
                addressdto.setUserDetails(user);
                user.getAddress().set(i, addressdto);
            }
            AccountDto accountDto = user.getAccountDetails();
            accountDto.setUserDetails(user);
            user.setAccountDetails(user.getAccountDetails());

            CreditCardDto creditCardDto = user.getCreditCardDetails();
            creditCardDto.setUserDetails(user);
            user.setCreditCardDetails(user.getCreditCardDetails());
            UserEntity user1 = new ModelMapper().map(user, UserEntity.class);
            user1.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user1.setUserId(utils.generateUserId(30));
            RoleEntity roleEntity = roleRepository.findByName("ROLE_USER");
            Collection<RoleEntity> arrayList = new ArrayList<>();
            arrayList.add(roleEntity);
            user1.setRoles(arrayList);

//        String token = utils.generateEmailVerificationToken(user1.getUserId());
//        user1.setEmailVerificationToken(token);
            //customer1.setPassword(bCryptPasswordEncoder.encode(customer1.getPassword()));
            userRepo.save(user1);
            UserResponseModel responseModel = new UserResponseModel();
            responseModel.setFirstName(user1.getFirstName());
            responseModel.setLastName(user1.getLastName());
            responseModel.setEmail(user1.getEmail());
            responseModel.setPhoneNumber(user1.getPhoneNumber());
            return responseModel;
        }
    }
    @Override
    public UserDto getUser(String email) throws ClientSideException{
        UserEntity userEntity = userRepo.findByEmail(email);
        if (userEntity == null) throw new ClientSideException(Messages.EMAIL_NOT_FOUND.getMessage());

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity,returnValue);

        return returnValue;
    }
    /**
     * Method to load a user by its username(email).
     * @param email email entered by user while doing login.
     * @return UserDetails.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepo.findByEmail(email);
        Collection<GrantedAuthority> authorities = new HashSet<>();
        Collection<AuthorityEntity> authorityEntity = new HashSet<>();

        Collection<RoleEntity> roles = userEntity.getRoles();

        roles.forEach((role) -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            authorityEntity.addAll(role.getAuthorities());
        });

        authorityEntity.forEach((authorityEntityobj) -> {
            authorities.add(new SimpleGrantedAuthority(authorityEntityobj.getName()));
        });
        return new User(userEntity.getEmail(),userEntity.getPassword(),
                userEntity.getEmailVerificationStatus(),
                true,true,true,authorities);
    }


    /**
     * Method to check if the record exists and to check the authorization.
     * @param userId unique userId generated for the user.
     * @throws ClientSideException Throws clientSideException.
     */
    @Override
    public void hasAccess(String userId) throws ClientSideException{
        UserEntity userEntity = userRepo.findByUserId(userId);
        if(userEntity == null)
            throw new ClientSideException(Messages.NO_RECORD_FOUND.getMessage());

        String  auth = SecurityContextHolder.getContext().getAuthentication().toString();
        if(!auth.contains(userEntity.getEmail()) && !auth.contains("ROLE_ADMIN"))
            throw new ClientSideException(Messages.NO_ACCESS.getMessage());
    }

    public List<UserDto> getUsers(int page, int limit) {
        List<UserDto> returnValue = new ArrayList<>();
        Pageable pageableRequest = PageRequest.of(page,limit);
        Page<UserEntity> usersPage = userRepo.findAll(pageableRequest);
        List<UserEntity> users = usersPage.getContent();

        for (UserEntity userEntity : users){
            UserDto userDto = new ModelMapper().map(userEntity,UserDto.class);
            returnValue.add(userDto);
        }
        return returnValue;
    }
    public PaymentEntity__ billPayment(String userId, Long billId) throws ClientSideException {
        Long id = userRepo.findIdByUserId(userId);
        String to=userRepo.findEmailById(id);
        BillGenerationEntity billGeneration = billGenerationRepo.findByUserIdAndBillId(id, billId);
        if (billGeneration == null) {
            throw new ClientSideException(Messages.NO_BILLS_FOUND);
        } else {
            double amount = billGeneration.getDueAmount();
            if (billGeneration.getDueOn().isAfter(LocalDateTime.now())) {
                for (LocalDateTime x = billGeneration.getDueOn(); x.isBefore(LocalDateTime.now()); x.plusDays(1)) {
                    amount = amount + 500;
                }
            }
            AccountEntity account = accountRepository.findByUserId(id);
            CreditCardEntity creditCardEntity = creditCardRepository.findByUserId(id);
            if (account.getBalance() >= amount) {
                account.setBalance(account.getBalance() - amount);
                double account_balance = account.getBalance() - amount;

                if ((creditCardEntity.getRemainingCredit())<(creditCardEntity.getCardLimit())) {
                    creditCardEntity.setOutStandingAmount((long) (creditCardEntity.getOutStandingAmount() - amount));
                    creditCardEntity.setRemainingCredit((long) (creditCardEntity.getRemainingCredit() + amount));
                }
                    PaymentEntity__ paymentEntity = new PaymentEntity__();
                    paymentEntity.setOnDate(LocalDate.now());
                    paymentEntity.setAmount(amount);
                    paymentEntity.setMessage("Transaction Successful");
                    paymentEntity.setOutStandingAmount(creditCardEntity.getOutStandingAmount());
                    paymentEntity.setRemaining_credit(creditCardEntity.getRemainingCredit());
                    paymentEntity.setAccount_balance((long) account.getBalance());
                    paymentEntity.setUserId(id);
                    paymentRepo.save(paymentEntity);
                    creditCardRepository.save(creditCardEntity);
                    accountRepository.save(account);
                    creditCardRepository.save(creditCardEntity);
                    billGenerationRepo.updateById(id, "success");
                UserEmailBuilder emailBuilder = new UserEmailBuilder();
                emailService.send(to, emailBuilder.buildPaymentContent
                        ( paymentEntity.getAmount(),
                                paymentEntity.getAccount_balance(), paymentEntity.getOutStandingAmount(),paymentEntity.getRemaining_credit()));
                return paymentEntity;
            } else {
                InsufficientBalanceEmailBuilder emailBuilder = new InsufficientBalanceEmailBuilder();
                emailService.send(to,emailBuilder.buildInsufficientBalanceMail(account.getBalance(),creditCardEntity.getOutStandingAmount(),creditCardEntity.getOutStandingAmount()));
                throw new ClientSideException(Messages.INSUFFICIENT_BALANCE);
            }


        }
    }

    @Override
    public List<PaymentEntity__> fetchPaymentRecords(String userId) {
        Long id = userRepo.findIdByUserId(userId);
        return paymentRepo.fetchUserRecords(id);
    }

    @Override
    public List<BillGenerationEntity> fetchBillsToPay(String userId) {
        Long id = userRepo.findIdByUserId(userId);
        List<BillGenerationEntity> billsToPay=billGenerationRepo.findListByUserId(id,"pending");
        if (billsToPay==null){
            throw new ClientSideException(Messages.NO_BILLS_FOUND);
        }
        else
            return billsToPay;
    }

    @Override
    public UserDto updateUser(String userId, UserDto user) {
        UserEntity userEntity = userRepo.findByUserId(userId);
        if (user.getFirstName() != null){
            userEntity.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null){
            userEntity.setLastName(user.getLastName());
        }
        if (user.getPhoneNumber() != null){
            userEntity.setPhoneNumber(user.getPhoneNumber());
        }
        if (user.getEmail() != null){
            userEntity.setEmail(user.getEmail());
        }
        UserEntity updatedUserDetails;
        try{
            updatedUserDetails = userRepo.save(userEntity);
        }
        catch (Exception e){
            throw new ClientSideException(Messages.FAILED_DB_SAVE.getMessage());
        }
        return new ModelMapper().map(updatedUserDetails,UserDto.class);
    }

    @Override
    public AddressDto updateAddress(String userId, AddressDto addressDto, Long addressId) {
        Long id = userRepo.findIdByUserId(userId);
        AddressEntity address = addressRepository.findByIdAndUserId(addressId,id);
        if (addressDto.getCity()!=null){
            address.setCity(addressDto.getCity());
        }
        if (addressDto.getType()!=null){
            address.setType(addressDto.getType());
        }
        if (addressDto.getCountry()!=null){
            address.setCountry(addressDto.getCountry());
        }
        if (addressDto.getStreetName()!=null){
            address.setStreetName(addressDto.getStreetName());
        }
        if (addressDto.getPostalCode()!=null){
            address.setPostalCode(addressDto.getPostalCode());
        }
        AddressEntity updatedAddressDetails;
        try {
            {
                updatedAddressDetails=addressRepository.save(address);
            }
        }
        catch (Exception e){
            throw new ClientSideException(Messages.FAILED_DB_SAVE.getMessage());
        }
        return new ModelMapper().map(updatedAddressDetails, AddressDto.class);
    }


}


