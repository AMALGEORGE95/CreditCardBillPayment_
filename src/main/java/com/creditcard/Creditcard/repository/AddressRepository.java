package com.creditcard.Creditcard.repository;

import com.creditcard.Creditcard.entity.AddressEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity,Long> {
    @Query(value ="SELECT * FROM address a WHERE a.id=?1 and a.user_id=?2 ",nativeQuery = true)
    AddressEntity findByIdAndUserId(Long addressId, Long id);
}
