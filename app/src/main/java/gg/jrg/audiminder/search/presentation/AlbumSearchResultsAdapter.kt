package gg.jrg.audiminder.search.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import gg.jrg.audiminder.collections.domain.model.Album
import gg.jrg.audiminder.collections.presentation.AddSearchResultToCollectionBottomSheetFragment
import gg.jrg.audiminder.collections.util.AlbumDiffCallback
import gg.jrg.audiminder.databinding.ItemSearchViewBinding

class AlbumSearchResultsAdapter(private val parentFragmentManager: FragmentManager) :
    ListAdapter<Album, AlbumSearchResultsAdapter.AlbumSearchResultsViewHolder>(AlbumDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumSearchResultsViewHolder {
        val binding =
            ItemSearchViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumSearchResultsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumSearchResultsViewHolder, position: Int) {
        val album = getItem(position)
        holder.bind(album)
    }

    inner class AlbumSearchResultsViewHolder(private val binding: ItemSearchViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(album: Album) {
            binding.collageOrAlbumArtInCardview.load(album.imageFilePath)
            binding.titleInCardview.text = album.name
            binding.subtitleInCardview.text = album.artist
            binding.collageOrAlbumItemCardview.setOnClickListener {
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

}
