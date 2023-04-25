package gg.jrg.audiminder.core.presentation

import androidx.lifecycle.ViewModel
import gg.jrg.audiminder.core.util.NavEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel : ViewModel() {

    private val mutableNavEvent: MutableStateFlow<NavEvent?> = MutableStateFlow(null)
    val navEvent: StateFlow<NavEvent?> = mutableNavEvent.asStateFlow()

    fun navigate(navEvent: NavEvent) {
        mutableNavEvent.value = navEvent
    }
}