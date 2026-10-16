package com.mary.bankolandia.dto

import com.mary.bankolandia.enumerator.StatusEnum
import com.mary.bankolandia.model.Account
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID



data class CreateAccountRequest(
    @field:NotNull
    val userId: UUID,

    @field:NotBlank
    val label: String = "Account",
)

data class AccountDto(
    val id: UUID?,
    val accountNumber: String,
    val userId: UUID?,
    val balance: BigDecimal,
    val accountStatusEnum: StatusEnum,
    val label: String,
    val createdAt: Instant?,
)

fun Account.toDto() = AccountDto(
    id = this.id,
    accountNumber = this.accountNumber,
    userId = this.user.id,
    balance = this.balance,
    accountStatusEnum = this.status,
    label = this.label,
    createdAt = this.createdAt,
)
