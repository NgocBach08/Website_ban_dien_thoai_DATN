package com.example.testhtml.repo;

import com.example.testhtml.entity.LoaiRomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LoaiRomRepo extends JpaRepository<LoaiRomEntity, Long> {
    List<LoaiRomEntity> findByDeleteFlagIsFalse();
}
