package gg.jrg.audiminder.collections.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gg.jrg.audiminder.collections.domain.model.AlbumCollection
import gg.jrg.audiminder.collections.util.AlbumCollectionDiffCallback
import gg.jrg.audiminder.databinding.ItemCollectionListViewBinding

class CollectionsScreenAdapter :
    ListAdapter<AlbumCollection, CollectionsScreenAdapter.AlbumCollectionCollectionsScreenViewHolder>(
        AlbumCollectionDiffCallback()
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CollectionsScreenAdapter.AlbumCollectionCollectionsScreenViewHolder {
        val binding =
            ItemCollectionListViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return AlbumCollectionCollectionsScreenViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CollectionsScreenAdapter.AlbumCollectionCollectionsScreenViewHolder,
        position: Int
    ) {
        val album = getItem(position)
        holder.bind(album)
    }

    inner class AlbumCollectionCollectionsScreenViewHolder(private val binding: ItemCollectionListViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(albumCollection: AlbumCollection) {
            binding.titleTextView.apply {
                text = albumCollection.name
            }
            binding.subtitleTextView.apply {
                text = ""
            }
        }

    }

}