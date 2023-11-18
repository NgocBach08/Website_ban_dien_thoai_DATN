package com.example.testhtml.repo;


import com.example.testhtml.entity.OrdersDetailEntity;
import com.example.testhtml.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrdersDetailRepo extends JpaRepository<OrdersDetailEntity, Long> {
    List<OrdersDetailEntity> findByDeleteFlagIsFalseAndOrdersEntity(OrdersEntity ordersEntity);

    OrdersDetailEntity findByDeleteFlagIsTrueAndId(Long id);

    OrdersDetailEntity findByDeleteFlagIsFalseAndId(Long id);

}
