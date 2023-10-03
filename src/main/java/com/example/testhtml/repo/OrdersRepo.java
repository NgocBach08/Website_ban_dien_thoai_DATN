package com.example.testhtml.repo;

import com.example.testhtml.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 *
 * @author: hieu
 * @since: 12/08/2022
 * Project_name: com.example.word_phone_web.repo
 */
@Repository
public interface OrdersRepo extends JpaRepository<OrdersEntity, Long> {

    List<OrdersEntity> findByDeleteFlagIsFalseOrderByCreateDateDesc();


    OrdersEntity findByCodeOrderAndDeleteFlagIsFalse(String id);
}
