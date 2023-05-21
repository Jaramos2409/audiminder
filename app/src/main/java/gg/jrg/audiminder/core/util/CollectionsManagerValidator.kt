package gg.jrg.audiminder.core.util

import gg.jrg.audiminder.collections.domain.model.CollectionsManager

class CollectionsManagerValidator(
    private val collectionsManager: CollectionsManager,
    private val validator: (CollectionsManager) -> Boolean
) {
    fun validate(): Boolean {
        return validator(collectionsManager)
    }
}
