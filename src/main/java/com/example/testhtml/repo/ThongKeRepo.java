package com.example.testhtml.repo;

import com.example.testhtml.dto.respone.ThongKeDto;
import com.example.testhtml.entity.OrdersDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ThongKeRepo extends JpaRepository<OrdersDetailEntity, Long> {
    @Query(name = "thong_ke", nativeQuery = true)
    List<ThongKeDto> findStockAkhirPerProductIn(
            @Param("day") String day,
            @Param("month") String month,
            @Param("year") String year
    );
}

