package ru.gusandr.shopcourse.presentation.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.gusandr.shopcourse.presentation.navigation.NavCommand
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor() : ViewModel() {
    private val _nav = Channel<NavCommand>(capacity = Channel.BUFFERED)
    val nav: Flow<NavCommand> = _nav.receiveAsFlow()

    fun onContinueClicked() {
        viewModelScope.launch {
            _nav.send(NavCommand.To(OnboardingFragmentDirections.actionOnboardingToRegistration()))
        }
    }

    fun switchToListCourses() {
        viewModelScope.launch {
            _nav.send(NavCommand.To(OnboardingFragmentDirections.actionOnboardingToListCourses()))
        }
    }
}