package ru.gusandr.shopcourse.presentation.navigation

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun Fragment.collectNavigation(
    flow: Flow<NavCommand>,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(minActiveState) {
            launch {
                flow.collect { cmd ->
                    val nav = findNavController()
                    when (cmd) {
                        is NavCommand.To -> nav.navigate(cmd.directions)
                        is NavCommand.ToActionId -> nav.navigate(cmd.actionId)
                        is NavCommand.Back -> nav.navigateUp()
                        is NavCommand.BackTo -> nav.popBackStack(cmd.destinationId, cmd.inclusive)
                    }
                }
            }
        }
    }
}