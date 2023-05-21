package gg.jrg.audiminder.collections.util

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import coil.load
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import gg.jrg.audiminder.R
import gg.jrg.audiminder.core.presentation.BindableView

fun BindableView.bindTo(
    cardView: MaterialCardView,
    titleTextView: MaterialTextView,
    subtitleTextView: MaterialTextView,
    imageContainer: ConstraintLayout,
    defaultImageResId: Int,
    clickListener: (BindableView) -> Unit
) {
    titleTextView.text = title
    subtitleTextView.text = subtitle

    cardView.setOnClickListener {
        clickListener(this)
    }

    imageContainer.apply {
        removeAllViews()
        contentDescription = context.getString(
            R.string.collage_of_all_the_albums_in_this_collection
        )

        val imageFilePaths = getImageFilePaths()

        if (imageFilePaths.size >= 4) {
            val imageIds = mutableListOf<Int>()
            for (i in 0 until 4) {
                val imageView = createImageView(context).apply {
                    id = View.generateViewId()
                    load(imageFilePaths[i])
                    layoutParams = ConstraintLayout.LayoutParams(0, 0)
                }
                this.addView(imageView)
                imageIds.add(imageView.id)
            }
            applyConstraints(imageIds)
        } else if (imageFilePaths.isNotEmpty()) {
            addImageView(context, imageFilePaths.random())
        } else {
            addImageView(context, defaultImageResId)
        }
    }
}

private fun createImageView(context: Context): ImageView {
    return ImageView(context).apply {
        id = View.generateViewId()
        adjustViewBounds = true
        scaleType = ImageView.ScaleType.CENTER_CROP
        layoutParams = ConstraintLayout.LayoutParams(0, 0)
    }
}

private fun ConstraintLayout.addImageView(context: Context, image: Any) {
    this.addView(ImageView(context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        adjustViewBounds = true
        scaleType = ImageView.ScaleType.CENTER_CROP
        load(image)
    })
}

private fun ConstraintLayout.applyConstraints(imageIds: List<Int>) {
    val set = ConstraintSet()
    set.clone(this)

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

    set.applyTo(this)
}
