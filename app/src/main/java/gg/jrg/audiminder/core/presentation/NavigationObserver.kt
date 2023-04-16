package gg.jrg.audiminder.core.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import gg.jrg.audiminder.util.collectLifecycleFlow

class NavigationObserver(private val viewModel: BaseViewModel) :
    FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentViewCreated(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onFragmentViewCreated(fragmentManager, fragment, view, savedInstanceState)

        fragment.collectLifecycleFlow(viewModel.navEvent) { navEvent ->
            navEvent?.let {
                when (it) {
                    is BaseViewModel.NavEvent.To -> navigateTo(it, fragment)
                    BaseViewModel.NavEvent.Up -> navigateUp(fragment)
                    BaseViewModel.NavEvent.Back -> navigateBack(fragment)
                    is BaseViewModel.NavEvent.BackTo -> navigateBackTo(it, fragment)
                }
            }
        }
    }

    fun register(fragment: Fragment) {
        fragment.parentFragmentManager.registerFragmentLifecycleCallbacks(this, false)
    }

    private fun navigateTo(event: BaseViewModel.NavEvent.To, fragment: Fragment) {
        findNavController(fragment).navigate(event.directions)
    }

    private fun navigateUp(fragment: Fragment) {
        findNavController(fragment).navigateUp()
    }

    private fun navigateBack(fragment: Fragment) {
        findNavController(fragment).popBackStack()
    }

    private fun navigateBackTo(event: BaseViewModel.NavEvent.BackTo, fragment: Fragment) {
        findNavController(fragment).popBackStack(
            event.destinationId,
            false
        )
    }
}


