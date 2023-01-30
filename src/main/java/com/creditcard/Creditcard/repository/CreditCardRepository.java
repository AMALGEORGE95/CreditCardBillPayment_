package com.creditcard.Creditcard.repository;

import com.creditcard.Creditcard.entity.CreditCardEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends CrudRepository<CreditCardEntity,Long> {
    @Query(value ="SELECT * FROM credit_card c WHERE c.user_id=?1",nativeQuery = true)
    CreditCardEntity findByUserId(Long id);
}
