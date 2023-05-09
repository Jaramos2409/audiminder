package gg.jrg.audiminder.settings.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gg.jrg.audiminder.R
import gg.jrg.audiminder.core.presentation.NavigationViewModel
import gg.jrg.audiminder.core.util.collectLatestLifecycleFlow
import gg.jrg.audiminder.databinding.FragmentSettingsScreenBinding

@AndroidEntryPoint
class SettingsScreenFragment : Fragment() {

    private lateinit var binding: FragmentSettingsScreenBinding
    private val settingsViewModel: SettingsViewModel by viewModels()
    private val navigationViewModel by activityViewModels<NavigationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsScreenBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.settingsTopBar.setNavigationOnClickListener {
            navigationViewModel.navigateBack()
        }

        collectLatestLifecycleFlow(settingsViewModel.shouldShowUnauthenticateButton) { shouldShowUnauthenticateButton ->
            binding.unauthenticateSpotifyButton.visibility =
                if (shouldShowUnauthenticateButton) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }

        binding.unauthenticateSpotifyButton.setOnClickListener {
            settingsViewModel.unauthorizeSpotify()
            Toast.makeText(
                requireContext(),
                getString(R.string.unauthenticated_spotify),
                Toast.LENGTH_SHORT
            ).show()
        }

        return binding.root
    }

}