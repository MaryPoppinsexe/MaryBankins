package com.mary.bankolandia.validator

import com.mary.bankolandia.enumerator.StatusEnum
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [StatusValidator::class])
annotation class ValidStatus(
    val message: String = "Invalid status. Accepted values: INACTIVE, ACTIVE",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)

class StatusValidator : ConstraintValidator<ValidStatus, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        return value == null || StatusEnum.entries.any { it.name == value.uppercase() }
    }
}