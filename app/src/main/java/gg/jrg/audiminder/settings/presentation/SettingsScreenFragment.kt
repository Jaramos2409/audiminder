package gg.jrg.audiminder.settings.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import dagger.hilt.android.AndroidEntryPoint
import gg.jrg.audiminder.R
import gg.jrg.audiminder.core.presentation.NavigationViewModel
import gg.jrg.audiminder.core.util.NavEvent
import gg.jrg.audiminder.core.util.collectLifecycleFlow
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
            navigationViewModel.navigate(NavEvent.Back)
        }

        collectLifecycleFlow(settingsViewModel.shouldShowSpotifyAuthenticatedCard) { (shouldShow, displayName, profilePath) ->
            binding.spotifyAuthenticatedView.settingsSpotifyAuthenticatedCardview.visibility =
                if (shouldShow) View.VISIBLE else View.GONE

            if (shouldShow) {
                binding.spotifyAuthenticatedView.spotifyDisplayName.text = displayName
                binding.spotifyAuthenticatedView.settingsSpotifyAuthenticatedProfilePicture.load(
                    profilePath
                ) {
                    transformations(CircleCropTransformation())
                }
            }
        }

        binding.spotifyAuthenticatedView.unauthenticateSpotifyButton.setOnClickListener {
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