package gg.jrg.audiminder.search.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gg.jrg.audiminder.core.util.collectLatestLifecycleFlow
import gg.jrg.audiminder.core.util.collectLifecycleFlow
import gg.jrg.audiminder.databinding.FragmentSearchScreenBinding
import gg.jrg.audiminder.music_services.presentation.AuthorizeMusicServiceBottomSheetFragment
import gg.jrg.audiminder.music_services.util.SpotifyApiStatus

@AndroidEntryPoint
class SearchScreenFragment : Fragment() {

    private lateinit var binding: FragmentSearchScreenBinding
    private val searchViewModel by viewModels<SearchViewModel>()
    private val albumAdapter by lazy { AlbumSearchResultsAdapter(parentFragmentManager) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchScreenBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.searchResultsRecyclerViewList.adapter = albumAdapter

        collectLifecycleFlow(searchViewModel.showAuthorizationBottomSheet) { shouldShowAuthorizationBottomSheet ->
            if (shouldShowAuthorizationBottomSheet) {
                AuthorizeMusicServiceBottomSheetFragment().show(
                    parentFragmentManager,
                    AuthorizeMusicServiceBottomSheetFragment.TAG
                )
            }
        }

        collectLatestLifecycleFlow(searchViewModel.albumSearchResults) { albums ->
            albumAdapter.submitList(albums)
        }

        collectLifecycleFlow(searchViewModel.searchResultsLoadingStatus) { status ->
            when (status) {
                SpotifyApiStatus.LOADING -> {
                    binding.searchResultsLoadingCircle.visibility = View.VISIBLE
                }

                SpotifyApiStatus.ERROR -> {
                    binding.searchResultsLoadingCircle.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Error loading search results",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                SpotifyApiStatus.DONE -> {
                    binding.searchResultsLoadingCircle.visibility = View.GONE
                }
            }
        }

        binding.searchView.editText.addTextChangedListener { changedText ->
            searchViewModel.setQuery(changedText.toString())
        }

        return binding.root
    }

}