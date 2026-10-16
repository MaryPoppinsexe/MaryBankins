package com.mary.bankolandia.controller

import com.mary.bankolandia.dto.AccountDto
import com.mary.bankolandia.dto.CreateAccountRequest
import com.mary.bankolandia.service.AccountService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/accounts")
class AccountController(
    private val accountService: AccountService,
) {

    @PostMapping
    fun createAccount(
        @Valid @RequestBody request: CreateAccountRequest,
    ): ResponseEntity<AccountDto> =
        ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(request))

    @GetMapping("/{accountId}")
    fun getAccount(
        @PathVariable accountId: UUID,
    ): ResponseEntity<AccountDto> =
        ResponseEntity.ok(accountService.getAccount(accountId))

    @GetMapping("/user/{userId}")
    fun getAccountsByUser(
        @PathVariable userId: UUID,
    ): ResponseEntity<List<AccountDto>> =
        ResponseEntity.ok(accountService.getAccountsByUser(userId))
}