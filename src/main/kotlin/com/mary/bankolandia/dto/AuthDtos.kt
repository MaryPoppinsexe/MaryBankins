package com.mary.bankolandia.dto

import com.mary.bankolandia.enumerator.RoleEnum
import com.mary.bankolandia.validator.ValidRole
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Past
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.util.Date

data class LoginRequest(
    @field:NotBlank val username: String,
    @field:NotBlank val password: String,
)

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String = "Bearer",
)

data class RegisterRequest(
    @field:NotBlank
    @field:Size(min = 3, max = 50)
    val username: String,

    @field:NotBlank
    @field:Size(min = 4, message = "Password must be at least 8 characters")
    val password: String,

    @field:NotBlank
    @ValidRole
    val roleEnum: RoleEnum,

    @field:NotBlank
    val firstName: String,

    @field:NotBlank
    val lastName: String,

    @field:Past(message = "Date of birth must be in the past")
    val dateOfBirth: Date,

    @field:NotBlank
    @field:Size(min = 9, max = 9, message = "NIF must be 9 digits")
    @field:Pattern(regexp = "\\d{9}", message = "NIF must contain only digits")
    val nif: String,

    @field:NotBlank
    val phoneNumber: String,

    @field:NotBlank
    @field:Email
    val email: String,
)
