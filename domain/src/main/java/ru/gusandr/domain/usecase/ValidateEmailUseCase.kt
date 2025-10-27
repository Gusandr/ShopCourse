package ru.gusandr.domain.usecase

import ru.gusandr.domain.model.ValidationResult

class ValidateEmailUseCase {
    private val emailRegex = Regex(
        pattern = """^(?!.*\p{IsCyrillic})[^@\s]+@[^@\s.]+\.[^@\s]{2,}$""",
        options = setOf(RegexOption.IGNORE_CASE)
    )

    operator fun invoke(email: String): ValidationResult {
        return when {
            !isValidEmail(email) -> ValidationResult.Error
            else -> ValidationResult.Success
        }
    }

    private fun isValidEmail(email: String): Boolean {
        if (email.isBlank()) return false
        return emailRegex.matches(email)
    }
}