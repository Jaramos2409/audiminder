package gg.jrg.audiminder.collections.util

import androidx.recyclerview.widget.DiffUtil
import gg.jrg.audiminder.collections.domain.model.AlbumCollection

class AlbumCollectionDiffCallback : DiffUtil.ItemCallback<AlbumCollection>() {
    override fun areItemsTheSame(oldItem: AlbumCollection, newItem: AlbumCollection): Boolean {
        return oldItem.collectionId == newItem.collectionId
    }

    override fun areContentsTheSame(oldItem: AlbumCollection, newItem: AlbumCollection): Boolean {
        return oldItem == newItem
    }
}