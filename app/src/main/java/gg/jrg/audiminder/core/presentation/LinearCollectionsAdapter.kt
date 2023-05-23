package gg.jrg.audiminder.core.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gg.jrg.audiminder.R
import gg.jrg.audiminder.collections.domain.model.AlbumCollectionWithAlbums
import gg.jrg.audiminder.collections.util.AlbumCollectionWithAlbumsDiffCallBack
import gg.jrg.audiminder.collections.util.bindTo
import gg.jrg.audiminder.databinding.ItemSearchViewBinding

class LinearCollectionsAdapter(
    private val clickListener: (BindableView) -> Unit,
    private val longClickListener: (BindableView) -> Unit
) :
    FilterableListAdapter<AlbumCollectionWithAlbums, LinearCollectionsAdapter.AlbumCollectionAddToExistingCollectionViewHolder>(
        AlbumCollectionWithAlbumsDiffCallBack(),
        { it.collection.name }
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumCollectionAddToExistingCollectionViewHolder {
        val binding =
            ItemSearchViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return AlbumCollectionAddToExistingCollectionViewHolder(
            binding,
            clickListener,
            longClickListener
        )
    }

    override fun onBindViewHolder(
        holder: AlbumCollectionAddToExistingCollectionViewHolder,
        position: Int
    ) {
        val albumCollectionWithAlbums = getItem(position)
        holder.bind(albumCollectionWithAlbums)
    }

    class AlbumCollectionAddToExistingCollectionViewHolder(
        private val binding: ItemSearchViewBinding,
        private val clickListener: (BindableView) -> Unit,
        private val longClickListener: (BindableView) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(albumCollectionWithAlbums: AlbumCollectionWithAlbums) {
            albumCollectionWithAlbums.bindTo(
                binding.collageOrAlbumItemCardview,
                binding.titleInCardview,
                binding.subtitleInCardview,
                binding.imageContainer,
                R.drawable.baseline_album_24,
                clickListener,
                longClickListener
            )
        }

    }
}

