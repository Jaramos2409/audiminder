package gg.jrg.audiminder.collections.util

import androidx.recyclerview.widget.DiffUtil
import gg.jrg.audiminder.collections.domain.model.AlbumCollectionWithAlbums

class AlbumCollectionWithAlbumsDiffCallBack : DiffUtil.ItemCallback<AlbumCollectionWithAlbums>() {
    override fun areItemsTheSame(
        oldItem: AlbumCollectionWithAlbums,
        newItem: AlbumCollectionWithAlbums
    ): Boolean {
        return oldItem.collection.collectionId == newItem.collection.collectionId
    }

    override fun areContentsTheSame(
        oldItem: AlbumCollectionWithAlbums,
        newItem: AlbumCollectionWithAlbums
    ): Boolean {
        return oldItem == newItem
    }
}
