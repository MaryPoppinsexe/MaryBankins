package com.mary.bankolandia.controller

import com.mary.bankolandia.dto.AuthResponse
import com.mary.bankolandia.dto.LoginRequest
import com.mary.bankolandia.service.AuthService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class AuthTests {

    @Mock
    lateinit var authService: AuthService

    @InjectMocks
    lateinit var authController: AuthController

    @Test
    fun `should return token when login is valid`() {

        val request = LoginRequest(
            username = "admin",
            password = "admin123"
        )

        val response = AuthResponse(
            accessToken = "fake-jwt-token",
            refreshToken = "fake-jwt-token",
            tokenType = "bearer"
        )

        `when`(authService.login(request))
            .thenReturn(response)

        val result = authController.login(request)

        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals("fake-jwt-token", result.body?.accessToken)
    }
}