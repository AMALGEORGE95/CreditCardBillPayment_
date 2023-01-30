package com.creditcard.Creditcard.repository;

import com.creditcard.Creditcard.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface is used to access the database.
 */
@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
    /**
     * Method is used to get roles its role name.
     * @param name role name.
     * @return RoleEntity.
     */
    RoleEntity findByName(String name);
}
