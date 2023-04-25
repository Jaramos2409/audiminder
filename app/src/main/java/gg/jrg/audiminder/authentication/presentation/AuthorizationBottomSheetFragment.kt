package gg.jrg.audiminder.authentication.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import gg.jrg.audiminder.authentication.data.AuthServiceType
import gg.jrg.audiminder.databinding.FragmentAuthorizationBottomSheetBinding

@AndroidEntryPoint
class AuthorizationBottomSheetFragment : BottomSheetDialogFragment() {

    private val authorizationViewModel: AuthorizationViewModel by viewModels()

    private lateinit var binding: FragmentAuthorizationBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthorizationBottomSheetBinding.inflate(inflater, container, false)

        binding.spotifyAuthButton.setOnClickListener {
            authorizationViewModel.authorizeThisService(AuthServiceType.SPOTIFY)
        }

        return binding.root
    }

    companion object {
        const val TAG = "AuthorizationBottomSheetFragment"
    }

}