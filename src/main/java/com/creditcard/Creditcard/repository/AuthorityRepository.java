package com.creditcard.Creditcard.repository;

import com.creditcard.Creditcard.entity.AuthorityEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface is used to access the database.
 */
@Repository
public interface AuthorityRepository extends CrudRepository<AuthorityEntity,Long> {

    /**
     * Method is used to find authority with authority name.
     * @param name name of authority.
     * @return AuthorityEntity
     */
    AuthorityEntity findByName(String name);
}
