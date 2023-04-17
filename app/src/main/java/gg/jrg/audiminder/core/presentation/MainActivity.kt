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
import gg.jrg.audiminder.authentication.presentation.AuthorizationViewModel
import gg.jrg.audiminder.collections.presentation.CollectionsScreenFragmentDirections
import gg.jrg.audiminder.databinding.ActivityMainBinding
import gg.jrg.audiminder.home.presentation.HomeScreenFragmentDirections
import gg.jrg.audiminder.search.presentation.SearchScreenFragmentDirections

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var binding: ActivityMainBinding
    private val authorizationViewModel by viewModels<AuthorizationViewModel>()
    private val navObserver by lazy { NavigationObserver(authorizationViewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navObserver.register(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        navHostFragment =
            binding.navHostFragment.getFragment() as NavHostFragment

        binding.bottomNavView.setupWithNavController(navHostFragment.navController)

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
                    authorizationViewModel.navigate(NavEvent.To(HomeScreenFragmentDirections.actionHomeScreenFragmentToSearchScreenFragment()))
                    true
                }
                Pair(R.id.homeScreenFragment, R.id.collections_navigation_icon) -> {
                    authorizationViewModel.navigate(NavEvent.To(HomeScreenFragmentDirections.actionHomeScreenFragmentToCollectionsScreenFragment()))
                    true
                }
                Pair(R.id.searchScreenFragment, R.id.home_navigation_icon) -> {
                    authorizationViewModel.navigate(NavEvent.To(SearchScreenFragmentDirections.actionSearchScreenFragmentToHomeScreenFragment()))
                    true
                }
                Pair(R.id.searchScreenFragment, R.id.collections_navigation_icon) -> {
                    authorizationViewModel.navigate(NavEvent.To(SearchScreenFragmentDirections.actionSearchScreenFragmentToCollectionsScreenFragment()))
                    true
                }
                Pair(R.id.collectionsScreenFragment, R.id.home_navigation_icon) -> {
                    authorizationViewModel.navigate(NavEvent.To(CollectionsScreenFragmentDirections.actionCollectionsScreenFragmentToHomeScreenFragment()))
                    true
                }
                Pair(R.id.collectionsScreenFragment, R.id.search_navigation_icon) -> {
                    authorizationViewModel.navigate(NavEvent.To(CollectionsScreenFragmentDirections.actionCollectionsScreenFragmentToSearchScreenFragment()))
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navHostFragment.navController.navigateUp() || super.onSupportNavigateUp()
    }
}