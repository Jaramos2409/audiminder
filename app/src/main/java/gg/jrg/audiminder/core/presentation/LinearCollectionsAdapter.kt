package gg.jrg.audiminder.core.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gg.jrg.audiminder.collections.domain.model.AlbumCollectionWithAlbums
import gg.jrg.audiminder.collections.util.AlbumCollectionWithAlbumsDiffCallBack
import gg.jrg.audiminder.databinding.ItemSearchViewBinding

class LinearCollectionsAdapter(private val clickListener: (AlbumCollectionWithAlbums) -> Unit) :
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
        return AlbumCollectionAddToExistingCollectionViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: AlbumCollectionAddToExistingCollectionViewHolder,
        position: Int
    ) {
        val albumCollectionWithAlbums = getItem(position)
        holder.bind(albumCollectionWithAlbums)
    }

    inner class AlbumCollectionAddToExistingCollectionViewHolder(private val binding: ItemSearchViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(albumCollectionWithAlbums: AlbumCollectionWithAlbums) {
            binding.titleInCardview.text = albumCollectionWithAlbums.collection.name
            binding.subtitleInCardview.text = ""

            binding.collageOrAlbumItemCardview.setOnClickListener {
                clickListener(albumCollectionWithAlbums)
            }
        }

    }
}

