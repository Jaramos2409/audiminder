package gg.jrg.audiminder.core.presentation

import androidx.navigation.NavDirections

sealed class NavEvent {
    data class To(val directions: NavDirections) : NavEvent()
    object Up : NavEvent()
    object Back : NavEvent()
    data class BackTo(val destinationId: Int) : NavEvent()
}
