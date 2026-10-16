package com.mary.bankolandia.service

import com.mary.bankolandia.dto.AuthResponse
import com.mary.bankolandia.dto.LoginRequest
import com.mary.bankolandia.dto.RegisterRequest

interface AuthService {

    fun login(request: LoginRequest): AuthResponse
    fun register(request: RegisterRequest)
}