package com.example.testhtml.repo;

import com.example.testhtml.entity.LoaiScreenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoaiScreenRepo extends JpaRepository<LoaiScreenEntity,Long>{
    List<LoaiScreenEntity> findByDeleteFlagIsFalse();
}
