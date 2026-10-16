package com.mary.bankolandia.service.impl

import com.mary.bankolandia.dto.AuthResponse
import com.mary.bankolandia.dto.LoginRequest
import com.mary.bankolandia.dto.RegisterRequest
import com.mary.bankolandia.security.JwtService
import com.mary.bankolandia.service.AuthService
import com.mary.bankolandia.service.PersonService
import com.mary.bankolandia.service.UserService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: UserDetailsService,
    private val jwtService: JwtService,
    private val personService: PersonService,
    private val userService: UserService,
) : AuthService {

    override fun login(request: LoginRequest): AuthResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.username, request.password)
        )
        val userDetails = userDetailsService.loadUserByUsername(request.username)

        val accessToken = jwtService.generateAccessToken(userDetails)

        return AuthResponse(accessToken = accessToken, refreshToken = "TBA")
    }

    override fun register(request: RegisterRequest) {
        userService.createUser(request).apply {
            personService.createPerson(request,this) }
    }

}