package gg.jrg.audiminder.core.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class FlowValidator<T>(
    private val flow: Flow<T>,
    private val validator: (T?) -> Boolean
) {
    suspend fun validate(): Boolean {
        return validator(flow.first())
    }
}
