package com.mary.bankolandia.controller

import com.mary.bankolandia.dto.AuthResponse
import com.mary.bankolandia.dto.LoginRequest
import com.mary.bankolandia.dto.RegisterRequest
import com.mary.bankolandia.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService,
) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<AuthResponse> =
        ResponseEntity.ok(authService.login(request))

    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterRequest): ResponseEntity<Void> {
        authService.register(request)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}