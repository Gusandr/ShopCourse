package ru.gusandr.domain.model

sealed class ValidationResult {
    data object Success : ValidationResult()
    data object Error : ValidationResult()
}