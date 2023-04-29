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
import gg.jrg.audiminder.core.util.NavEvent
import gg.jrg.audiminder.databinding.FragmentSettingsScreenBinding
import gg.jrg.audiminder.music_services.data.MusicServiceType
import gg.jrg.audiminder.music_services.presentation.MusicServiceAuthorizationViewModel

@AndroidEntryPoint
class SettingsScreenFragment : Fragment() {

    private lateinit var binding: FragmentSettingsScreenBinding
    private val settingsViewModel: SettingsViewModel by viewModels()
    private val musicServiceAuthorizationViewModel: MusicServiceAuthorizationViewModel by viewModels()
    private val navigationViewModel by activityViewModels<NavigationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsScreenBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.settingsTopBar.setOnClickListener {
            navigationViewModel.navigateBack()
        }

        binding.unauthenticateSpotifyButton.visibility =
            if (musicServiceAuthorizationViewModel.isThisServiceAuthorized(MusicServiceType.SPOTIFY)) {
                View.VISIBLE
            } else {
                View.GONE
            }

        binding.unauthenticateSpotifyButton.setOnClickListener {
            musicServiceAuthorizationViewModel.unauthorizeThisService(MusicServiceType.SPOTIFY)
            Toast.makeText(
                requireContext(),
                getString(R.string.unauthenticated_spotify),
                Toast.LENGTH_SHORT
            ).show()
            navigationViewModel.navigateBackTo(NavEvent.BackTo(R.id.homeScreenFragment))
        }

        return binding.root
    }

}