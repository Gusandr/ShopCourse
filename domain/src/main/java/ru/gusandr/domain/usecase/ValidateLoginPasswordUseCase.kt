package ru.gusandr.domain.usecase

import ru.gusandr.domain.model.ValidationResult

class ValidateLoginPasswordUseCase {
    operator fun invoke(password: String): ValidationResult {
        return when {
            password.isBlank() -> ValidationResult.Error
            else -> ValidationResult.Success
        }
    }
}