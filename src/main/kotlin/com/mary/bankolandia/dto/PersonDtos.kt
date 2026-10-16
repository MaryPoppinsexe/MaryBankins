package com.mary.bankolandia.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Past
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.util.Date
import java.util.UUID


class UpdatePersonDto(
    val firstName: String? = null,
    val lastName: String? = null,
    @field:Past(message = "Date of birth must be in the past")
    val dateOfBirth: Date? = null,
    @field:Size(min = 9, max = 9, message = "NIF must be 9 digits")
    @field:Pattern(regexp = "\\d{9}", message = "NIF must contain only digits")
    val nif: String? = null,
    val phoneNumber: String? = null,
    @field:Email
    val email: String? = null,
)

data class PersonDto(
    val id: UUID?= null,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: Date,
    val nif: String,
    val phoneNumber: String,
    val email: String
)
