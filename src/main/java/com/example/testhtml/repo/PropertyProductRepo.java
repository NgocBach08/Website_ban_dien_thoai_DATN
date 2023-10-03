package com.example.testhtml.repo;

import com.example.testhtml.entity.ProductPropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyProductRepo extends JpaRepository<ProductPropertyEntity, Long> {

    @Query("select o from ProductPropertyEntity o where o.deleteFlag = false  and o.romEntity.id = ?1")
    List<ProductPropertyEntity> findByRom(Long id);


    @Query("select o from ProductPropertyEntity o where o.status = 'ON' and o.deleteFlag = false  and o.romEntity.id = ?1")
    List<ProductPropertyEntity> findByRomAAndStatus(Long id);

    @Query("select o from ProductPropertyEntity o where o.deleteFlag = false  and o.romEntity.id = ?1 and o.colorEntity.id = ?2")
    List<ProductPropertyEntity> findByRomAndColor(Long romId, Long colorId);

    @Query("select o from ProductPropertyEntity o where o.deleteFlag = false  and  o.colorEntity.id = ?1")
    List<ProductPropertyEntity> findByColor( Long colorId);


    List<ProductPropertyEntity> findByDeleteFlagIsFalse();


}
