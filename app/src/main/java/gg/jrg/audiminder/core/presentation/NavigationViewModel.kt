package gg.jrg.audiminder.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gg.jrg.audiminder.core.util.NavEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class NavigationViewModel : ViewModel() {
    private val _navigationEvent = MutableSharedFlow<NavEvent>(replay = 0)
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun navigate(event: NavEvent) {
        viewModelScope.launch {
            _navigationEvent.emit(event)
        }
    }

}