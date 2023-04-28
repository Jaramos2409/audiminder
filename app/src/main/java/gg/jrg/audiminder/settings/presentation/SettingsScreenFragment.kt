package gg.jrg.audiminder.settings.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gg.jrg.audiminder.authentication.data.AuthServiceType
import gg.jrg.audiminder.authentication.presentation.AuthorizationViewModel
import gg.jrg.audiminder.core.presentation.NavigationViewModel
import gg.jrg.audiminder.databinding.FragmentSettingsScreenBinding

@AndroidEntryPoint
class SettingsScreenFragment : Fragment() {

    private lateinit var binding: FragmentSettingsScreenBinding
    private val settingsViewModel: SettingsViewModel by viewModels()
    private val authorizationViewModel: AuthorizationViewModel by viewModels()
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
            if (authorizationViewModel.isThisServiceAuthorized(AuthServiceType.SPOTIFY)) {
                View.VISIBLE
            } else {
                View.GONE
            }

        binding.unauthenticateSpotifyButton.setOnClickListener {
            authorizationViewModel.unauthorizeThisService(AuthServiceType.SPOTIFY)
        }

        return binding.root
    }


}