package gg.jrg.audiminder.collections.presentation.collections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gg.jrg.audiminder.collections.domain.model.CollectionsManager
import gg.jrg.audiminder.collections.domain.usecase.CollectionsUseCases
import gg.jrg.audiminder.collections.util.CollectionsSortingType
import gg.jrg.audiminder.core.util.ScreenKey
import gg.jrg.audiminder.core.util.logChanges
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionsBottomSheetViewModel @Inject constructor(
    collectionsUseCases: CollectionsUseCases
) : ViewModel() {

    val collectionsList = CollectionsManager(
        collectionsUseCases,
        ScreenKey.COLLECTIONS_SCREEN
    ).apply { logChanges("CollectionsBottomSheetViewModel _collectionsList") }


    fun setSortingType(sortingType: CollectionsSortingType): Job =
        viewModelScope.launch {
            collectionsList.setStoredSortingType(sortingType)
        }

}