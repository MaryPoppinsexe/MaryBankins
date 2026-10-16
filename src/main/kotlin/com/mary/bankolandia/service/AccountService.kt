package com.mary.bankolandia.service

import com.mary.bankolandia.dto.AccountDto
import com.mary.bankolandia.dto.CreateAccountRequest
import java.util.UUID

interface AccountService {
    fun createAccount(request: CreateAccountRequest): AccountDto
    fun getAccountsByUser(userId: UUID): List<AccountDto>
    fun getAccount(accountId: UUID): AccountDto
}