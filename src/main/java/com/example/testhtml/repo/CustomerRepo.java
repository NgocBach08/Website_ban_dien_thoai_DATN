package com.example.testhtml.repo;

import com.example.testhtml.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findById(Long id);

    CustomerEntity findByPhoneNumber(String phone);
    @Query("SELECT o FROM CustomerEntity o WHERE o.fullName LIKE %:keyword% OR o.phoneNumber LIKE %:keyword% OR o.email LIKE %:keyword%")
    List<CustomerEntity> findByKeyword(@Param("keyword") String keyword);
}
