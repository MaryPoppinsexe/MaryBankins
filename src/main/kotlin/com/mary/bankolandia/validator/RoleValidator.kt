package com.mary.bankolandia.validator

import com.mary.bankolandia.enumerator.RoleEnum
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [RoleValidator::class])
annotation class ValidRole(
    val message: String = "Invalid role. Accepted values: USER, ADMIN",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)

class RoleValidator : ConstraintValidator<ValidRole, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        return RoleEnum.entries.any { it.name == value?.uppercase() }
    }
}