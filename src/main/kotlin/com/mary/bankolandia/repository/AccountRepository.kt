package com.mary.bankolandia.repository

import com.mary.bankolandia.model.Account
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface AccountRepository : JpaRepository<Account, UUID> {
    fun findByUserId(userId: UUID): List<Account>
    fun findByAccountNumber(accountNumber: String): Account?
    fun existsByAccountNumber(accountNumber: String): Boolean
}
