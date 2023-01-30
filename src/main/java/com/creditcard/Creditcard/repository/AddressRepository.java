package com.creditcard.Creditcard.repository;

import com.creditcard.Creditcard.entity.AddressEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity,Long> {
}
