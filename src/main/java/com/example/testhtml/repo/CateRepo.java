package com.example.testhtml.repo;

import com.example.testhtml.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CateRepo extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findByDeleteFlagIsFalse();
    List<CategoryEntity> findByDeleteFlagIsFalseOrderByCreateDateAsc();


    List<CategoryEntity> findByIdAndDeleteFlagIsFalse(Long id);

    List<CategoryEntity> findByNameAndDeleteFlagIsFalse(String name);
}
