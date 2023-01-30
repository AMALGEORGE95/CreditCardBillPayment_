package com.creditcard.Creditcard.service.serviceImpl;

import com.creditcard.Creditcard.repository.StatementRepository;
import com.creditcard.Creditcard.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatementServiceImpl implements StatementService {
    @Autowired
    StatementRepository statementRepository;
}
