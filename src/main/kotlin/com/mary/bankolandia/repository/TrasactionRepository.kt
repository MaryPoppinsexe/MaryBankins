package com.mary.bankolandia.repository

import com.mary.bankolandia.enumerator.TransactionType
import com.mary.bankolandia.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.Instant
import java.util.UUID

interface TransactionRepository : JpaRepository<Transaction, UUID> {

    @Query("""
        SELECT t FROM Transaction t
        WHERE t.account.id = :accountId
          AND (:type IS NULL OR t.type = :type)
          AND (:from IS NULL OR t.createdAt >= :from)
          AND (:to   IS NULL OR t.createdAt <= :to)
        ORDER BY t.createdAt DESC
    """)
    fun findHistory(
        @Param("accountId") accountId: UUID,
        @Param("type") type: TransactionType?,
        @Param("from") from: Instant?,
        @Param("to") to: Instant?,
    ): List<Transaction>
}
