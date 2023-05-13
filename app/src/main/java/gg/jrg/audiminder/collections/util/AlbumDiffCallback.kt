package gg.jrg.audiminder.collections.util

import androidx.recyclerview.widget.DiffUtil
import gg.jrg.audiminder.collections.domain.model.Album

class AlbumDiffCallback : DiffUtil.ItemCallback<Album>() {
    override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem.albumId == newItem.albumId
    }

    override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem == newItem
    }
}