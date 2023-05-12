package gg.jrg.audiminder.core.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import gg.jrg.audiminder.R
import gg.jrg.audiminder.collections.presentation.CollectionsScreenFragmentDirections
import gg.jrg.audiminder.core.util.ActivityStateFlowWrapper
import gg.jrg.audiminder.core.util.NavEvent
import gg.jrg.audiminder.core.util.collectLifecycleFlow
import gg.jrg.audiminder.databinding.ActivityMainBinding
import gg.jrg.audiminder.home.presentation.HomeScreenFragmentDirections
import gg.jrg.audiminder.search.presentation.SearchScreenFragmentDirections
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var binding: ActivityMainBinding
    private val navigationViewModel: NavigationViewModel by viewModels()

    @Inject
    lateinit var activityStateFlowWrapper: ActivityStateFlowWrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityStateFlowWrapper.stateFlow.value = this

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        navHostFragment =
            binding.navHostFragment.getFragment() as NavHostFragment

        binding.bottomNavView.setupWithNavController(navHostFragment.navController)

        collectLifecycleFlow(navigationViewModel.navigationEvent) { navEvent ->
            when (navEvent) {
                is NavEvent.To -> navHostFragment.navController.navigate(navEvent.directions)
                NavEvent.Up -> navHostFragment.navController.navigateUp()
                NavEvent.Back -> navHostFragment.navController.popBackStack()
                is NavEvent.BackTo -> navHostFragment.navController.popBackStack(
                    navEvent.destinationId,
                    false
                )
            }
        }

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.homeScreenFragment ||
                destination.id == R.id.searchScreenFragment ||
                destination.id == R.id.collectionsScreenFragment
            ) {
                binding.bottomNavView.visibility = View.VISIBLE
            } else {
                binding.bottomNavView.visibility = View.GONE
            }
        }

        binding.bottomNavView.setOnItemSelectedListener { item ->
            @IdRes val selectedIcon = item.itemId
            @IdRes val currentFragmentId = navHostFragment.navController.currentDestination?.id

            when (Pair(currentFragmentId, selectedIcon)) {
                Pair(R.id.homeScreenFragment, R.id.search_navigation_icon) -> {
                    navigationViewModel.navigate(
                        NavEvent.To(HomeScreenFragmentDirections.actionHomeScreenFragmentToSearchScreenFragment())
                    )
                    true
                }

                Pair(R.id.homeScreenFragment, R.id.collections_navigation_icon) -> {
                    navigationViewModel.navigate(
                        NavEvent.To(HomeScreenFragmentDirections.actionHomeScreenFragmentToCollectionsScreenFragment())
                    )
                    true
                }

                Pair(R.id.searchScreenFragment, R.id.home_navigation_icon) -> {
                    navigationViewModel.navigate(
                        NavEvent.To(SearchScreenFragmentDirections.actionSearchScreenFragmentToHomeScreenFragment())
                    )
                    true
                }

                Pair(R.id.searchScreenFragment, R.id.collections_navigation_icon) -> {
                    navigationViewModel.navigate(
                        NavEvent.To(SearchScreenFragmentDirections.actionSearchScreenFragmentToCollectionsScreenFragment())
                    )
                    true
                }

                Pair(R.id.collectionsScreenFragment, R.id.home_navigation_icon) -> {
                    navigationViewModel.navigate(
                        NavEvent.To(CollectionsScreenFragmentDirections.actionCollectionsScreenFragmentToHomeScreenFragment())
                    )
                    true
                }

                Pair(R.id.collectionsScreenFragment, R.id.search_navigation_icon) -> {
                    navigationViewModel.navigate(
                        NavEvent.To(CollectionsScreenFragmentDirections.actionCollectionsScreenFragmentToSearchScreenFragment())
                    )
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    override fun onDestroy() {
        activityStateFlowWrapper.stateFlow.value = null
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navHostFragment.navController.navigateUp() || super.onSupportNavigateUp()
    }

}
