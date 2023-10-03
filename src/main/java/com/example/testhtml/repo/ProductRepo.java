package com.example.testhtml.repo;

import com.example.testhtml.entity.CategoryEntity;
import com.example.testhtml.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, Long> {

    @Query("select o from ProductEntity o where o.deleteFlag = false")
    List<ProductEntity> findAll();

    @Query("select o from ProductEntity o where o.deleteFlag = false and o.name LIKE %:keyword%")
    List<ProductEntity> findByName(@Param("keyword") String name);

    ProductEntity findByIdAndDeleteFlagIsFalse(Long id);

    List<ProductEntity> findByCategoryAndDeleteFlagIsFalse(CategoryEntity categoryEntity);
}
