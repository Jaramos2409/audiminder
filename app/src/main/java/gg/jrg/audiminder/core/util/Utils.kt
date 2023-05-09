package gg.jrg.audiminder.core.util

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