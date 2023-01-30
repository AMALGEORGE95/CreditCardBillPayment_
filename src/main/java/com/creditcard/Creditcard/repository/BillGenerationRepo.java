package com.creditcard.Creditcard.repository;

import com.creditcard.Creditcard.entity.BillGenerationEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BillGenerationRepo extends CrudRepository<BillGenerationEntity,Long> {
    @Query(value ="SELECT * FROM bill_generation b WHERE b.user_id=?1 and b.status=?2 order by bill_id desc limit 1",nativeQuery = true)
    BillGenerationEntity findByUserId(Long Id,String status);
    @Query(value ="SELECT * FROM bill_generation b WHERE b.user_id=?1 and b.bill_id=?2 ",nativeQuery = true)
    BillGenerationEntity findByUserIdAndBillId(Long Id,Long billId);

    @Query(value ="SELECT bill_id FROM bill_generation b WHERE b.user_id=?1 and b.status=?2",nativeQuery = true)
    boolean findByUserIdCheck(Long Id,String status);

    @Transactional
    @Modifying
    @Query(value = "UPDATE bill_generation set status=?2 WHERE user_id = ?1",nativeQuery = true)
    void updateById(Long id, String success);

    @Query(value ="SELECT * FROM bill_generation b WHERE b.user_id=?1 and b.status=?2 order by bill_id desc limit 1",nativeQuery = true)
    List<BillGenerationEntity> findListByUserId(Long id, String pending);
}
