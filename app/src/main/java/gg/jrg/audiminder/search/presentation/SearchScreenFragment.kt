package gg.jrg.audiminder.search.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gg.jrg.audiminder.authentication.data.AuthServiceType
import gg.jrg.audiminder.authentication.data.AuthorizationState
import gg.jrg.audiminder.authentication.presentation.AuthorizationBottomSheetFragment
import gg.jrg.audiminder.authentication.presentation.AuthorizationViewModel
import gg.jrg.audiminder.databinding.FragmentSearchScreenBinding

@AndroidEntryPoint
class SearchScreenFragment : Fragment() {

    private val authorizationViewModel: AuthorizationViewModel by viewModels()

    private lateinit var binding: FragmentSearchScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchScreenBinding.inflate(inflater, container, false)

        binding.searchServiceBar.setOnClickListener {
            if (authorizationViewModel.getAuthorizationStateOfThisService(AuthServiceType.SPOTIFY) == AuthorizationState.Unauthorized) {
                AuthorizationBottomSheetFragment().show(
                    parentFragmentManager,
                    AuthorizationBottomSheetFragment.TAG
                )
            }
        }

        return binding.root
    }

}