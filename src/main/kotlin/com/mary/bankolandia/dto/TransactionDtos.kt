package com.mary.bankolandia.dto

import com.mary.bankolandia.enumerator.TransactionStatus
import com.mary.bankolandia.enumerator.TransactionType
import com.mary.bankolandia.model.Transaction
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class DepositRequest(
    @field:NotNull
    @field:DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    @field:Digits(integer = 17, fraction = 2, message = "Invalid amount format")
    val amount: BigDecimal,

    val description: String? = null,
)

data class WithdrawRequest(
    @field:NotNull
    @field:DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    @field:Digits(integer = 17, fraction = 2, message = "Invalid amount format")
    val amount: BigDecimal,

    val description: String? = null,
)

data class TransferRequest(
    @field:NotNull
    val toAccountNumber: String,

    @field:NotNull
    @field:DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    @field:Digits(integer = 17, fraction = 2, message = "Invalid amount format")
    val amount: BigDecimal,

    val description: String? = null,
)

data class TransactionDto(
    val id: UUID?,
    val accountId: UUID?,
    val amount: BigDecimal,
    val type: TransactionType,
    val status: TransactionStatus,
    val relatedTransactionId: UUID?,
    val description: String?,
    val createdAt: Instant?,
)

fun Transaction.toDto() = TransactionDto(
    id = this.id,
    accountId = this.account.id,
    amount = this.amount,
    type = this.type,
    status = this.status,
    relatedTransactionId = this.relatedTransaction?.id,
    description = this.description,
    createdAt = this.createdAt,
)
