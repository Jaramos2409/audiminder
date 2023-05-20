package gg.jrg.audiminder.collections.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gg.jrg.audiminder.R
import gg.jrg.audiminder.collections.domain.model.AlbumCollectionWithAlbums
import gg.jrg.audiminder.core.presentation.GridCollectionsAdapter
import gg.jrg.audiminder.core.presentation.LinearCollectionsAdapter
import gg.jrg.audiminder.core.presentation.NavigationViewModel
import gg.jrg.audiminder.core.util.NavEvent
import gg.jrg.audiminder.core.util.collectLatestLifecycleFlow
import gg.jrg.audiminder.databinding.FragmentCollectionsScreenBinding

@AndroidEntryPoint
class CollectionsScreenFragment : Fragment() {

    private lateinit var binding: FragmentCollectionsScreenBinding
    private val collectionsViewModel by viewModels<CollectionsViewModel>()
    private val navigationViewModel by activityViewModels<NavigationViewModel>()
    private val gridCollectionsAdapter by lazy { GridCollectionsAdapter(::onAlbumCollectionClick) }
    private val collectionsScreenSearchViewAdapter by lazy { LinearCollectionsAdapter(::onAlbumCollectionClick) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCollectionsScreenBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.collectionsScreenRecyclerView.adapter = gridCollectionsAdapter

        binding.collectionsScreenCollectionsSearchViewRecyclerViewList.adapter =
            collectionsScreenSearchViewAdapter

        binding.collectionsTopBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.collections_add_new -> {
                    navigationViewModel.navigate(
                        NavEvent.To(
                            CollectionsScreenFragmentDirections.actionCollectionsScreenFragmentToCreateNewCollectionFragment(
                                null
                            )
                        )
                    )
                    true
                }

                R.id.collections_sort -> {
                    CollectionsBottomSheetFragment().show(
                        parentFragmentManager,
                        CollectionsBottomSheetFragment.TAG
                    )
                    true
                }

                else -> false
            }
        }

        collectLatestLifecycleFlow(collectionsViewModel.collectionsList) { listOfCollections ->
            gridCollectionsAdapter.setFullList(listOfCollections)
            collectionsScreenSearchViewAdapter.setFullList(listOfCollections)
        }

        binding.collectionsScreenSearchView.editText.addTextChangedListener { changedText ->
            collectionsScreenSearchViewAdapter.filter(changedText.toString())
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        collectionsViewModel.refreshListOfCollections()
    }

    private fun onAlbumCollectionClick(albumCollectionWithAlbums: AlbumCollectionWithAlbums) {

    }

}