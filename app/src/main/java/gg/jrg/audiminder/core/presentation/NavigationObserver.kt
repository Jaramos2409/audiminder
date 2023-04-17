package gg.jrg.audiminder.core.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import gg.jrg.audiminder.R
import gg.jrg.audiminder.util.collectLifecycleFlow

class NavigationObserver(
    private val viewModel: BaseViewModel,
    @IdRes private val navHostViewId: Int = R.id.nav_host_fragment
) :
    FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentViewCreated(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onFragmentViewCreated(fragmentManager, fragment, view, savedInstanceState)
        observeNavEvents(fragment)
    }

    fun register(fragment: Fragment) {
        fragment.parentFragmentManager.registerFragmentLifecycleCallbacks(this, false)
    }

    fun register(activity: ComponentActivity) {
        activity.lifecycle.addObserver(LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {
                observeNavEvents(activity)
            }
        })
    }

    private fun observeNavEvents(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.collectLifecycleFlow(viewModel.navEvent) { navEvent ->
            navEvent?.let {
                when (it) {
                    is NavEvent.To -> navigateTo(it, lifecycleOwner)
                    NavEvent.Up -> navigateUp(lifecycleOwner)
                    NavEvent.Back -> navigateBack(lifecycleOwner)
                    is NavEvent.BackTo -> navigateBackTo(it, lifecycleOwner)
                }
            }
        }
    }

    private fun navigateTo(event: NavEvent.To, lifecycleOwner: LifecycleOwner) {
        findNavController(lifecycleOwner).navigate(event.directions)
    }

    private fun navigateUp(lifecycleOwner: LifecycleOwner) {
        findNavController(lifecycleOwner).navigateUp()
    }

    private fun navigateBack(lifecycleOwner: LifecycleOwner) {
        findNavController(lifecycleOwner).popBackStack()
    }

    private fun navigateBackTo(event: NavEvent.BackTo, lifecycleOwner: LifecycleOwner) {
        findNavController(lifecycleOwner).popBackStack(
            event.destinationId,
            false
        )
    }

    private fun findNavController(lifecycleOwner: LifecycleOwner): NavController {
        return when (lifecycleOwner) {
            is Fragment -> lifecycleOwner.findNavController()
            is ComponentActivity -> lifecycleOwner.findNavController(navHostViewId)
            else -> throw IllegalArgumentException("Unsupported LifecycleOwner: $lifecycleOwner")
        }
    }
}



