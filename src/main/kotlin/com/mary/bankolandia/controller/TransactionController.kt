package com.mary.bankolandia.controller

import com.mary.bankolandia.dto.DepositRequest
import com.mary.bankolandia.dto.TransactionDto
import com.mary.bankolandia.dto.TransferRequest
import com.mary.bankolandia.dto.WithdrawRequest
import com.mary.bankolandia.enumerator.TransactionType
import com.mary.bankolandia.service.TransactionService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.UUID

@RestController
@RequestMapping("/api/accounts/{accountId}/transactions")
class TransactionController(
    private val transactionService: TransactionService,
) {

    @PostMapping("/deposit")
    fun deposit(
        @PathVariable accountId: UUID,
        @Valid @RequestBody request: DepositRequest,
    ): ResponseEntity<TransactionDto> =
        ResponseEntity.status(HttpStatus.CREATED)
            .body(transactionService.deposit(accountId, request))

    @PostMapping("/withdraw")
    fun withdraw(
        @PathVariable accountId: UUID,
        @Valid @RequestBody request: WithdrawRequest,
    ): ResponseEntity<TransactionDto> =
        ResponseEntity.status(HttpStatus.CREATED)
            .body(transactionService.withdraw(accountId, request))

    @PostMapping("/transfer")
    fun transfer(
        @PathVariable accountId: UUID,
        @Valid @RequestBody request: TransferRequest,
    ): ResponseEntity<Map<String, TransactionDto>> {
        val (debit, credit) = transactionService.transfer(accountId, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            mapOf("debit" to debit, "credit" to credit)
        )
    }

    @GetMapping
    fun getHistory(
        @PathVariable accountId: UUID,
        @RequestParam(required = false) type: TransactionType?,
        @RequestParam(required = false) from: Instant?,
        @RequestParam(required = false) to: Instant?,
    ): ResponseEntity<List<TransactionDto>> =
        ResponseEntity.ok(transactionService.getHistory(accountId, type, from, to))
}