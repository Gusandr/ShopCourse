package ru.gusandr.shopcourse.presentation.navigation

import androidx.navigation.NavDirections

sealed class NavCommand {
    data class To(val directions: NavDirections): NavCommand()
    data class ToActionId(val actionId: Int): NavCommand()
    data object Back: NavCommand()
    data class BackTo(val destinationId: Int, val inclusive: Boolean = false): NavCommand()
}