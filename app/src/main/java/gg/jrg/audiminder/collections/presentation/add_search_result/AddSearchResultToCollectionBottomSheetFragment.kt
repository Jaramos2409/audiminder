package gg.jrg.audiminder.collections.presentation.add_search_result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import gg.jrg.audiminder.collections.domain.model.Album
import gg.jrg.audiminder.core.presentation.NavigationViewModel
import gg.jrg.audiminder.core.util.NavEvent
import gg.jrg.audiminder.core.util.getParcelableCompat
import gg.jrg.audiminder.databinding.FragmentAddSearchResultToCollectionBottomSheetBinding
import gg.jrg.audiminder.search.presentation.SearchScreenFragmentDirections

@AndroidEntryPoint
class AddSearchResultToCollectionBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddSearchResultToCollectionBottomSheetBinding
    private val addSearchResultToCollectionViewModel by viewModels<AddSearchResultToCollectionViewModel>()
    private val navigationViewModel by activityViewModels<NavigationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddSearchResultToCollectionBottomSheetBinding.inflate(
            inflater,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner

        arguments?.getParcelableCompat(ALBUM_KEY, Album::class.java)?.let { album ->
            binding.searchResultAlbumArt.load(album.imageFilePath)
            binding.searchResultAlbumName.text = album.name
            binding.searchResultAlbumArtist.text = album.artist
            addSearchResultToCollectionViewModel.setAlbum(album)
        }

        binding.createNewCollectionMaterialCardView.setOnClickListener {
            navigationViewModel.navigate(
                NavEvent.To(
                    SearchScreenFragmentDirections.actionSearchScreenFragmentToCreateNewCollectionFragment(
                        addSearchResultToCollectionViewModel.getAlbum()
                    )
                )
            )
            dismiss()
        }

        binding.addSearchResultToExistingCollectionCardView.setOnClickListener {
            navigationViewModel.navigate(
                NavEvent.To(
                    SearchScreenFragmentDirections.actionSearchScreenFragmentToAddToExistingCollectionFragment(
                        addSearchResultToCollectionViewModel.getAlbum()!!
                    )
                )
            )
            dismiss()
        }

        return binding.root
    }

    companion object {
        const val TAG = "AddSearchResultToCollectionBottomSheetFragment"
        const val ALBUM_KEY = "AddSearchResultToCollectionBottomSheetFragment.album"
    }

}