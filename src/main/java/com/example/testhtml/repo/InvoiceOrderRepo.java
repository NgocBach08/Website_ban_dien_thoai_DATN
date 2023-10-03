package com.example.testhtml.repo;


import com.example.testhtml.entity.InvoiceOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceOrderRepo extends JpaRepository<InvoiceOrderEntity, Long> {
    List<InvoiceOrderEntity> findByDeleteFlagIsFalseOrderByIdDesc();

    @Query("select o from InvoiceOrderEntity o where o.deleteFlag = false and o.status = 1")
    List<InvoiceOrderEntity> findAllNhapHang();
    InvoiceOrderEntity findByCodeOrder(String code);

    InvoiceOrderEntity findByIdAndDeleteFlagIsFalse(Long id);
    @Query("select o from InvoiceOrderEntity o where o.deleteFlag = false  and o.totalMoney > 0")
    List<InvoiceOrderEntity> findByDeleteFlagIsFalseAndTotalMoney();
}
