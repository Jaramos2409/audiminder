package gg.jrg.spotiminder.core.presentation

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel : ViewModel() {
    sealed class NavEvent {
        data class To(val directions: NavDirections) : NavEvent()
        object Up : NavEvent()
        object Back : NavEvent()
        data class BackTo(val destinationId: Int) : NavEvent()
    }

    private val mutableNavEvent: MutableStateFlow<NavEvent?> = MutableStateFlow(null)
    val navEvent: StateFlow<NavEvent?> = mutableNavEvent.asStateFlow()

    fun navigate(navEvent: NavEvent) {
        mutableNavEvent.value = navEvent
    }
}