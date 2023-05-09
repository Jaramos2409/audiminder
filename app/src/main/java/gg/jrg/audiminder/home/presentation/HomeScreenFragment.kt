package gg.jrg.audiminder.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import dagger.hilt.android.AndroidEntryPoint
import gg.jrg.audiminder.R
import gg.jrg.audiminder.core.presentation.NavigationViewModel
import gg.jrg.audiminder.core.util.NavEvent
import gg.jrg.audiminder.core.util.collectLatestLifecycleFlow
import gg.jrg.audiminder.databinding.FragmentHomeScreenBinding

@AndroidEntryPoint
class HomeScreenFragment : Fragment() {

    private lateinit var binding: FragmentHomeScreenBinding
    private val homeViewModel by viewModels<HomeViewModel>()
    private val navigationViewModel by activityViewModels<NavigationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        collectLatestLifecycleFlow(homeViewModel.welcomeText) {
            binding.homeScreenWelcomeUserText.text = it
        }

        collectLatestLifecycleFlow(homeViewModel.profileImageFilePath) { filePath ->
            if (filePath.isNotEmpty()) {
                binding.homeScreenProfilePhoto.load(filePath) {
                    transformations(CircleCropTransformation())
                }
            } else {
                binding.homeScreenProfilePhoto.setImageResource(R.drawable.account_circle_24)
            }
        }

        binding.homeTopBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home_settings -> {
                    navigationViewModel.navigate(
                        NavEvent.To(HomeScreenFragmentDirections.actionHomeScreenFragmentToSettingsScreenFragment())
                    )
                    true
                }

                else -> {
                    false
                }
            }
        }

        return binding.root
    }

}