package com.techmix.fisher.repository;

import com.techmix.fisher.entity.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author fisher
 * @since 4/8/16
 */

@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {
}
