package com.creditcard.Creditcard.repository;

import com.creditcard.Creditcard.entity.StatementEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatementRepository extends CrudRepository<StatementEntity,Long> {

}
