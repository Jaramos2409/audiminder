package gg.jrg.audiminder.collections.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gg.jrg.audiminder.collections.domain.model.AlbumCollectionWithAlbums
import gg.jrg.audiminder.collections.util.AlbumCollectionWithAlbumsDiffCallBack
import gg.jrg.audiminder.databinding.ItemSearchViewBinding

class AddToExistingCollectionAdapter(private val clickListener: OnAlbumCollectionClickListener) :
    ListAdapter<AlbumCollectionWithAlbums, AddToExistingCollectionAdapter.AlbumCollectionAddToExistingCollectionViewHolder>(
        AlbumCollectionWithAlbumsDiffCallBack()
    ) {

    private var albumListFull: List<AlbumCollectionWithAlbums> = listOf()

    fun setFullList(list: List<AlbumCollectionWithAlbums>) {
        this.albumListFull = list
        submitList(albumListFull)
    }

    fun filter(query: String) {
        val filteredList = if (query.isEmpty()) {
            albumListFull
        } else {
            albumListFull.filter {
                it.collection.name.contains(query, ignoreCase = true)
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
        val albumCollectionWithAlbums = getItem(position)
        holder.bind(albumCollectionWithAlbums)
    }

    interface OnAlbumCollectionClickListener {
        fun onAlbumCollectionClick(albumCollectionWithAlbums: AlbumCollectionWithAlbums)
    }

    inner class AlbumCollectionAddToExistingCollectionViewHolder(private val binding: ItemSearchViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(albumCollectionWithAlbums: AlbumCollectionWithAlbums) {
            binding.titleInCardview.text = albumCollectionWithAlbums.collection.name
            binding.subtitleInCardview.text = ""

            binding.collageOrAlbumItemCardview.setOnClickListener {
                clickListener.onAlbumCollectionClick(albumCollectionWithAlbums)
            }
        }

    }
}

