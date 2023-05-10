package gg.jrg.audiminder.search.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gg.jrg.audiminder.databinding.FragmentSearchScreenBinding
import gg.jrg.audiminder.music_services.presentation.AuthorizeMusicServiceBottomSheetFragment

@AndroidEntryPoint
class SearchScreenFragment : Fragment() {

    private lateinit var binding: FragmentSearchScreenBinding
    private val searchViewModel by viewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchScreenBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.searchView.setupWithSearchBar(binding.searchServiceBar)

        binding.searchServiceBar.setOnClickListener {
            if (!searchViewModel.isSpotifyAuthorized()) {
                AuthorizeMusicServiceBottomSheetFragment().show(
                    parentFragmentManager,
                    AuthorizeMusicServiceBottomSheetFragment.TAG
                )
            } else {
                binding.searchView.show()
            }
        }

        return binding.root
    }

}