package gg.jrg.audiminder.collections.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gg.jrg.audiminder.collections.domain.model.AlbumCollection
import gg.jrg.audiminder.collections.util.AlbumCollectionDiffCallback
import gg.jrg.audiminder.databinding.ItemSearchViewBinding

class AddToExistingCollectionAdapter(private val clickListener: OnAlbumCollectionClickListener) :
    ListAdapter<AlbumCollection, AddToExistingCollectionAdapter.AlbumCollectionAddToExistingCollectionViewHolder>(
        AlbumCollectionDiffCallback()
    ) {

    private var albumListFull: List<AlbumCollection> = listOf()

    fun setFullList(list: List<AlbumCollection>) {
        this.albumListFull = list
        submitList(albumListFull)
    }

    fun filter(query: String) {
        val filteredList = if (query.isEmpty()) {
            albumListFull
        } else {
            albumListFull.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
        submitList(filteredList)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddToExistingCollectionAdapter.AlbumCollectionAddToExistingCollectionViewHolder {
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
        val album = getItem(position)
        holder.bind(album)
    }

    interface OnAlbumCollectionClickListener {
        fun onAlbumCollectionClick(albumCollection: AlbumCollection)
    }

    inner class AlbumCollectionAddToExistingCollectionViewHolder(private val binding: ItemSearchViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(albumCollection: AlbumCollection) {
            binding.titleInCardview.text = albumCollection.name
            binding.subtitleInCardview.text = ""

            binding.collageOrAlbumItemCardview.setOnClickListener {
                clickListener.onAlbumCollectionClick(albumCollection)
            }
        }

    }
}

