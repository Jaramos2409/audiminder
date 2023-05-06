package gg.jrg.audiminder.music_services.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import gg.jrg.audiminder.databinding.FragmentAuthorizationBottomSheetBinding

@AndroidEntryPoint
class AuthorizeMusicServiceBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAuthorizationBottomSheetBinding
    private val authorizeMusicServiceViewModel by viewModels<AuthorizeMusicServiceViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthorizationBottomSheetBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.spotifyAuthButton.setOnClickListener {
            authorizeMusicServiceViewModel.authorizeSpotify()
        }

        return binding.root
    }

    companion object {
        const val TAG = "AuthorizeMusicServiceBottomSheetFragment"
    }

}