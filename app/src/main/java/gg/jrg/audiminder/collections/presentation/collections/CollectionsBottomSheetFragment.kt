package gg.jrg.audiminder.collections.presentation.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import gg.jrg.audiminder.collections.util.CollectionsSortingType
import gg.jrg.audiminder.databinding.FragmentCollectionsBottomSheetBinding

@AndroidEntryPoint
class CollectionsBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentCollectionsBottomSheetBinding
    private val collectionsBottomSheetViewModel by viewModels<CollectionsBottomSheetViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCollectionsBottomSheetBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        when (collectionsBottomSheetViewModel.collectionsList.getStoredSortingType()) {
            CollectionsSortingType.ALPHABETICAL_A_Z -> binding.checkboxAlphabeticalAZ.visibility =
                View.VISIBLE

            CollectionsSortingType.ALPHABETICAL_Z_A -> binding.checkboxAlphabeticalZA.visibility =
                View.VISIBLE

            CollectionsSortingType.RECENTLY_UPDATED -> binding.checkboxLastUpdatedNewest.visibility =
                View.VISIBLE

            CollectionsSortingType.LEAST_RECENTLY_UPDATED -> binding.checkboxOldest.visibility =
                View.VISIBLE

            CollectionsSortingType.LARGEST_TO_SMALLEST -> binding.checkboxLargest.visibility =
                View.VISIBLE

            CollectionsSortingType.SMALLEST_TO_LARGEST -> binding.checkboxSmallest.visibility =
                View.VISIBLE
        }

        binding.linearLayoutAlphabeticalAZ.setOnClickListener {
            setSortingTypeAndDismissBottomSheet(CollectionsSortingType.ALPHABETICAL_A_Z)
        }

        binding.linearLayoutAlphabeticalZA.setOnClickListener {
            setSortingTypeAndDismissBottomSheet(CollectionsSortingType.ALPHABETICAL_Z_A)
        }

        binding.linearLayoutLastUpdatedNewest.setOnClickListener {
            setSortingTypeAndDismissBottomSheet(CollectionsSortingType.RECENTLY_UPDATED)
        }

        binding.linearLayoutOldest.setOnClickListener {
            setSortingTypeAndDismissBottomSheet(CollectionsSortingType.LEAST_RECENTLY_UPDATED)
        }

        binding.linearLayoutLargest.setOnClickListener {
            setSortingTypeAndDismissBottomSheet(CollectionsSortingType.LARGEST_TO_SMALLEST)
        }

        binding.linearLayoutSmallest.setOnClickListener {
            setSortingTypeAndDismissBottomSheet(CollectionsSortingType.SMALLEST_TO_LARGEST)
        }

        binding.collectionsSortCancelButton.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    private fun setSortingTypeAndDismissBottomSheet(sortingType: CollectionsSortingType) {
        collectionsBottomSheetViewModel.setSortingType(sortingType)
            .invokeOnCompletion {
                Toast.makeText(
                    requireContext(),
                    "Sorting by ${sortingType.value}",
                    Toast.LENGTH_SHORT
                ).show()
                dismiss()
            }
    }

    companion object {
        const val TAG = "CollectionsBottomSheetFragment"
    }
}