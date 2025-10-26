package ru.gusandr.shopcourse.presentation.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import ru.gusandr.shopcourse.R
import ru.gusandr.shopcourse.presentation.navigation.NavCommand
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor() : ViewModel() {
    private val _nav = MutableSharedFlow<NavCommand>()
    val nav: SharedFlow<NavCommand> = _nav

    fun onContinueClicked() {
        viewModelScope.launch {
            _nav.emit(NavCommand.ToActionId(R.id.action_onboarding_to_registration))
        }
    }
}