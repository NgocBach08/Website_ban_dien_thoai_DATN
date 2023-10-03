package com.example.testhtml.repo;

import com.example.testhtml.entity.LoaiRomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 *
 * @author: hieu
 * @since: 05/10/2022
 * Project_name: com.example.testhtml.repo
 */
@Repository
public interface LoaiRomRepo extends JpaRepository<LoaiRomEntity, Long> {
    List<LoaiRomEntity> findByDeleteFlagIsFalse();
}
