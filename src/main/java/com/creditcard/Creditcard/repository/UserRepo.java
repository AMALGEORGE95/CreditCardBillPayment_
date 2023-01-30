package com.creditcard.Creditcard.repository;

import com.creditcard.Creditcard.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends PagingAndSortingRepository<UserEntity,Long> {
    UserEntity findByEmail(String email);

    UserEntity findByUserId(String userId);
    @Query(value ="SELECT id FROM users u WHERE u.user_id=?1",nativeQuery = true)
    Long findIdByUserId(String userId);
    @Query(value ="SELECT email_id FROM users u WHERE u.id=?1",nativeQuery = true)
    String findEmailById(Long id);
}
