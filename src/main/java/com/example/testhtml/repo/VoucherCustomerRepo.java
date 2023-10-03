package com.example.testhtml.repo;

import com.example.testhtml.entity.VoucherCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface VoucherCustomerRepo extends JpaRepository<VoucherCustomerEntity, String> {

    List<VoucherCustomerEntity> findAllByVoucherId(String voucherId);


}
