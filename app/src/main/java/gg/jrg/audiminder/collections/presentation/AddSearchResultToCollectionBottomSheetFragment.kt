package gg.jrg.audiminder.collections.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import gg.jrg.audiminder.collections.domain.model.Album
import gg.jrg.audiminder.core.util.getParcelableCompat
import gg.jrg.audiminder.databinding.FragmentAddSearchResultToCollectionBottomSheetBinding

@AndroidEntryPoint
class AddSearchResultToCollectionBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddSearchResultToCollectionBottomSheetBinding
    private val addSearchResultToCollectionViewModel by viewModels<AddSearchResultToCollectionViewModel>()

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

        return binding.root
    }

    companion object {
        const val TAG = "AddSearchResultToCollectionBottomSheetFragment"
        const val ALBUM_KEY = "album"
    }

}