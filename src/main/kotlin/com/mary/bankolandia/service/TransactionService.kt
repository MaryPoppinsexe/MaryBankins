package com.mary.bankolandia.service

import com.mary.bankolandia.dto.DepositRequest
import com.mary.bankolandia.dto.TransactionDto
import com.mary.bankolandia.dto.TransferRequest
import com.mary.bankolandia.dto.WithdrawRequest
import com.mary.bankolandia.enumerator.TransactionType
import java.time.Instant
import java.util.UUID

interface TransactionService {
    fun deposit(accountId: UUID, request: DepositRequest): TransactionDto
    fun withdraw(accountId: UUID, request: WithdrawRequest): TransactionDto
    fun transfer(fromAccountId: UUID, request: TransferRequest): Pair<TransactionDto, TransactionDto>
    fun getHistory(
        accountId: UUID,
        type: TransactionType?,
        from: Instant?,
        to: Instant?,
    ): List<TransactionDto>
}