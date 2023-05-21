package gg.jrg.audiminder.core.util

import kotlinx.coroutines.flow.MutableStateFlow

class FlowValidator<T>(
    private val flow: MutableStateFlow<T>,
    private val validator: (T?) -> Boolean
) {
    fun validate(): Boolean {
        return validator(flow.value)
    }
}
