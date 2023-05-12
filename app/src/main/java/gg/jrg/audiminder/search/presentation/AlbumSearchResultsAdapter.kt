package gg.jrg.audiminder.search.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import gg.jrg.audiminder.collections.domain.model.Album
import gg.jrg.audiminder.collections.presentation.AddSearchResultToCollectionBottomSheetFragment
import gg.jrg.audiminder.databinding.ItemSearchViewBinding

class AlbumSearchResultsAdapter(private val parentFragmentManager: FragmentManager) :
    ListAdapter<Album, AlbumSearchResultsAdapter.AlbumViewHolder>(AlbumDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding =
            ItemSearchViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = getItem(position)
        holder.bind(album)
    }

    inner class AlbumViewHolder(private val binding: ItemSearchViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(album: Album) {
            binding.collageOrAlbumArtInSearchSpotifyItemCardview.load(album.imageFilePath)
            binding.albumNameInSearchSpotifyCardview.text = album.name
            binding.artistNameInSearchSpotifyCardview.text = album.artist
            binding.searchItemCardview.setOnClickListener {
                AddSearchResultToCollectionBottomSheetFragment()
                    .apply {
                        arguments = Bundle().apply {
                            putParcelable(
                                AddSearchResultToCollectionBottomSheetFragment.ALBUM_KEY,
                                album
                            )
                        }
                    }.show(
                        parentFragmentManager,
                        AddSearchResultToCollectionBottomSheetFragment.TAG
                    )
            }
        }
    }

    class AlbumDiffCallback : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.albumId == newItem.albumId
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem == newItem
        }
    }
}
