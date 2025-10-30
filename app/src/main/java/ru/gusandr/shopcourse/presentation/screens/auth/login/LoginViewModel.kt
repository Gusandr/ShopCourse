package ru.gusandr.shopcourse.presentation.screens.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.gusandr.domain.model.ValidationResult
import ru.gusandr.domain.usecase.OpenSocialAuthUseCase
import ru.gusandr.domain.usecase.ValidateEmailUseCase
import ru.gusandr.domain.usecase.ValidateLoginPasswordUseCase
import ru.gusandr.shopcourse.presentation.navigation.NavCommand
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidateLoginPasswordUseCase,
    private val openSocialAuthUseCase: OpenSocialAuthUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _nav = Channel<NavCommand>(capacity = Channel.BUFFERED)
    val nav: Flow<NavCommand> = _nav.receiveAsFlow()

    fun onEmailChanged(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
        updateButtonState()
    }

    fun onPasswordChanged(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
        updateButtonState()
    }

    private fun updateButtonState() {
        val currState = _uiState.value
        val emailValid = validateEmailUseCase(currState.email) is ValidationResult.Success
        val passwordValid = validatePasswordUseCase(
            currState.password,
        ) is ValidationResult.Success

        _uiState.value = currState.copy(
            isButtonEnabled = emailValid && passwordValid
        )
    }

    fun onLoginClick() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            // тута типа в скором будущем должна быть отправка данных на сервер

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                isSuccess = true
            )

            _nav.send(NavCommand.To(LoginFragmentDirections.actionLoginToListCourses()))
        }
    }

    fun onSwitchToRegistration() {
        viewModelScope.launch {
            _nav.send(NavCommand.To(LoginFragmentDirections.actionLoginToRegistration()))
        }
    }

    fun getVkAuthUrl() = openSocialAuthUseCase.getVkUrl()
    fun getOkAuthUrl() = openSocialAuthUseCase.getOkUrl()
}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isButtonEnabled: Boolean = false
)