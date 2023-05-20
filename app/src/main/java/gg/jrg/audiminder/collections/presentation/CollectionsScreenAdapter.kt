package gg.jrg.audiminder.collections.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import gg.jrg.audiminder.R
import gg.jrg.audiminder.collections.domain.model.AlbumCollectionWithAlbums
import gg.jrg.audiminder.collections.util.AlbumCollectionWithAlbumsDiffCallBack
import gg.jrg.audiminder.databinding.ItemCollectionListViewBinding

class CollectionsScreenAdapter :
    ListAdapter<AlbumCollectionWithAlbums, CollectionsScreenAdapter.AlbumCollectionCollectionsScreenViewHolder>(
        AlbumCollectionWithAlbumsDiffCallBack()
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
        val albumCollectionWithAlbums = getItem(position)
        holder.bind(albumCollectionWithAlbums)
    }

    inner class AlbumCollectionCollectionsScreenViewHolder(private val binding: ItemCollectionListViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(albumCollectionWithAlbums: AlbumCollectionWithAlbums) {
            binding.titleTextView.text = albumCollectionWithAlbums.collection.name
            binding.subtitleTextView.text = ""

            binding.imageContainer.removeAllViews()
            binding.imageContainer.contentDescription =
                binding.root.context.getString(R.string.collage_of_all_the_albums_in_this_collection)

            if (albumCollectionWithAlbums.albums.size >= 4) {
                val imageIds = mutableListOf<Int>()
                val listOfAlbumArts = albumCollectionWithAlbums.albums.shuffled().take(4)

                for (i in 0 until 4) {
                    val imageView = ImageView(itemView.context).apply {
                        id = View.generateViewId()
                        adjustViewBounds = true
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        layoutParams = ConstraintLayout.LayoutParams(0, 0)
                    }
                    imageView.load(listOfAlbumArts[i].imageFilePath)
                    binding.imageContainer.addView(imageView)
                    imageIds.add(imageView.id)
                }

                val set = ConstraintSet()
                set.clone(binding.imageContainer)

                imageIds.forEachIndexed { index, id ->
                    set.connect(
                        id,
                        ConstraintSet.TOP,
                        if (index < 2) ConstraintSet.PARENT_ID else imageIds[index - 2],
                        if (index < 2) ConstraintSet.TOP else ConstraintSet.BOTTOM
                    )
                    set.connect(
                        id,
                        ConstraintSet.START,
                        if (index % 2 == 0) ConstraintSet.PARENT_ID else imageIds[index - 1],
                        if (index % 2 == 0) ConstraintSet.START else ConstraintSet.END
                    )
                    set.constrainPercentHeight(id, 0.5f)
                    set.constrainPercentWidth(id, 0.5f)
                }

                set.applyTo(binding.imageContainer)
            } else if (albumCollectionWithAlbums.albums.size in 1..3) {
                binding.imageContainer.addView(ImageView(itemView.context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    adjustViewBounds = true
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    load(albumCollectionWithAlbums.albums.random().imageFilePath)
                })
            } else {
                binding.imageContainer.addView(ImageView(itemView.context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    adjustViewBounds = true
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    load(R.drawable.baseline_album_24)
                })
            }
        }
    }

}