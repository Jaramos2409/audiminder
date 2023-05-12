package gg.jrg.audiminder.core.util

import android.os.Build
import android.os.Bundle
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

@Suppress("DEPRECATION")
fun <T> Bundle.getParcelableCompat(key: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(key, clazz)
    } else {
        getParcelable(key) as? T
    }
}