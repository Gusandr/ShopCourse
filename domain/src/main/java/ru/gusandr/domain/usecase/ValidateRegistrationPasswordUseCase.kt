package ru.gusandr.domain.usecase

import ru.gusandr.domain.model.ValidationResult

class ValidateRegistrationPasswordUseCase {
    operator fun invoke(password: String, confirmPassword: String): ValidationResult {
        return when {
            !isValidPassword(password, confirmPassword) -> ValidationResult.Error
            else -> ValidationResult.Success
        }
    }

    private fun isValidPassword(password: String, confirmPassword: String): Boolean {
        if (password.isBlank() || confirmPassword.isBlank()) return false
        if (password != confirmPassword) return false
        return true
    }
}