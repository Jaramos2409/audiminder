package gg.jrg.audiminder.collections.presentation.collections

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import gg.jrg.audiminder.R
import gg.jrg.audiminder.collections.domain.model.AlbumCollectionWithAlbums
import gg.jrg.audiminder.collections.util.bindTo
import gg.jrg.audiminder.core.presentation.NavigationViewModel
import gg.jrg.audiminder.core.util.getParcelableCompat
import gg.jrg.audiminder.databinding.FragmentDeleteCollectionBottomSheetBinding

@AndroidEntryPoint
class DeleteCollectionBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentDeleteCollectionBottomSheetBinding
    private val deleteCollectionBottomSheetViewModel by viewModels<DeleteCollectionsBottomSheetViewModel>()
    private val navigationViewModel by activityViewModels<NavigationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeleteCollectionBottomSheetBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        arguments?.getParcelableCompat(COLLECTION_KEY, AlbumCollectionWithAlbums::class.java)
            ?.let { albumCollectionWithAlbums ->
                albumCollectionWithAlbums.bindTo(
                    cardView = binding.collectionHoldCardView,
                    titleTextView = binding.collectionNameTextView,
                    subtitleTextView = binding.collectionArtistsTextView,
                    imageContainer = binding.collectionAlbumArt,
                    defaultImageResId = R.drawable.baseline_album_24
                )

                deleteCollectionBottomSheetViewModel.setAlbumCollectionWithAlbums(
                    albumCollectionWithAlbums
                )
            }

        binding.deleteCollectionCardview.setOnClickListener {
            val albumCollectionWithAlbums =
                deleteCollectionBottomSheetViewModel.getAlbumCollectionWithAlbums()

            AlertDialog.Builder(requireContext())
                .setTitle("Delete collection")
                .setMessage("Are you sure you want to delete ${albumCollectionWithAlbums?.collection?.name}?")
                .setPositiveButton("Delete") { _, _ ->
                    deleteCollectionBottomSheetViewModel
                        .deleteAlbumCollection()
                        .invokeOnCompletion {
                            Toast
                                .makeText(
                                    requireContext(),
                                    "Deleted ${albumCollectionWithAlbums?.collection?.name}",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                            dismiss()
                        }

                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.cancel()
                    dismiss()
                }
                .create()
                .show()
        }

        return binding.root
    }

    companion object {
        const val TAG = "DeleteCollectionBottomSheetFragment"
        const val COLLECTION_KEY = "DeleteCollectionBottomSheetFragment.collection"
    }
}