package gg.jrg.audiminder.core.util

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.request.Disposable
import coil.request.ImageRequest
import com.google.android.material.appbar.MaterialToolbar
import timber.log.Timber

fun logAndReturnEvaluation(expression: Boolean): Boolean {
    Timber.d("Expression evaluated to: $expression")
    return expression
}

fun <T> Result<T>.throwIfFailure() {
    if (isFailure) {
        throw Exception(exceptionOrNull())
    }
}