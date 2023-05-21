package gg.jrg.audiminder.collections.presentation.add_to_existing

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
import gg.jrg.audiminder.collections.domain.model.AlbumCollectionWithAlbums
import gg.jrg.audiminder.core.presentation.BindableView
import gg.jrg.audiminder.core.presentation.LinearCollectionsAdapter
import gg.jrg.audiminder.core.presentation.NavigationViewModel
import gg.jrg.audiminder.core.util.NavEvent
import gg.jrg.audiminder.core.util.collectLatestLifecycleFlow
import gg.jrg.audiminder.databinding.FragmentAddToExistingCollectionBinding
import timber.log.Timber

@AndroidEntryPoint
class AddToExistingCollectionFragment : Fragment() {

    private lateinit var binding: FragmentAddToExistingCollectionBinding
    private val navArgs by navArgs<AddToExistingCollectionFragmentArgs>()
    private val addToExistingCollectionViewModel by viewModels<AddToExistingCollectionViewModel>()
    private val navigationViewModel by activityViewModels<NavigationViewModel>()
    private val linearCollectionsAdapter by lazy { LinearCollectionsAdapter(::onAlbumCollectionClick) }
    private val addToExistingCollectionSearchViewAdapter by lazy { LinearCollectionsAdapter(::onAlbumCollectionClick) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddToExistingCollectionBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.addToExistingCollectionCollectionsRecyclerViewList.adapter =
            linearCollectionsAdapter

        binding.addToExistingCollectionCollectionsSearchViewRecyclerViewList.adapter =
            addToExistingCollectionSearchViewAdapter

        binding.addToExistingCollectionTopBar.setNavigationOnClickListener {
            navigationViewModel.navigate(NavEvent.Back)
        }

        collectLatestLifecycleFlow(addToExistingCollectionViewModel.collectionsList) { listOfCollections ->
            linearCollectionsAdapter.setFullList(listOfCollections)
            addToExistingCollectionSearchViewAdapter.setFullList(listOfCollections)
        }

        binding.searchView.editText.addTextChangedListener { changedText ->
            addToExistingCollectionSearchViewAdapter.filter(changedText.toString())
        }

        return binding.root
    }

    private fun onAlbumCollectionClick(bindableView: BindableView) {
        val albumCollectionWithAlbums = bindableView as AlbumCollectionWithAlbums
        Timber.i("Album collection clicked: $albumCollectionWithAlbums")
        addToExistingCollectionViewModel.saveAlbumToAlbumCollection(
            navArgs.album,
            albumCollectionWithAlbums.collection
        )
            .invokeOnCompletion {
                navigationViewModel.navigate(NavEvent.Back)
                Toast.makeText(
                    requireContext(),
                    "Added ${navArgs.album.name} to ${albumCollectionWithAlbums.collection.name}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }


}