package com.creditcard.Creditcard.repository;

import com.creditcard.Creditcard.entity.PaymentEntity__;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepo extends CrudRepository<PaymentEntity__,Long> {
    @Query(value ="SELECT * FROM payment_records p WHERE p.user_id=?1",nativeQuery = true)
    List<PaymentEntity__> fetchUserRecords(Long id);
}
