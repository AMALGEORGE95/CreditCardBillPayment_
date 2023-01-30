package com.creditcard.Creditcard.repository;

import com.creditcard.Creditcard.entity.AccountEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity,Long> {
    @Query(value ="SELECT * FROM account a  WHERE a.user_id=?1",nativeQuery = true)
    AccountEntity findByUserId(Long id);
}
