package com.waes.diffcheck.repository;

import com.waes.diffcheck.domain.entity.RightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for RightEntity using JpaRepository
 */
@Repository
public interface RightDataRepository extends JpaRepository<RightEntity, String> {
}