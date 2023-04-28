package gg.jrg.audiminder.search.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gg.jrg.audiminder.authentication.data.AuthServiceType
import gg.jrg.audiminder.authentication.presentation.AuthorizationBottomSheetFragment
import gg.jrg.audiminder.authentication.presentation.AuthorizationViewModel
import gg.jrg.audiminder.databinding.FragmentSearchScreenBinding

@AndroidEntryPoint
class SearchScreenFragment : Fragment() {

    private lateinit var binding: FragmentSearchScreenBinding
    private val authorizationViewModel by viewModels<AuthorizationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchScreenBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.searchServiceBar.setOnClickListener {
            if (!authorizationViewModel.isThisServiceAuthorized(AuthServiceType.SPOTIFY)) {
                AuthorizationBottomSheetFragment().show(
                    parentFragmentManager,
                    AuthorizationBottomSheetFragment.TAG
                )
            }
        }

        return binding.root
    }

}