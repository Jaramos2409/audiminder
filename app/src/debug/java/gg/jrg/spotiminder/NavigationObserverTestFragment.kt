package gg.jrg.spotiminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gg.jrg.spotiminder.core.presentation.NavigationObserver
import gg.jrg.spotiminder.databinding.FragmentTestBinding

@AndroidEntryPoint
class NavigationObserverTestFragment : Fragment() {

    private val viewModel: TestViewModel by viewModels()
    private val navObserver by lazy { NavigationObserver(viewModel) }
    private lateinit var binding: FragmentTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navObserver.register(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_test, container, false)

        binding.navigateToButton.setOnClickListener {
            viewModel.navigateTo()
        }

        binding.navigateBackButton.setOnClickListener {
            viewModel.navigateBack()
        }

        binding.navigateUpButton.setOnClickListener {
            viewModel.navigateUp()
        }

        binding.navigateBackToRootButton.setOnClickListener {
            viewModel.navigateBackToRoot()
        }

        return binding.root
    }


}