package gg.jrg.audiminder

import android.os.Bundle
import androidx.navigation.NavDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import gg.jrg.audiminder.core.presentation.BaseViewModel
import gg.jrg.audiminder.core.util.NavEvent
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor() : BaseViewModel() {

    companion object {
        val expectedDirections = object : NavDirections {
            override val actionId: Int
                get() = 0
            override val arguments: Bundle
                get() = Bundle()
        }

        const val expectedDestinationId = 0
    }

    fun navigateTo() {
        navigate(NavEvent.To(expectedDirections))
    }

    fun navigateBack() {
        navigate(NavEvent.Back)
    }

    fun navigateUp() {
        navigate(NavEvent.Up)
    }

    fun navigateBackToRoot() {
        navigate(NavEvent.BackTo(expectedDestinationId))
    }

}