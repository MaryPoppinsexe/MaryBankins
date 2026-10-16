package com.mary.bankolandia.service

import com.mary.bankolandia.dto.DepositRequest
import com.mary.bankolandia.dto.TransactionDto
import com.mary.bankolandia.dto.TransferRequest
import com.mary.bankolandia.dto.WithdrawRequest
import com.mary.bankolandia.dto.toDto
import com.mary.bankolandia.enumerator.StatusEnum
import com.mary.bankolandia.enumerator.TransactionType
import com.mary.bankolandia.model.Transaction
import com.mary.bankolandia.repository.AccountRepository
import com.mary.bankolandia.repository.TransactionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.UUID

@Service
class TransactionServiceImpl(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
) :TransactionService {

    @Transactional
    override fun deposit(accountId: UUID, request: DepositRequest): TransactionDto {
        val account = accountRepository.findById(accountId)
            .orElseThrow { NoSuchElementException("Account not found") }

        check(account.status == StatusEnum.ACTIVE) {
            "Account ${account.accountNumber} is not active"
        }

        account.balance = account.balance.add(request.amount)
        accountRepository.save(account)

        val transaction = Transaction().apply {
            this.account = account
            this.amount = request.amount
            this.type = TransactionType.DEPOSIT
            this.description = request.description
        }

        return transactionRepository.save(transaction).toDto()
    }

    @Transactional
    override fun withdraw(accountId: UUID, request: WithdrawRequest): TransactionDto {
        val account = accountRepository.findById(accountId)
            .orElseThrow { NoSuchElementException("Account not found") }

        check(account.status == StatusEnum.ACTIVE) {
            "Account ${account.accountNumber} is not active"
        }
        check(account.balance >= request.amount) {
            "Insufficient funds. Available balance: ${account.balance} EUR"
        }

        account.balance = account.balance.subtract(request.amount)
        accountRepository.save(account)

        val transaction = Transaction().apply {
            this.account = account
            this.amount = request.amount
            this.type = TransactionType.WITHDRAWAL
            this.description = request.description
        }

        return transactionRepository.save(transaction).toDto()
    }

    @Transactional
    override fun transfer(fromAccountId: UUID, request: TransferRequest): Pair<TransactionDto, TransactionDto> {
        val fromAccount = accountRepository.findById(fromAccountId)
            .orElseThrow { NoSuchElementException("Source account not found") }

        val toAccount = accountRepository.findByAccountNumber(request.toAccountNumber)
            ?: throw NoSuchElementException("Destination account ${request.toAccountNumber} not found")

        check(fromAccount.id != toAccount.id) {
            "Cannot transfer to the same account"
        }
        check(fromAccount.status == StatusEnum.ACTIVE) {
            "Source account ${fromAccount.accountNumber} is not active"
        }
        check(toAccount.status == StatusEnum.ACTIVE) {
            "Destination account ${toAccount.accountNumber} is not active"
        }
        check(fromAccount.balance >= request.amount) {
            "Insufficient funds. Available balance: ${fromAccount.balance} EUR"
        }

        // Update balances
        fromAccount.balance = fromAccount.balance.subtract(request.amount)
        toAccount.balance = toAccount.balance.add(request.amount)
        accountRepository.save(fromAccount)
        accountRepository.save(toAccount)

        // Create debit transaction (money leaving source)
        val debit = Transaction().apply {
            this.account = fromAccount
            this.amount = request.amount
            this.type = TransactionType.TRANSFER_DEBIT
            this.description = request.description
        }
        val savedDebit = transactionRepository.save(debit)

        // Create credit transaction (money arriving at destination) linked to debit
        val credit = Transaction().apply {
            this.account = toAccount
            this.amount = request.amount
            this.type = TransactionType.TRANSFER_CREDIT
            this.description = request.description
            this.relatedTransaction = savedDebit
        }
        val savedCredit = transactionRepository.save(credit)

        // Link debit back to credit so both sides know about each other
        savedDebit.relatedTransaction = savedCredit
        transactionRepository.save(savedDebit)

        return Pair(savedDebit.toDto(), savedCredit.toDto())
    }

    override fun getHistory(
        accountId: UUID,
        type: TransactionType?,
        from: Instant?,
        to: Instant?,
    ): List<TransactionDto> {
        if (!accountRepository.existsById(accountId)) {
            throw NoSuchElementException("Account with id $accountId not found")
        }
        return transactionRepository.findHistory(accountId, type, from, to)
            .map { it.toDto() }
    }
}