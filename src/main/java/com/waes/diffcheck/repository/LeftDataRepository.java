package com.waes.diffcheck.repository;

import com.waes.diffcheck.domain.entity.LeftEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for LeftEntity using JpaRepository
 */
@Repository
public interface LeftDataRepository extends JpaRepository<LeftEntity, String> {
}