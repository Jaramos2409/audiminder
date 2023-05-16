package gg.jrg.audiminder.collections.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import gg.jrg.audiminder.collections.domain.model.AlbumCollection
import gg.jrg.audiminder.core.presentation.NavigationViewModel
import gg.jrg.audiminder.core.util.NavEvent
import gg.jrg.audiminder.core.util.collectLatestLifecycleFlow
import gg.jrg.audiminder.databinding.FragmentAddToExistingCollectionBinding
import timber.log.Timber

@AndroidEntryPoint
class AddToExistingCollectionFragment : Fragment(),
    AddToExistingCollectionAdapter.OnAlbumCollectionClickListener {

    private lateinit var binding: FragmentAddToExistingCollectionBinding
    private val navArgs by navArgs<AddToExistingCollectionFragmentArgs>()
    private val addToExistingCollectionViewModel by viewModels<AddToExistingCollectionViewModel>()
    private val navigationViewModel by activityViewModels<NavigationViewModel>()
    private val addToExistingCollectionAdapter by lazy { AddToExistingCollectionAdapter(this) }
    private val addToExistingCollectionSearchViewAdapter by lazy {
        AddToExistingCollectionAdapter(
            this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddToExistingCollectionBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.addToExistingCollectionCollectionsRecyclerViewList.adapter =
            addToExistingCollectionAdapter

        binding.addToExistingCollectionCollectionsSearchViewRecyclerViewList.adapter =
            addToExistingCollectionSearchViewAdapter

        binding.addToExistingCollectionTopBar.setNavigationOnClickListener {
            navigationViewModel.navigate(NavEvent.Back)
        }

        collectLatestLifecycleFlow(addToExistingCollectionViewModel.collectionsList) { listOfCollections ->
            addToExistingCollectionAdapter.setFullList(listOfCollections)
            addToExistingCollectionSearchViewAdapter.setFullList(listOfCollections)
        }

        binding.searchView.editText.addTextChangedListener { changedText ->
            addToExistingCollectionSearchViewAdapter.filter(changedText.toString())
        }

        return binding.root
    }

    override fun onAlbumCollectionClick(albumCollection: AlbumCollection) {
        Timber.i("Album collection clicked: $albumCollection")
        addToExistingCollectionViewModel.saveAlbumToAlbumCollection(navArgs.album, albumCollection)
            .invokeOnCompletion {
                navigationViewModel.navigate(NavEvent.Back)
                Toast.makeText(
                    requireContext(),
                    "Added ${navArgs.album.name} to ${albumCollection.name}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

}