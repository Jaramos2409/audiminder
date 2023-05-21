package gg.jrg.audiminder.core.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gg.jrg.audiminder.R
import gg.jrg.audiminder.collections.domain.model.AlbumCollectionWithAlbums
import gg.jrg.audiminder.collections.util.AlbumCollectionWithAlbumsDiffCallBack
import gg.jrg.audiminder.collections.util.bindTo
import gg.jrg.audiminder.databinding.ItemCollectionListViewBinding

class GridCollectionsAdapter(private val clickListener: (BindableView) -> Unit) :
    FilterableListAdapter<AlbumCollectionWithAlbums, GridCollectionsAdapter.AlbumCollectionCollectionsScreenViewHolder>(
        AlbumCollectionWithAlbumsDiffCallBack(),
        { it.collection.name }
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumCollectionCollectionsScreenViewHolder {
        val binding =
            ItemCollectionListViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return AlbumCollectionCollectionsScreenViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(
        holder: AlbumCollectionCollectionsScreenViewHolder,
        position: Int
    ) {
        val albumCollectionWithAlbums = getItem(position)
        holder.bind(albumCollectionWithAlbums)
    }

    class AlbumCollectionCollectionsScreenViewHolder(
        private val binding: ItemCollectionListViewBinding,
        private val clickListener: (BindableView) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(albumCollectionWithAlbums: AlbumCollectionWithAlbums) {
            albumCollectionWithAlbums.bindTo(
                binding.cardView,
                binding.titleTextView,
                binding.subtitleTextView,
                binding.imageContainer,
                R.drawable.baseline_album_24,
                clickListener
            )
        }
    }
}
