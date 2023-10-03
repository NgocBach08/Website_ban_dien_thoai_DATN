package com.example.testhtml.repo;

import com.example.testhtml.entity.OSEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OsRepo extends JpaRepository<OSEntity, Long> {
    List<OSEntity> findByDeleteFlagIsFalse();
}
