package com.mary.bankolandia.service.impl

import com.mary.bankolandia.dto.AccountDto
import com.mary.bankolandia.dto.CreateAccountRequest
import com.mary.bankolandia.dto.toDto
import com.mary.bankolandia.model.Account
import com.mary.bankolandia.repository.AccountRepository
import com.mary.bankolandia.repository.UserRepository
import com.mary.bankolandia.service.AccountService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class AccountServiceImpl(
    private val accountRepository: AccountRepository,
    private val userRepository: UserRepository,
): AccountService {

    @Transactional
    override fun createAccount(request: CreateAccountRequest): AccountDto {
        val user = userRepository.findById(request.userId)
            .orElseThrow { NoSuchElementException("User with id ${request.userId} not found") }

        val account = Account().apply {
            this.user = user
            this.accountNumber = generateUniqueAccountNumber()
            this.label = request.label
        }

        return accountRepository.save(account).toDto()
    }

    override fun getAccountsByUser(userId: UUID): List<AccountDto> {
        if (!userRepository.existsById(userId)) {
            throw NoSuchElementException("User with id $userId not found")
        }
        return accountRepository.findByUserId(userId).map { it.toDto() }
    }

    override fun getAccount(accountId: UUID): AccountDto =
        accountRepository.findById(accountId)
            .orElseThrow { NoSuchElementException("Account with id $accountId not found") }
            .toDto()

    private fun generateUniqueAccountNumber(): String {
        var accountNumber: String
        do {
            accountNumber = "PT" + (1..18).map { (0..9).random() }.joinToString("")
        } while (accountRepository.existsByAccountNumber(accountNumber))
        return accountNumber
    }
}