package ru.gusandr.shopcourse.presentation.screens.auth.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gusandr.domain.model.ValidationResult
import ru.gusandr.domain.usecase.OpenSocialAuthUseCase
import ru.gusandr.domain.usecase.ValidateEmailUseCase
import ru.gusandr.domain.usecase.ValidatePasswordUseCase
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val openSocialAuthUseCase: OpenSocialAuthUseCase,
    ) : ViewModel() {
    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

    fun onEmailChanged(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
        updateButtonState()
    }

    fun onPasswordChanged(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
        updateButtonState()
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = confirmPassword)
        updateButtonState()
    }

    private fun updateButtonState() {
        val currState = _uiState.value
        val emailValid = validateEmailUseCase(currState.email) is ValidationResult.Success
        val passwordValid = validatePasswordUseCase(
            currState.password,
            currState.confirmPassword
        ) is ValidationResult.Success

        _uiState.value = currState.copy(
            isButtonEnabled = emailValid && passwordValid
        )
    }

    fun onRegisterClick() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            // тута типа в скором будущем должна быть отправка данных на сервер

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                isSuccess = true
            )
        }
    }

    fun getVkAuthUrl() = openSocialAuthUseCase.getVkUrl()
    fun getOkAuthUrl() = openSocialAuthUseCase.getOkUrl()
}

data class RegistrationUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isButtonEnabled: Boolean = false
)