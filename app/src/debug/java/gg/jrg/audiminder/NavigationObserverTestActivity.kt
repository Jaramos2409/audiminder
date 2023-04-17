package gg.jrg.audiminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import gg.jrg.audiminder.core.presentation.NavigationObserver
import gg.jrg.audiminder.databinding.ActivityNavigationObserverTestBinding
import gg.jrg.audiminder.databinding.FragmentNavigationObserverTestFragmentBinding

@AndroidEntryPoint
class NavigationObserverTestActivity : AppCompatActivity() {

    private val viewModel: TestViewModel by viewModels()
    private val navObserver by lazy { NavigationObserver(viewModel, R.id.nav_host_test_fragment) }
    lateinit var binding: ActivityNavigationObserverTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navObserver.register(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_navigation_observer_test)

        binding.bottomTestNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_navigate_to_button -> {
                    viewModel.navigateTo()
                    true
                }
                R.id.bottom_navigate_back_button -> {
                    viewModel.navigateBack()
                    true
                }
                R.id.bottom_navigate_up_button -> {
                    viewModel.navigateUp()
                    true
                }
                R.id.bottom_navigate_back_to_root_button -> {
                    viewModel.navigateBackToRoot()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

}

@AndroidEntryPoint
class NavigationObserverTestActivityTestFragment : Fragment() {

    private lateinit var binding: FragmentNavigationObserverTestFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_navigation_observer_test_fragment,
            container,
            false
        )
        return binding.root
    }


}